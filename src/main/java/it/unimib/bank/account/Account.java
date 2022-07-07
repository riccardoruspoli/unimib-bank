package it.unimib.bank.account;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import it.unimib.bank.transaction.Transaction;

@Entity(name = "Account")
@Table(schema = "bank", name = "account")
public class Account {
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(generator = "random_20_chars_hex")
	@GenericGenerator(name = "random_20_chars_hex", strategy = "it.unimib.bank.account.Random20CharsHexGenerator")
	private String id;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "name")
	private String name;
	@Column(name = "surname")
	private String surname;
	@Column(name = "balance")
	private BigDecimal balance = new BigDecimal(0);

	@OneToMany(mappedBy = "source", cascade = CascadeType.ALL)
	@OrderBy("timestamp ASC")
	@JsonManagedReference(value = "source")
	private List<Transaction> transactions;

	public Account() {
	}

	public Account(String id) {
		this.id = id;
	}

	public Account(String id, boolean active, String name, String surname, BigDecimal balance,
			List<Transaction> transactions) {
		super();
		this.id = id;
		this.active = active;
		this.name = name;
		this.surname = surname;
		this.balance = balance;
		this.transactions = transactions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", active=" + active + ", name=" + name + ", surname=" + surname + ", amount="
				+ balance + ", transactions=" + transactions + "]";
	}
}
