package train.pooyan.core;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MyExecutorService {

	private ExecutorService executorService;

	private MyExecutorService() {
	}

	public ExecutorService getExecutorSercvice() {
		// get a singleton instance of executor for doing tasks concurrently in main app
		if (executorService == null)
			executorService = Executors.newScheduledThreadPool(
					Runtime.getRuntime().availableProcessors()
			);
		return executorService;
	}
}
