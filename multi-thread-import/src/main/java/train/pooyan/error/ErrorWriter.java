package train.pooyan.error;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class ErrorWriter {
    /*
    * this bean responsible for save errors of several tasks synchronously
    * and finally (after all tasks completed) write these errors to json file using JsonMapper
    * */
    @Value("${errors.file.json.fileName}")
    private String errorsFileName;
    private final JsonMapper jsonMapper = new JsonMapper();
    private final Set<Error> errors = new HashSet<>();

    public synchronized void addError(Error error) {
        errors.add(error);
    }

    public void writeErrors() {
        try {
            jsonMapper.writerWithDefaultPrettyPrinter()
            		.writeValue(new File(errorsFileName), errors);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
