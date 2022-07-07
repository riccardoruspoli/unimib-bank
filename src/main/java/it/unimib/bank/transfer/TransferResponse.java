package it.unimib.bank.transfer;

import it.unimib.bank.account.Account;
import it.unimib.bank.transaction.Transaction;

public class TransferResponse {
	private Account source;
	private Account destination;
	private Transaction transaction;

	public TransferResponse(Account source, Account destination, Transaction transaction) {
		super();
		this.source = source;
		this.destination = destination;
		this.transaction = transaction;
	}

	public Account getSource() {
		return source;
	}

	public void setSource(Account source) {
		this.source = source;
	}

	public Account getDestination() {
		return destination;
	}

	public void setDestination(Account destination) {
		this.destination = destination;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public String toString() {
		return "TransferResponse [source=" + source + ", destination=" + destination + ", transaction=" + transaction
				+ "]";
	}
}
