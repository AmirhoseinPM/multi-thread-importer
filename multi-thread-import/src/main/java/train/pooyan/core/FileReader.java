package train.pooyan.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

public abstract class FileReader<T> {
	/*
	 * this class implement core functionality of reading csv files
	 * that related to entity of <T>, and process each line using a
	 * LineProcessor bean.
	 * */
	public abstract String getFileName();
	public abstract LineProcessor<T> getLineProcessor();

	public void start(CountDownLatch threadCount) {

        try (
                Stream<String> fileStream = Files.lines(Path.of(getFileName()))
        ) {
			// use parallel stream to parallel processing
        	fileStream.parallel().forEach(
					line -> getLineProcessor().processLine(line)
			);
        } catch (IOException e) {
			e.printStackTrace();
		}
        finally {        	
			threadCount.countDown();
		}
	}
}
