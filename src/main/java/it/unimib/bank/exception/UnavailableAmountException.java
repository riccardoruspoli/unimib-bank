package it.unimib.bank.exception;

public class UnavailableAmountException extends RuntimeException {
	private static final long serialVersionUID = -6092557194634872439L;

	public UnavailableAmountException() {
		super();
	}

	public UnavailableAmountException(String msg) {
		super(msg);
	}

}
