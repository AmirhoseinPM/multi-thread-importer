package train.pooyan.core;

import java.time.LocalDate;

import train.pooyan.error.Error;
import train.pooyan.error.ErrorWriter;

public abstract class LineProcessor<T> {
	/*
	 * this class implement core functionality of processing each line of a csv files,
	 * that map to entity of <T>.
	 * to doing that
	 * 1. first validate entity using an EntityValidation<T>
	 * 2. then
	 * 		if validated, insert entity it to database,
	 * 		otherwise write an error to json file.
	 * */
	public abstract EntityValidation<T> getValidation(String line);
	public abstract String getFileName();
	public abstract ErrorWriter getErrorWriter();
	public abstract T saveEntity(T entity);

	public void processLine(String line) {
		// process line and validate and maps it to entity
        EntityValidation<T> validation = getValidation(line);

		// insert entity to database or write to json
        importOrError(validation); 
    }
	
	 public void importOrError(EntityValidation<T> validation) {
	        if (validation.hasError())
	            errorHandling(validation, "400", "Validation", 
	            		validation.getFieldErrors().toString());
	        else {
	            try {
	                saveEntity(
	                		validation.getValidated());
	            } catch (Exception exception) {
	                errorHandling(validation, "500", "SQL", 
	                			"data base exception on saving record");
	            }
	        }
	 }

    public void errorHandling(EntityValidation<T> validation, String code, String classification, String discription) {
        Error error = new Error(
                getFileName(), validation.getRecordNumber(), code,
                classification, discription, LocalDate.now().toString());

        getErrorWriter().addError(error);
    }
}
