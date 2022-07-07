package it.unimib.bank.exception;

public class TransactionNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -9221068400182780767L;

	public TransactionNotFoundException(String id) {
		super("Cannot find transaction with id " + id);
	}

}
