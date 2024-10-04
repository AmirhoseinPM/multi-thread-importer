package train.pooyan.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import train.pooyan.core.FileReader;
import train.pooyan.core.LineProcessor;

@Component
public class AccountFileReader extends FileReader<Account> {

    @Value("${account.file.name}")
    private String fileName;
    AccountProcessor lineProcessor;

	@Autowired
	public AccountFileReader(AccountProcessor lineProcessor) {
		this.lineProcessor = lineProcessor;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public LineProcessor<Account> getLineProcessor() {
		return lineProcessor;
	}
}
