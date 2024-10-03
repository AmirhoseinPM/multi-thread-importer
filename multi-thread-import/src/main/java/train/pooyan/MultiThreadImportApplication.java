package train.pooyan;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import train.pooyan.account.AccountFileReader;
import train.pooyan.core.MyExecutorService;
import train.pooyan.customer.CustomerFileReader;
import train.pooyan.error.ErrorWriter;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class MultiThreadImportApplication {


	private static final Logger log = LoggerFactory.getLogger(MultiThreadImportApplication.class);

	@Autowired
	private static MyExecutorService executorService;
	
	
	@Autowired
	private static ErrorWriter errorWriter;

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MultiThreadImportApplication.class, args);

		CustomerFileReader customerFileReader = context.getBean(CustomerFileReader.class);
		AccountFileReader accountFileReader = context.getBean(AccountFileReader.class);
		ErrorWriter errorWriter = context.getBean(ErrorWriter.class);

		ExecutorService executor = executorService.getExecutorSercvice();
		
		
		CountDownLatch threadCount = new CountDownLatch(2);

		executor.submit(
				() -> accountFileReader.start(threadCount)
		);
		executor.submit(
				() -> customerFileReader.start(threadCount)
		);
		
		try {
			threadCount.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			executor.shutdown();
			errorWriter.writeErrors();
			log.info("End----------------------------");
		}
		

		
	}
}
