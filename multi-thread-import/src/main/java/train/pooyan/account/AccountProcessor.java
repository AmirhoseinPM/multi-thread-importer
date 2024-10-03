package train.pooyan.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import train.pooyan.core.EntityValidation;
import train.pooyan.core.LineProcessor;
import train.pooyan.error.ErrorWriter;


@Component
public class AccountProcessor extends LineProcessor<Account>{
	
    @Autowired
    AccountRepo accountRepo;

    @Autowired
    ErrorWriter errorWriter;

    @Value("${account.file.name}")
    private String fileName;
    
	@Override
	public EntityValidation<Account> getValidation(String line) {
		return AccountValidationFactory.getAccountValidation(line);
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
	public Account saveEntity(Account entity) {
		return (Account) accountRepo.save(entity);
	}    
}
