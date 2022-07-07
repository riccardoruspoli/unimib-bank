package it.unimib.bank.transfer;

import java.math.BigDecimal;

public class Transfer {
	private String from;
	private String to;
	private BigDecimal amount;

	public Transfer() {
	}

	public Transfer(String from, String to, BigDecimal amount) {
		super();
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transfer [from=" + from + ", to=" + to + ", amount=" + amount + "]";
	}
}
