package it.unimib.bank.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import it.unimib.bank.account.Account;

@Entity(name = "Transaction")
@Table(schema = "bank", name = "transaction")
public class Transaction {
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "source", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "transaction_source_fkey"))
	@JsonBackReference(value = "source")
	private Account source;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "destination", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "transaction_destination_fkey"))
	@JsonBackReference(value = "destination")
	private Account destination;
	@Column(name = "amount")
	private BigDecimal amount;
	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private TransactionType type;

	public Transaction() {
	}

	public Transaction(Account source, Account destination, BigDecimal amount, TransactionType type) {
		super();
		this.source = source;
		this.destination = destination;
		this.amount = amount;
		this.type = type;
		this.timestamp = LocalDateTime.now();
	}

	public Transaction(UUID id, Account source, Account destination, BigDecimal amount, LocalDateTime timestamp,
			TransactionType type) {
		super();
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.amount = amount;
		this.timestamp = timestamp;
		this.type = type;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", amount=" + amount + ", timestamp=" + timestamp + ", type=" + type + "]";
	}
}
