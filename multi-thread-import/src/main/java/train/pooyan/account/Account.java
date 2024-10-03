package train.pooyan.account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


import java.time.LocalDate;


@Entity
public class Account {
	
	@Id
	@GeneratedValue
	private Long id;
	private String number;
	private String type;
	private long customerId;
	private long balanceLimit;
	private LocalDate openDate;
	private String balance;
	


	@Override
	public String toString() {
		return "Account{" +
				"id=" + id +
				", number='" + number + '\'' +
				", type='" + type + '\'' +
				", customerId=" + customerId +
				", limit=" + balanceLimit +
				", openDate=" + openDate +
				", balance=" + balance +
				'}';
	}

	public Account() {}

	public Account(String number, String type, long customerId, long limit, LocalDate openDate, String balance) {
		this.number = number;
		this.type = type;
		this.customerId = customerId;
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

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
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
