package it.unimib.bank.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import it.unimib.bank.account.Account;
import it.unimib.bank.account.AccountService;
import it.unimib.bank.exception.TransactionNotFoundException;
import it.unimib.bank.exception.UnavailableAmountException;
import it.unimib.bank.transfer.DivertRequest;
import it.unimib.bank.transfer.Transfer;
import it.unimib.bank.transfer.TransferResponse;

@Service
public class TransactionService {
	private final TransactionRepository repository;
	private final AccountService accountService;

	public TransactionService(TransactionRepository repository, AccountService accountService) {
		this.repository = repository;
		this.accountService = accountService;
	}

	public Transaction createTransaction(Transaction transaction) {
		if (transaction == null)
			throw new IllegalArgumentException("Cannot process null transaction");

		return repository.save(transaction);
	}

	public TransferResponse transfer(Transfer transfer) {
		if (transfer == null)
			throw new IllegalArgumentException("Cannot process null transfer");

		if (transfer.getFrom() == null || transfer.getTo() == null)
			throw new IllegalArgumentException("Cannot process transfer without source or destination account");

		if (transfer.getAmount() == null)
			throw new IllegalArgumentException("Cannot process transfer without amount");

		if (transfer.getAmount().compareTo(BigDecimal.ZERO) < 0)
			throw new IllegalArgumentException("Cannot process transfer with negative amount");

		Account source = accountService.getAccountById(transfer.getFrom());

		if (source.getBalance().compareTo(transfer.getAmount()) < 0) {
			throw new UnavailableAmountException(
					"Unable to transfer " + transfer.getAmount() + ", balance is " + source.getBalance());
		}

		Account destination = accountService.getAccountById(transfer.getTo());
		Transaction transaction = new Transaction(source, destination, transfer.getAmount(), TransactionType.TRANSFER);

		transaction = createTransaction(transaction);

		// remove amount from source account
		source.setBalance(source.getBalance().subtract(transaction.getAmount()));
		source = accountService.updateAccount(source);

		// remove transactions
		source.setTransactions(null);

		// add amount from destination account
		destination.setBalance(destination.getBalance().add(transaction.getAmount()));
		destination = accountService.updateAccount(destination);

		// remove transactions
		destination.setTransactions(null);

		return new TransferResponse(source, destination, transaction);
	}

	public void divert(DivertRequest divert) {
		if (divert == null)
			throw new IllegalArgumentException("Cannot process null divert");

		if (divert.getId() == null)
			throw new IllegalArgumentException("Cannot process divert without transfer id");

		Optional<Transaction> persistentTransaction = repository.findById(UUID.fromString(divert.getId()));

		if (!persistentTransaction.isPresent())
			throw new TransactionNotFoundException(divert.getId());

		Transaction transaction = persistentTransaction.get();

		if (transaction.getType() != TransactionType.TRANSFER) {
			throw new UnsupportedOperationException("Can only divert a transfer");
		}

		Account source = transaction.getDestination();

		if (source.getBalance().compareTo(transaction.getAmount()) < 0) {
			throw new UnavailableAmountException("Unable to divert transaction with amount " + transaction.getAmount()
					+ ", balance is " + source.getBalance());
		}

		Account destination = transaction.getSource();
		Transaction newTransaction = new Transaction(source, destination, transaction.getAmount(),
				TransactionType.DIVERT);
		newTransaction.setTimestamp(LocalDateTime.now());

		createTransaction(newTransaction);

		// remove amount from source account
		source.setBalance(source.getBalance().subtract(transaction.getAmount()));
		accountService.updateAccount(source);

		// add amount from destination account
		destination.setBalance(destination.getBalance().add(transaction.getAmount()));
		accountService.updateAccount(destination);
	}

	public Account saveTransaction(String accountId, Transaction transaction) {
		if (accountId == null)
			throw new IllegalArgumentException("Cannot save transaction without account id");

		if (transaction == null)
			throw new IllegalArgumentException("Cannot save null transaction");

		Account account = accountService.getAccountById(accountId);

		transaction.setSource(account);
		transaction.setDestination(account);
		transaction.setTimestamp(LocalDateTime.now());

		if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
			transaction.setType(TransactionType.WITHDRAWAL);

			if (account.getBalance().compareTo(transaction.getAmount().abs()) < 0)
				throw new UnavailableAmountException(
						"Cannot withdraw " + transaction.getAmount().abs() + ", balance is " + account.getBalance());
		} else {
			transaction.setType(TransactionType.DEPOSIT);
		}

		createTransaction(transaction);

		account.setBalance(account.getBalance().add(transaction.getAmount()));
		account = accountService.updateAccount(account);

		// return the updated account with the newly inserted transaction only
		account.setTransactions(List.of(transaction));
		return account;
	}
}
