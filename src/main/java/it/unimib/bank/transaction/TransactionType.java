package it.unimib.bank.transaction;

public enum TransactionType {
	DEPOSIT("DEPOSIT"), WITHDRAWAL("WITHDRAWAL"), TRANSFER("TRANSFER"), DIVERT("DIVERT");

	private final String type;

	private TransactionType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
