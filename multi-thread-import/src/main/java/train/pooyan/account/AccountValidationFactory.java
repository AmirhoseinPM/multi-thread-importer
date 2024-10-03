package train.pooyan.account;

import org.springframework.stereotype.Component;

import train.pooyan.core.EntityValidation;

@Component
public class AccountValidationFactory {
	
	public static EntityValidation<Account> getAccountValidation(String line) {
		return new AccountValidation(line);
	}
}
