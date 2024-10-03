package train.pooyan.core;

import java.time.LocalDate;

import train.pooyan.customer.Customer;
import train.pooyan.error.Error;
import train.pooyan.error.ErrorWriter;

public abstract class LineProcessor<T> {
	
	

	public abstract EntityValidation<T> getValidation(String line);
	public abstract String getFileName();
	public abstract ErrorWriter getErrorWriter();
	public abstract T saveEntity(T entity);
	
	
	public void processLine(String line) {
        
        EntityValidation<T> validation = getValidation(line);        
        importOrError(validation); 
    }
	
	 public void importOrError(EntityValidation<T> validation) {
	        if (validation.hasError())
	            errorHandling(validation, "400", "Validation", 
	            		validation.getFieldErrors().toString());
	        else {
	            try {
	                saveEntity(
	                		(T) validation.getValidated());
	            } catch (Exception exception) {
	                errorHandling(validation, "500", "SQL", 
	                			"data base exception on saving record");
	            }
	        }
	    }
	    
	
    public void errorHandling(EntityValidation validation, String code, String classification, String discription) {
        Error error = new Error(
                getFileName(),
                validation.getRecordNumber(),
                code,
                classification,
                discription,
                LocalDate.now().toString()
        );
        getErrorWriter().addError(error);
    }
}
