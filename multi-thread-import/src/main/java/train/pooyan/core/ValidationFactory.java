package train.pooyan.core;


public interface ValidationFactory<T> {
	 EntityValidation<T> getValidation(String line);

}
