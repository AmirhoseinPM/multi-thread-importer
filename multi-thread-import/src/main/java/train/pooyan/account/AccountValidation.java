package train.pooyan.account;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import train.pooyan.core.EncryptionUtil;
import train.pooyan.core.EntityValidation;
import train.pooyan.customer.Customer;


public class AccountValidation implements EntityValidation<Account> {
	
    private Account account;

    private long recordNumber;
    private Map<String, String> fieldErrors = new HashMap<>();
    private boolean hasError;

    
    public AccountValidation(String line) {
    	String[] cols = line.split("[,;]");
                
        if (cols.length != 7) {
            addError("BadFormat", "Every line should have 7 columns not " + cols.length +" (" + line + ")");
        }
        
        recordNumber = recordNumberValidation(cols);

        long customerId = customerIdValidation(cols);
		Customer customer = new Customer();
		customer.setId(customerId);

		String number = numberValidation(cols);
		String type = typeValidation(cols);
        long balanceLimit = balanceLimitValidation(cols);        
        LocalDate open = openDateValidation(cols);        
        String balance = balanceValidation(cols, balanceLimit);
        
        if (getFieldErrors().size() > 0)
        	setHasError(true);
        else {
        	Account account =  new Account(
                    number,
                    type, 
                    customer,
                    balanceLimit,
                    open,  
                    balance);
            setAccount(account);
        }
    }

    private String balanceValidation(String[] cols, long limit) {
		String encryptedBalance = "";
        try {
        	encryptedBalance = cols[6].trim();
        	
        	if (encryptedBalance.length() < 1)
        		throw new IllegalArgumentException();
			// try to decrypt balance without exception.
        	String decryptedBalance = EncryptionUtil.decrypt(encryptedBalance);
			// try to parseLong without any exception.
        	long balance = Long.parseLong(decryptedBalance);
			// check balance limitation
        	if (
        			(balance < limit)
        		)
        		throw new IllegalArgumentException();
        	
        } catch (Exception exp) {
        	addError("BALANCE", "Balance not valid");	
        }
		return encryptedBalance;
	}
    
    private LocalDate openDateValidation(String[] cols) {
		LocalDate open = null;
		try {
	        List<Integer> date = Arrays.stream(cols[5].trim().split("[/-]"))
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
	        
	        open = LocalDate.of(date.get(0), date.get(1), date.get(2));
	        
	        if (open.compareTo(LocalDate.now()) > 0)
	        	throw new IllegalArgumentException();
	        
        } catch (Exception e) {
			// TODO: handle exception
            addError("OPEN_DATE", "Open date not valid");           
		}
		return open;
	}

	private String numberValidation(String[] cols) {
		String encryptedNumber = "";
        try {
        	encryptedNumber = cols[1].trim();
        	
        	if (encryptedNumber.length() < 1)
        		throw new IllegalArgumentException();
        	
        	
        	String number = EncryptionUtil.decrypt(encryptedNumber);
        	
        	if (
        			(number == null) ||
        			(!number.matches("^[0-9]{22}")) 
        		)
        		throw new IllegalArgumentException();

        } catch (Exception exp) {
        	addError("NUMBER", "Number not valid");	
        }
		return encryptedNumber;
	}
	
	private String typeValidation(String[] cols) {
		String type = "";
        try {
        	type = cols[2].trim();
        	if (!type.matches("^[123]$"))
        		throw new IllegalArgumentException();
        } catch (Exception exp) {
        	addError("TYPE", "Type not valid");
        } 
		return type;
	}
	
	private long customerIdValidation(String[] cols) {
		long customerId = 0;
        try {
        	customerId = Long.parseLong(cols[3].trim());
        	if (customerId < 1)
        		throw new IllegalArgumentException();
        } catch (Exception exp) {
        	addError("CUSTOMER_ID", "Customer id not valid");
        } 
		return customerId;
	}
	
	private long balanceLimitValidation(String[] cols) {
		long balanceLimit = 0;
        try {
        	balanceLimit = Long.parseLong(cols[4].trim());
        	if (balanceLimit < 1)
        		throw new IllegalArgumentException();
        } catch (IllegalArgumentException exp) {
        	addError("LIMIT", "Balance limit id not valid");
        } catch (Exception exp) {
        	addError("LIMIT", "Balance limit not found");	
        }
		return balanceLimit;
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

    public Account getValidated() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
    
    public void addError(String name, String message) {
    	this.fieldErrors.put(name, message);
    }


    public boolean hasError() {
        return hasError;
    }
    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    @Override
    public String toString() {
        return "AccountValidation{" +
                "account=" + account +
                ", recordNumber=" + recordNumber +
                ", fieldErrors=" + fieldErrors +
                ", hasError=" + hasError +
                '}';
    }
}
