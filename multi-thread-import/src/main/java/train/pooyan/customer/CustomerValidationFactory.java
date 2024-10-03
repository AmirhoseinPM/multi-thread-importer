package train.pooyan.customer;

import org.springframework.stereotype.Component;

import train.pooyan.core.EntityValidation;
import train.pooyan.core.ValidationFactory;

@Component
public class CustomerValidationFactory implements ValidationFactory<Customer> {

	public EntityValidation<Customer> getValidation(String line) {
		return new CustomerValidation(line);
	}
}
