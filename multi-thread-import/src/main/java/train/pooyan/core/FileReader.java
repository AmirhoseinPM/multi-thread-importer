package train.pooyan.core;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public abstract class FileReader<T> {

	public void start(CountDownLatch threadCount) {
		File file = new File(getFileName());
        
        try (
                Scanner fileScanner = new Scanner(file);
        ) {
        	
        	while (fileScanner.hasNext()) {
        			String line = fileScanner.nextLine();
        			getLineProcessor().processLine(line);
        	}

        } catch (IOException  e) {
			e.printStackTrace();
		}
        finally {        	
			threadCount.countDown();
		}
	}
	
	public abstract String getFileName();
	public abstract LineProcessor<T> getLineProcessor();
}
