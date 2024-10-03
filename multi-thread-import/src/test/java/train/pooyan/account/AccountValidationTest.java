package train.pooyan.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import train.pooyan.core.EncryptionUtil;

public class AccountValidationTest {
	
	  String validNumber = "0012345678901234567890";
	  String validEncryptedNumber = EncryptionUtil.encrypt(validNumber);
	  String invalidNumber = "123456789123456789";
	  String invalidEncryptedNumber = EncryptionUtil.encrypt(invalidNumber);
	  String invalidFormatEncryptedNumber = "123456789123456789";
	  
	  long validBalanceLimit = 100;
	  String validBalance = "1000";
	  String validEncryptedBalance = EncryptionUtil.encrypt(validBalance);
	  String invalidEncryptedBalance = EncryptionUtil.encrypt("0");
	  String invalidEncryptedBalanceFormat = "1000";
	  
	
	@Test
	public void testGetAccountValidation_validLine() {
		
	  String line = "1," + validEncryptedNumber + ",1,10," + 
			  		String.valueOf(validBalanceLimit) + ",2023-10-02," + validEncryptedBalance;
	  
	  AccountValidation validation = new AccountValidation(line);
	 
	  // Assert that the account is created correctly
	  assertNotNull(validation.getValidated());
	  assertEquals(validEncryptedNumber, validation.getValidated().getNumber());
	  assertEquals("1", validation.getValidated().getType());
	  assertEquals(10, validation.getValidated().getCustomerId());
	  assertEquals(validBalanceLimit, validation.getValidated().getBalanceLimit());
	  assertEquals(LocalDate.of(2023, 10, 2), validation.getValidated().getOpenDate());
	  assertEquals(validEncryptedBalance, validation.getValidated().getBalance());
	 
	  // Assert that there are no errors
	  assertEquals(0, validation.getFieldErrors().size());
	  assertFalse(validation.hasError());
	}

	@Test
	public void testGetAccountValidation_invalidLine_notEnoughColumns() {
	  String line = "1," + validEncryptedNumber + ",1,10," + 
		  		validBalanceLimit + ",2023-10-02,";
	  AccountValidation validation = new AccountValidation(line);
	 
	 
	  // Assert that there is an error for bad format
	  assertNull(validation.getValidated());
	  assertTrue(validation.getFieldErrors().containsKey("BadFormat"));
	  assertTrue(validation.hasError());
	}
	
	@Test
	public void testGetAccountValidation_invalidLine_notNullColumns() {
	  String line = "        ,          ,          ,          ,           ,         ,      ";
	  AccountValidation validation = new AccountValidation(line);
	 
	  // Assert that there is an error for bad format
	  assertTrue(validation.getFieldErrors().containsKey("BALANCE"));
	  assertTrue(validation.getFieldErrors().containsKey("OPEN_DATE"));
	  assertTrue(validation.getFieldErrors().containsKey("NUMBER"));
	  assertTrue(validation.getFieldErrors().containsKey("TYPE"));
	  assertTrue(validation.getFieldErrors().containsKey("CUSTOMER_ID"));
	  assertTrue(validation.getFieldErrors().containsKey("LIMIT"));
	  assertTrue(validation.getFieldErrors().containsKey("RECORD_NUMBER"));
	  assertTrue(validation.hasError());
	  assertNull(validation.getValidated());
	  }

	@Test
	public void testNumberValidation_invalidNumber_tooShort() {
      String line = "1," + invalidEncryptedNumber + ",1,10," + 
	  		validBalanceLimit + ",2023-10-02," + validEncryptedBalance;
	  
	  AccountValidation validation = new AccountValidation(line);
	 
	  // Assert that there is an error for invalid number
	  assertNull(validation.getValidated());
	  assertTrue(validation.getFieldErrors().containsKey("NUMBER"));
	  assertTrue(validation.hasError());
	}

	@Test
	public void testNumberValidation_invalidNumber_invalidFormat() {
      String line = "1," + invalidFormatEncryptedNumber + ",1,10," + 
  	  		validBalanceLimit + ",2023-10-02," + validEncryptedBalance;
  	  
	  AccountValidation validation = new AccountValidation(line);
	 
	  // Assert that there is an error for invalid number
	  assertNull(validation.getValidated());
	  assertTrue(validation.getFieldErrors().containsKey("NUMBER"));
	  assertTrue(validation.hasError());
	}
	
	@Test
	public void testNumberValidation_invalidType_number() {

	  
	  String line = "1," + validEncryptedNumber + ",4,10," + 
  	  		validBalanceLimit + ",2023-10-02," + validEncryptedBalance;	  	  
	  AccountValidation validation = new AccountValidation(line);
	  // Assert that there is an error for invalid type
	  assertNull(validation.getValidated());
	  assertTrue(validation.getFieldErrors().containsKey("TYPE"));
	  assertTrue(validation.hasError());
	}
	
	@Test
	public void testNumberValidation_invalidType_string() {
		
      String line = "1," + validEncryptedNumber + ",CURRENT,10," + 
  	  		validBalanceLimit + ",2023-10-02," + validEncryptedBalance;  	  
	  AccountValidation validation = new AccountValidation(line);
	  // Assert that there is an error for invalid type
	  assertNull(validation.getValidated());
	  assertTrue(validation.getFieldErrors().containsKey("TYPE"));
	  assertTrue(validation.hasError());

	}
	
	@Test
	public void testNumberValidation_invalidCustomerId_string() {
		  String line = "1," + validEncryptedNumber + ",2,hfn," + 
	  	  		validBalanceLimit + ",2023-10-02," + validEncryptedBalance;	  	  
		  AccountValidation validation = new AccountValidation(line);
		  // Assert that there is an error for invalid customerId
		  assertNull(validation.getValidated());
		  assertTrue(validation.getFieldErrors().containsKey("CUSTOMER_ID"));
		  assertTrue(validation.hasError()); 
		  

	}
	
	@Test
	public void testNumberValidation_invalidCustomerId_zero() {
		  

		  String line = "1," + validEncryptedNumber + ",2,0," + 
	  	  		validBalanceLimit + ",2023-10-02," + validEncryptedBalance;	  	  
		  AccountValidation validation = new AccountValidation(line);
		  // Assert that there is an error for invalid customer id
		  assertNull(validation.getValidated());
		  assertTrue(validation.getFieldErrors().containsKey("CUSTOMER_ID"));
		  assertTrue(validation.hasError());
	}
	
	@Test
	public void testNumberValidation_invalidLimit_string() {
		  String line = "1," + validEncryptedNumber + ",2,1," + 
	  	  		"jkbfv" + ",2023-10-02," + validEncryptedBalance;	  	  
		  AccountValidation validation = new AccountValidation(line);
		  // Assert that there is an error for invalid limit
		  assertNull(validation.getValidated());
		  assertTrue(validation.getFieldErrors().containsKey("LIMIT"));
		  assertTrue(validation.hasError()); 		 
	}
	

	@Test
	public void testNumberValidation_invalidLimit_zero() {

		  String line = "1," + validEncryptedNumber + ",2,1," + 
	  	  		"0" + ",2023-10-02," + validEncryptedBalance;	  	  
		  AccountValidation validation = new AccountValidation(line);
		  // Assert that there is an error for invalid limit
		  assertNull(validation.getValidated());
		  assertTrue(validation.getFieldErrors().containsKey("LIMIT"));
		  assertTrue(validation.hasError());
	}
	
	@Test
	public void testNumberValidation_invalidOpenDate_future() {
		  String line = "1," + validEncryptedNumber + ",2,1," + 
	  	  		validBalanceLimit + ",2025-10-02," + validEncryptedBalance;	  	  
		  AccountValidation validation = new AccountValidation(line);
		  // Assert that there is an error for invalid open date
		  assertNull(validation.getValidated());
		  assertTrue(validation.getFieldErrors().containsKey("OPEN_DATE"));
		  assertTrue(validation.hasError()); 
		  
		  
	}
	
	@Test
	public void testNumberValidation_invalidOpenDate_onlyNumber() {
		  String line = "1," + validEncryptedNumber + ",2,1," + 
				  validBalanceLimit + ",2023," + validEncryptedBalance;	  	  
		  AccountValidation validation = new AccountValidation(line);
		  // Assert that there is an error for invalid open date
		  assertNull(validation.getValidated());
		  assertTrue(validation.getFieldErrors().containsKey("OPEN_DATE"));
		  assertTrue(validation.hasError());
		  
	}
	
	@Test
	public void testNumberValidation_invalidOpenDate_badMonthNumber() {
		  String line = "1," + validEncryptedNumber + ",2,1," + 
				  validBalanceLimit + ",2023-22-11," + validEncryptedBalance;	  	  
		  AccountValidation validation = new AccountValidation(line);
		  // Assert that there is an error for invalid open date
		  assertNull(validation.getValidated());
		  assertTrue(validation.getFieldErrors().containsKey("OPEN_DATE"));
		  assertTrue(validation.hasError());		  
	}
	
	@Test
	public void testNumberValidation_invalidBalance_lessThanLimit() {
		  String line = "1," + validEncryptedNumber + ",2,1," + 
	  	  		validBalanceLimit + ",2022-10-02," + invalidEncryptedBalance;	  	  
		  AccountValidation validation = new AccountValidation(line);
		  // Assert that there is an error for invalid balance
		  assertNull(validation.getValidated());
		  assertTrue(validation.getFieldErrors().containsKey("BALANCE"));
		  assertTrue(validation.hasError()); 
		  
	}
	
	@Test
	public void testNumberValidation_invalidBalance_nonEncrypted() {

		  String line = "1," + validEncryptedNumber + ",2,1," + 
				  validBalanceLimit + ",2022-10-10," + invalidEncryptedBalanceFormat;	  	  
		  AccountValidation validation = new AccountValidation(line);
		  // Assert that there is an error for invalid balance
		  assertNull(validation.getValidated());
		  assertTrue(validation.getFieldErrors().containsKey("BALANCE"));
		  assertTrue(validation.hasError());
		  
	}
}
