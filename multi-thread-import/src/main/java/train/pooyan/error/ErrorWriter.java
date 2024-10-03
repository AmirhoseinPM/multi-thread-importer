package train.pooyan.error;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Component
public class ErrorWriter {

    @Value("${errors.file.json.fileName}")
    private String errorsFileName;
    private final JsonMapper jsonMapper = new JsonMapper();
    private Set<Error> errors = new HashSet<>();
    private static final Logger log = LoggerFactory.getLogger(ErrorWriter.class);

    public synchronized void addError(Error error) {
        log.info(error.RECORD_NUMBER() + "- add error: " + error);
        errors.add(error);
    }


    public void writeErrors() {
        try {
            jsonMapper.writerWithDefaultPrettyPrinter()
            		.writeValue(new File(errorsFileName), errors);
            log.info("Save errors");            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public CompletableFuture<Void> writeErrorsAsync() {
    	return CompletableFuture.runAsync(
    			() -> writeErrors()
    			);
    }
    
    public List<Error> readErrors() {
    	List<Error> old;
    	try {
    		old = jsonMapper.readValue(new File(errorsFileName), new TypeReference<List<Error>>(){});
    		log.info("readed: " + old);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    	return old;
    }
}
