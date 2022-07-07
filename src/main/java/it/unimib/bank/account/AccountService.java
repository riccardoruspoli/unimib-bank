package it.unimib.bank.account;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import it.unimib.bank.exception.AccountNotFoundException;

@Service
public class AccountService {
	private final AccountRepository repository;

	public AccountService(AccountRepository repository) {
		this.repository = repository;
	}

	public List<Account> getAllAccounts() {
		return repository.findAll();
	}

	public Account getAccountById(String id) {
		if (id == null)
			throw new IllegalArgumentException("Cannot process null id");

		Optional<Account> account = repository.findById(id);

		if (!account.isPresent())
			throw new AccountNotFoundException(id);

		return account.get();
	}

	public Account createAccount(Account account) {
		if (account == null)
			throw new IllegalArgumentException("Cannot process null account");

		if (account.getName() == null || account.getSurname() == null)
			throw new IllegalArgumentException("Name and surname are mandatory fields to open an account");

		return repository.save(account);
	}

	public Account updateAccount(Account account) {
		if (account == null)
			throw new IllegalArgumentException("Cannot process null account");

		if (account.getId() == null)
			throw new IllegalArgumentException("Id is mandatory to update an account");

		return repository.save(account);
	}

	public Account deleteAccount(String id) {
		if (id == null)
			throw new IllegalArgumentException("Cannot process null id");

		Optional<Account> persistentAccount = repository.findById(id);

		if (!persistentAccount.isPresent())
			throw new AccountNotFoundException(id);

		Account account = persistentAccount.get();
		account.setActive(false);
		return repository.save(account);
	}

	public Account updateAccountDetails(String accountId, Account accountDTO, HttpMethod method) {
		if (accountId == null)
			throw new IllegalArgumentException("Cannot process null id");

		if (accountDTO == null)
			throw new IllegalArgumentException("Cannot process null account");

		if (method == HttpMethod.PUT && (accountDTO.getName() == null || accountDTO.getSurname() == null))
			throw new IllegalArgumentException("Cannot update single value on PUT method.");

		if (method == HttpMethod.PATCH && (accountDTO.getName() != null && accountDTO.getSurname() != null))
			throw new IllegalArgumentException("Cannot update multiple values simultaneously on PATCH method.");

		Account account = getAccountById(accountId);

		if (accountDTO.getName() != null)
			account.setName(accountDTO.getName());

		if (accountDTO.getSurname() != null)
			account.setSurname(accountDTO.getSurname());

		return repository.save(account);
	}
}
