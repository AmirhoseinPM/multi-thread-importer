package train.pooyan.account;

import jakarta.persistence.*;
import train.pooyan.customer.Customer;

import java.time.LocalDate;

@Entity
public class Account {
	
	@Id
	@GeneratedValue
	private Long id;
	private String number;
	private String type;


	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	private long balanceLimit;
	private LocalDate openDate;
	private String balance;
	


	@Override
	public String toString() {
		return "Account{" +
				"id=" + id +
				", number='" + number + '\'' +
				", type='" + type + '\'' +
				", customer=" + customer +
				", limit=" + balanceLimit +
				", openDate=" + openDate +
				", balance=" + balance +
				'}';
	}

	public Account() {}

	public Account(String number, String type, Customer customer, long limit, LocalDate openDate, String balance) {
		this.number = number;
		this.type = type;
		this.customer = customer;
		this.balanceLimit = limit;
		this.openDate = openDate;
		this.balance = balance;
	}


	// getters and setters

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public long getBalanceLimit() {
		return balanceLimit;
	}

	public void setBalanceLimit(long limit) {
		this.balanceLimit = limit;
	}

	public LocalDate getOpenDate() {
		return openDate;
	}

	public void setOpenDate(LocalDate openDate) {
		this.openDate = openDate;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
}
