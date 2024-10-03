package train.pooyan.core;

import java.util.Map;

public interface EntityValidation<T> {
	T getValidated();
	long getRecordNumber();
	boolean hasError();
	Map<String, String> getFieldErrors();
}
