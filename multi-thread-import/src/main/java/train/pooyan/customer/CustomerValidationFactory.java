package train.pooyan.customer;

import org.springframework.stereotype.Component;

import train.pooyan.core.EntityValidation;

@Component
public class CustomerValidationFactory {

	public static EntityValidation<Customer> getCustomerValidation(String line) {
		return new CustomerValidation(line);
	}
}
