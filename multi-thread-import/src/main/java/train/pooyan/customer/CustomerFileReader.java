package train.pooyan.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import train.pooyan.core.FileReader;
import train.pooyan.core.LineProcessor;

@Component
public class CustomerFileReader extends FileReader<Customer>{

    @Value("${customer.file.name}")
    private String fileName;
    CustomerProcessor lineProcessor;

	@Autowired
	public CustomerFileReader(CustomerProcessor lineProcessor) {
		this.lineProcessor = lineProcessor;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public LineProcessor<Customer> getLineProcessor() {
		return lineProcessor;
	}
}
