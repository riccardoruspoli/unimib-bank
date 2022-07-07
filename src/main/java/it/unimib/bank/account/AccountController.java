package it.unimib.bank.account;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.unimib.bank.exception.AccountNotFoundException;
import it.unimib.bank.exception.UnavailableAmountException;
import it.unimib.bank.transaction.Transaction;
import it.unimib.bank.transaction.TransactionService;

@RestController
@RequestMapping("/api/account")
public class AccountController {
	private final AccountService accountService;
	private final TransactionService transactionService;

	public AccountController(AccountService accountService, TransactionService transactionService) {
		this.accountService = accountService;
		this.transactionService = transactionService;
	}

	@GetMapping
	public List<Account> getAllAccounts() {
		return accountService.getAllAccounts();
	}

	@PostMapping
	public Account createAccount(@RequestBody Account account) {
		try {
			return accountService.createAccount(account);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@DeleteMapping
	public Account deleteAccount(@PathParam("id") String id) {
		try {
			return accountService.deleteAccount(id);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (AccountNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@GetMapping("/{accountId}")
	public Account getAccount(HttpServletResponse response, @PathVariable("accountId") String accountId) {
		try {
			Account account = accountService.getAccountById(accountId);
			response.addHeader("X-Sistema-Bancario", account.getName() + ";" + account.getSurname());
			return account;
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (AccountNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@PostMapping("/{accountId}")
	public Account insertTransaction(@PathVariable("accountId") String accountId,
			@RequestBody Transaction transaction) {
		try {
			return transactionService.saveTransaction(accountId, transaction);
		} catch (IllegalArgumentException | UnavailableAmountException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (AccountNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@PutMapping("/{accountId}")
	public Account putAccount(@PathVariable("accountId") String accountId, @RequestBody Account account) {
		try {
			return accountService.updateAccountDetails(accountId, account, HttpMethod.PUT);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (AccountNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@PatchMapping("/{accountId}")
	public Account patchAccount(@PathVariable("accountId") String accountId, @RequestBody Account account) {
		try {
			return accountService.updateAccountDetails(accountId, account, HttpMethod.PATCH);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (AccountNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
}
