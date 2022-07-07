package it.unimib.bank.exception;

public class AccountNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4211730410854366164L;

	public AccountNotFoundException(String id) {
		super("Cannot find account with id " + id);
	}

}
