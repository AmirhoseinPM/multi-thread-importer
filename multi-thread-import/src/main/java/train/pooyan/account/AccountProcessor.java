package train.pooyan.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import train.pooyan.core.EntityValidation;
import train.pooyan.core.LineProcessor;
import train.pooyan.core.ValidationFactory;
import train.pooyan.error.ErrorWriter;


@Component
public class AccountProcessor extends LineProcessor<Account>{

	@Value("${account.file.name}")
	private String fileName;
    AccountRepo accountRepo;
    ErrorWriter errorWriter;
    ValidationFactory<Account> validationFactory;

	@Autowired
	public AccountProcessor(AccountRepo accountRepo, ErrorWriter errorWriter, ValidationFactory<Account> validationFactory) {
		this.accountRepo = accountRepo;
		this.errorWriter = errorWriter;
		this.validationFactory = validationFactory;
	}

	@Override
	public EntityValidation<Account> getValidation(String line) {
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
	public void saveEntity(Account entity) {
		accountRepo.save(entity);
	}    
}
