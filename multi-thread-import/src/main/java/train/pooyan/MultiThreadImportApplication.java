package train.pooyan;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger log =
			LoggerFactory.getLogger(MultiThreadImportApplication.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MultiThreadImportApplication.class, args);

		// Reading and processing Bean, for customer and account files
		CustomerFileReader customerFileReader = context.getBean(CustomerFileReader.class);
		AccountFileReader accountFileReader = context.getBean(AccountFileReader.class);

		// get Singleton executor instance for do tasks concurrently
		MyExecutorService myExecutorService = context.getBean(MyExecutorService.class);
		ExecutorService executor = myExecutorService.getExecutorSercvice();

		// track completing tasks that we add to executor
		CountDownLatch threadCount = new CountDownLatch(2);

		// start processing of account csv file
		executor.submit(
				() -> accountFileReader.start(threadCount)
		);

		// start processing of customer csv file
		executor.submit(
				() -> customerFileReader.start(threadCount)
		);

		try {
			// waiting for all tasks done
			threadCount.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// shutdown executor after all tasks done
			executor.shutdown();
			// write error that saved in ErrorWriter bean to errors file
			ErrorWriter errorWriter = context.getBean(ErrorWriter.class);
			errorWriter.writeErrors();
			log.info("End----------------------------");
		}
	}
}
