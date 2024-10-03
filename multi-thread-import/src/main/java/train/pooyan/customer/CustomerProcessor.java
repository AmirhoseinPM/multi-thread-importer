package train.pooyan.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import train.pooyan.core.EntityValidation;
import train.pooyan.core.LineProcessor;
import train.pooyan.core.ValidationFactory;
import train.pooyan.error.ErrorWriter;

@Component
public class CustomerProcessor extends LineProcessor<Customer>{

    @Autowired
    ErrorWriter errorWriter;
    
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ValidationFactory<Customer> validationFactory;
    
    @Value("${customer.file.name}")
    private String fileName;

	@Override
	public EntityValidation<Customer> getValidation(String line) {
		return validationFactory.getValidation(line);
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public ErrorWriter getErrorWriter() {
		return errorWriter;
	}

	@Override
	public Customer saveEntity(Customer entity) {
		return (Customer) customerRepo.save(entity);
	}
}
