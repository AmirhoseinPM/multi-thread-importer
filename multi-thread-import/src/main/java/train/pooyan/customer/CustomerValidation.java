package train.pooyan.customer;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import train.pooyan.core.EncryptionUtil;
import train.pooyan.core.EntityValidation;


public class CustomerValidation implements EntityValidation<Customer> {
	
    private Customer customer;
    private long recordNumber;
    private Map<String, String> fieldErrors = new HashMap<>();
    private boolean hasError;

    public CustomerValidation(String line) {
    	
        String[] cols = line.split("[,;]"); 
        
        if (cols.length != 8) {
            addError("BadFormat", "Every line should have 8 columns not " + cols.length +" (" + line + ")");
        }
        
        recordNumber =  recordNumberValidation(cols);
        long customerId = customerIdValidation(cols);
        String name = nameValidation(cols); 
        String surName = surnameValidation(cols); 
        String address = addressValidation(cols);        
        String zipCode = zipCodeValidation(cols);
        String nationalId = nationalIdValidation(cols);        
        LocalDate birth = birthDateValidation(cols);
        
        if (getFieldErrors().size() > 0)
        	setHasError(true);
        else {
        	Customer customer = new Customer(
                    customerId, name, surName, address, zipCode,  nationalId, birth);            
            setCustomer(customer);
        }
    }
    
	private LocalDate birthDateValidation(String[] cols) {
		LocalDate birth = null;
		try {
	        List<Integer> date = Arrays.stream(cols[7].trim().split("[/-]"))
	                .map(Integer::parseInt).toList();
	        
	        if (
	        		(date.size() != 3) ||
	        		(date.get(0) < 1995) ||
	        		(date.get(1) > 12) ||
	        		(date.get(1) < 1) ||
	        		(date.get(2) > 31) ||
	        		(date.get(2) < 1)
	        	) {	        		
	        	throw new IllegalArgumentException();
	        }
	        
	        birth = LocalDate.of(date.get(0), date.get(1), date.get(2));
	        
	        if (birth.compareTo(LocalDate.now()) > 0)
	        	throw new IllegalArgumentException();
	        
        } catch (Exception e) {
			// TODO: handle exception
            addError("BIRTH_DATE", "Birth date not valid");           
		}
		return birth;
	}

	private String nationalIdValidation(String[] cols) {
		String encryptedNationalId = "";
		try {
			encryptedNationalId = cols[6].trim();
			if (encryptedNationalId.length() < 1)
				throw new IllegalArgumentException();
			
			String nationalId = EncryptionUtil.decrypt(encryptedNationalId);
			
			if (!nationalId.matches("^[0-9]{10}$"))
				throw new IllegalArgumentException();
			
		} catch (Exception e) {
			// TODO: handle exception
            addError("NATIONAL_ID", "National Id not valid");           
		}
		return encryptedNationalId;
	}

	private String zipCodeValidation(String[] cols) {
		String zipCode = "";
		try {
			zipCode = cols[5].trim();
			if (zipCode.length() < 3)
				throw new IllegalArgumentException();
		} catch (Exception e) {
			// TODO: handle exception
            addError("ZIPCODE", "ZipCode not valid");           
		}
		return zipCode;
	}

	private String addressValidation(String[] cols) {
		String address = "";
		try {
			address = cols[4].trim();
			if (address.length() < 3)
				throw new IllegalArgumentException();
		} catch (Exception e) {
			// TODO: handle exception
            addError("ADDRESS", "Address not valid");           
		}
		return address;
	}

	private String surnameValidation(String[] cols) {
		String encryptedSurName = "";
		try {
			encryptedSurName = cols[3].trim();
			if (encryptedSurName.length() < 3)
				throw new IllegalArgumentException();
			
			String surName = EncryptionUtil.decrypt(encryptedSurName);
			
			if ((surName == null) || (surName.length() < 1))
				throw new IllegalArgumentException();
			
		} catch (Exception e) {
            addError("SURNAME", "Surname not valid");           
		}
		return encryptedSurName;
	}

	private String nameValidation(String[] cols) {
		String encryptedName = "";
		try {
			encryptedName = cols[2].trim();
			if (encryptedName.length() < 1)
				throw new IllegalArgumentException();
			String name = EncryptionUtil.decrypt(encryptedName);
			
			if ((name == null) || (name.length() < 1))
				throw new IllegalArgumentException();
			
		} catch (Exception e) {
			addError("NAME", "Name not valid");
		}
		return encryptedName;
	}

	private long customerIdValidation(String[] cols) {
		long customerId = 0;
		try {
			customerId = Long.parseLong(cols[1].trim());
			if (customerId < 1)
				throw new IllegalArgumentException();
        } catch (Exception e) {
			// TODO: handle exception
            addError("CUSTOMER_ID", "Customer Id not valid");           
		}
		return customerId;
	}

	private long recordNumberValidation(String[] cols) {
		long recordNumber = 0;
		try {
        	recordNumber = Long.parseLong(cols[0].trim());
        } catch (Exception e) {
			// TODO: handle exception
            addError("RECORD_NUMBER", "Record number not valid");           
		}
		return recordNumber;
	}

    public void addError(String name, String message) {
        fieldErrors.put(name, message);
    }

    public Customer getValidated() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getRecordNumber() {
        return this.recordNumber;
    }


    public void setRecordNumber(long recordNumber) {
        this.recordNumber = recordNumber;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }


    public boolean hasError() {
        return hasError;
    }
    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    @Override
    public String toString() {
        return "CustomerValidation{" +
                "customer=" + customer +
                ", recordNumber=" + recordNumber +
                ", fieldErrors=" + fieldErrors +
                ", hasError=" + hasError +
                '}';
    }
}
