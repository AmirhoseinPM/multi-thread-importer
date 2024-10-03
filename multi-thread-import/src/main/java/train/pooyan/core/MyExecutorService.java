package train.pooyan.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyExecutorService {
	
	private static ExecutorService executorService;
	
	private MyExecutorService() {

	}

	public static ExecutorService getExecutorSercvice() {
		if (executorService == null)
			executorService = Executors.newScheduledThreadPool(
					Runtime.getRuntime().availableProcessors()
			);
		return executorService;
	}


	
	public void shutdown() {
		executorService.shutdown();
	}
}
