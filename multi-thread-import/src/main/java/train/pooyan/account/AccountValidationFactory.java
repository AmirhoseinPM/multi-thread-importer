package train.pooyan.account;

import org.springframework.stereotype.Component;

import train.pooyan.core.EntityValidation;
import train.pooyan.core.ValidationFactory;

@Component
public class AccountValidationFactory implements ValidationFactory<Account>{
	
	public EntityValidation<Account> getValidation(String line) {
		return new AccountValidation(line);
	}
}
