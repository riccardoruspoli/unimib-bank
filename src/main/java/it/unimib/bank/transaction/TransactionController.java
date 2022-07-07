package it.unimib.bank.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.unimib.bank.exception.AccountNotFoundException;
import it.unimib.bank.exception.TransactionNotFoundException;
import it.unimib.bank.exception.UnavailableAmountException;
import it.unimib.bank.transfer.DivertRequest;
import it.unimib.bank.transfer.Transfer;
import it.unimib.bank.transfer.TransferResponse;

@RestController
@RequestMapping("/api")
public class TransactionController {
	private final TransactionService service;

	public TransactionController(TransactionService service) {
		this.service = service;
	}

	@PostMapping("/transfer")
	public TransferResponse transfer(@RequestBody Transfer transfer) {
		try {
			return service.transfer(transfer);
		} catch (IllegalArgumentException | UnavailableAmountException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (AccountNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@PostMapping("/divert")
	public void divert(@RequestBody DivertRequest divert) {
		try {
			service.divert(divert);
		} catch (IllegalArgumentException | UnsupportedOperationException | UnavailableAmountException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (TransactionNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

}
