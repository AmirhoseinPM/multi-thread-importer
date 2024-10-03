package train.pooyan.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import train.pooyan.core.EncryptionUtil;


public class CustomerValidationTest {
	
	String validName = "amir";
	String validEncryptedName = EncryptionUtil.encrypt(validName);
	String invalidName = "";
	String invalidEncryptedName = EncryptionUtil.encrypt(invalidName);
	String invalidEncryptedNameFormat = "amir";
	
	String validSurname = "pooyan";
	String validEncryptedSurname = EncryptionUtil.encrypt(validSurname);
	String invalidSurname = "";
	String invalidEncryptedSurname = EncryptionUtil.encrypt(invalidSurname);
	String invalidEncryptedSurnameFormat = "pooyan";
	
	
	String validNationalId = "0123456789";
	String validEncryptedNationalId = EncryptionUtil.encrypt(validNationalId);
	String invalidNationalId = "0123456";
	String invalidEncryptedNationalId = EncryptionUtil.encrypt(invalidNationalId);
	String invalidEncryptedNationalIdFormat = "0123456789";
	
	
	
	@Test
	public void testGetAccountValidation_validLine() {
		
	  String line = "1," + "1," + validEncryptedName + "," + validEncryptedSurname + ",address,zipcode," + 
			  		 validEncryptedNationalId + ",2010-02-02";	  
	  CustomerValidation validation = new CustomerValidation(line);
	  
	 
	  // Assert that the account is created correctly
	  assertNotNull(validation.getValidated());
	  assertEquals(1, validation.getValidated().getId());
	  assertEquals(validEncryptedName, validation.getValidated().getName());
	  assertEquals(validEncryptedSurname, validation.getValidated().getSurname());
	  assertEquals("address", validation.getValidated().getAddress());
	  assertEquals(LocalDate.of(2010, 02, 02), validation.getValidated().getBirthDate());
	  assertEquals(validEncryptedNationalId, validation.getValidated().getNationalId());
	 
	  // Assert that there are no errors
	  assertEquals(0, validation.getFieldErrors().size());
	  assertFalse(validation.hasError());
	}

	@Test
	public void testGetAccountValidation_invalidLine_notEnoughColumns() {
		  String line = "1," + "1," + validEncryptedName + "," + validEncryptedSurname + ",address,zipcode," + 
		  		 validEncryptedNationalId;		 
		  CustomerValidation validation = new CustomerValidation(line);
		
		  // Assert that there are BadFormat error
		  assertNull(validation.getValidated());
		  assertTrue(validation.getFieldErrors().containsKey("BadFormat"));
		  assertTrue(validation.hasError());

	}

	@Test
	public void testGetAccountValidation_invalidLine_blankLine() {
		  String line = "    ";		 
		 
		  CustomerValidation validation = new CustomerValidation(line);
		
		  // Assert that there are BadFormat error
		  assertNull(validation.getValidated());
		  assertTrue(validation.getFieldErrors().containsKey("BadFormat"));
		  assertTrue(validation.hasError());
	}
	
	@Test
	public void testGetAccountValidation_invalidLine_notNullColumns() {
		String line = "   ,      ,           ,         ,      ,      ,    ,    ";
		  CustomerValidation validation = new CustomerValidation(line);
		

		  // Assert that there are Null error for all fields
		  assertTrue(validation.getFieldErrors().containsKey("CUSTOMER_ID"));
		  assertTrue(validation.getFieldErrors().containsKey("NAME"));
		  assertTrue(validation.getFieldErrors().containsKey("SURNAME"));
		  assertTrue(validation.getFieldErrors().containsKey("ADDRESS"));
		  assertTrue(validation.getFieldErrors().containsKey("ZIPCODE"));
		  assertTrue(validation.getFieldErrors().containsKey("NATIONAL_ID"));
		  assertTrue(validation.getFieldErrors().containsKey("BIRTH_DATE"));
		  assertTrue(validation.getFieldErrors().containsKey("RECORD_NUMBER"));
		  assertTrue(validation.hasError());
		  assertNull(validation.getValidated());
	}
	
	@Test
	public void testGetAccountValidation_invalidCustomerId_string() {
		String line = "1," + "uhisuodfv," + validEncryptedName + "," + validEncryptedSurname + ",address,zipcode," + 
				 validEncryptedNationalId + ",2010-02-02";		 
		  CustomerValidation validation = new CustomerValidation(line);
		
		// Assert that there are BadFormat error
		assertNull(validation.getValidated());
		assertTrue(validation.getFieldErrors().containsKey("CUSTOMER_ID"));
		assertTrue(validation.hasError());
	}
	
	@Test
	public void testGetAccountValidation_invalidCustomerId_zero() {
		String line = "1," + "0," + validEncryptedName + "," + validEncryptedSurname + ",address,zipcode," + 
				 validEncryptedNationalId + ",2010-02-02";		 
		  CustomerValidation validation = new CustomerValidation(line);
		
		// Assert that there are BadFormat error
		assertNull(validation.getValidated());
		assertTrue(validation.getFieldErrors().containsKey("CUSTOMER_ID"));
		assertTrue(validation.hasError());
	}
	
	@Test
	public void testGetAccountValidation_invalidName_tooShort() {
		String line = "1," + "1," + invalidEncryptedName + "," + validEncryptedSurname + ",address,zipcode," + 
				 validEncryptedNationalId + ",2010-02-02";		 
		  CustomerValidation validation = new CustomerValidation(line);
		
		// Assert that there are BadFormat error
		assertNull(validation.getValidated());
		assertTrue(validation.getFieldErrors().containsKey("NAME"));
		assertTrue(validation.hasError());
	}
	
	@Test
	public void testGetAccountValidation_invalidName_format() {
		String line = "1," + "1," + invalidEncryptedNameFormat + "," + validEncryptedSurname + ",address,zipcode," + 
				 validEncryptedNationalId + ",2010-02-02";		 
		  CustomerValidation validation = new CustomerValidation(line);
		
		// Assert that there are BadFormat error
		assertNull(validation.getValidated());
		assertTrue(validation.getFieldErrors().containsKey("NAME"));
		assertTrue(validation.hasError());
	}
	
	@Test
	public void testGetAccountValidation_invalidSurname_tooShort() {
		String line = "1," + "1," + validEncryptedName + "," + invalidEncryptedSurname + ",address,zipcode," + 
				 validEncryptedNationalId + ",2010-02-02";		 
		  CustomerValidation validation = new CustomerValidation(line);
		
		// Assert that there are BadFormat error
		assertNull(validation.getValidated());
		assertTrue(validation.getFieldErrors().containsKey("SURNAME"));
		assertTrue(validation.hasError());
	}
	
	@Test
	public void testGetAccountValidation_invalidSurname_format() {
		String line = "1," + "1," + validEncryptedName + "," + invalidEncryptedSurnameFormat + ",address,zipcode," + 
				 validEncryptedNationalId + ",2010-02-02";		 
		  CustomerValidation validation = new CustomerValidation(line);
		
		// Assert that there are BadFormat error
		assertNull(validation.getValidated());
		assertTrue(validation.getFieldErrors().containsKey("SURNAME"));
		assertTrue(validation.hasError());
	}
	

	@Test
	public void testGetAccountValidation_invalidNationalId_tooShort() {
		String line = "1," + "1," + validEncryptedName + "," + validEncryptedSurname + ",address,zipcode," + 
				 invalidEncryptedNationalId + ",2010-02-02";		 
		  CustomerValidation validation = new CustomerValidation(line);
		
		// Assert that there are BadFormat error
		assertNull(validation.getValidated());
		assertTrue(validation.getFieldErrors().containsKey("NATIONAL_ID"));
		assertTrue(validation.hasError());
	}
	
	@Test
	public void testGetAccountValidation_invalidBirthDate_lessThan1995() {
		String line = "1," + "1," + validEncryptedName + "," + validEncryptedSurname + ",address,zipcode," + 
				 validEncryptedNationalId + ",1990-02-02";		 
		  CustomerValidation validation = new CustomerValidation(line);
		
		// Assert that there are BadFormat error
		assertNull(validation.getValidated());
		assertTrue(validation.getFieldErrors().containsKey("BIRTH_DATE"));
		assertTrue(validation.hasError());
	}
	
	@Test
	public void testGetAccountValidation_invalidBirthDate_moreThanNow() {
		String line = "1," + "1," + validEncryptedName + "," + validEncryptedSurname + ",address,zipcode," + 
				 validEncryptedNationalId + ",2990-02-02";		 
		  CustomerValidation validation = new CustomerValidation(line);
		
		// Assert that there are BadFormat error
		assertNull(validation.getValidated());
		assertTrue(validation.getFieldErrors().containsKey("BIRTH_DATE"));
		assertTrue(validation.hasError());
	}
	
	@Test
	public void testGetAccountValidation_invalidNationalId_format() {
		String line = "1," + "1," + validEncryptedName + "," + validEncryptedSurname + ",address,zipcode," + 
				 validEncryptedNationalId + ",20100202";		 
		  CustomerValidation validation = new CustomerValidation(line);
		
		// Assert that there are BadFormat error
		assertNull(validation.getValidated());
		assertTrue(validation.getFieldErrors().containsKey("BIRTH_DATE"));
		assertTrue(validation.hasError());
	}
	
	
	
}
