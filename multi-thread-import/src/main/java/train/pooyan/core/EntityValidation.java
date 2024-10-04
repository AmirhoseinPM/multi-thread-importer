package train.pooyan.core;

import java.util.Map;

public interface EntityValidation<T> {
	/*
	* this interface shows abstraction of
	* validation classes that created for validate <T> entity
	* */
	T getValidated();
	long getRecordNumber();
	boolean hasError();
	Map<String, String> getFieldErrors();
}
