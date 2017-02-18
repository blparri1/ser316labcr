/*
  File: AccountServerTest.java
  Author: kevinagary
  Date:	Spring 2017
  
  Description: Tests the functionality of the AccountServer class
*/
package banking.primitive.core;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountServerTest {
	//Fix Coding Standard - 4
	private static AccountServer _accountServer = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		_accountServer = AccountServerFactory.getMe().lookup();
	}

	@Before
	public void setUp() throws Exception {
		_accountServer.newAccount("Checking", "CheckingTest1", 100.0f);
		_accountServer.newAccount("Savings", "SavingsTest1", 200.0f);
		_accountServer.newAccount("Checking", "CheckingTest2", 300.0f);
		_accountServer.newAccount("Savings", "SavingsTest2", 400.0f);
	}

	/**
	 * testNewAccountsType checks for valid input types in the 1st parameter
	 */
	@Test (expected=java.lang.IllegalArgumentException.class)
	public void testNewAccountType() {
        assertTrue(!_accountServer.newAccount("BLAH", "BadChecking1", 10.0f));
        assertNull(_accountServer.getAccount("BadChecking1"));
	}
	
    /**
     * testNewAccountsBalance checks for valid input values in the 3rd parameter
     */
    @Test (expected=java.lang.IllegalArgumentException.class)
    public void testNewAccountBalance() {
      assertTrue(!_accountServer.newAccount("Checking", "BadChecking2", -10.0f));
      assertTrue(!_accountServer.newAccount("Savings", "BadSavings2", -10.0f));
      assertNull(_accountServer.getAccount("BadChecking2"));
      assertNull(_accountServer.getAccount("BadChecking2"));
    }   

    /**
     * testNewAccountDuplicate checks that accounts with duplicate names can't be added
     */
    @Test
    public void testNewAccountDuplicate() {
        assertTrue(!_accountServer.newAccount("Savings", "CheckingTest1", 500.0f));
        assertTrue(!_accountServer.newAccount("Savings", "SavingsTest1", 500.0f));
    }
    
	/**
	 * testNewAccount checks the behavior of adding an account
	 * Question: What should the BVA result in here?
	 */
	@Test
	public void testNewAccount() {
		// Get all the accounts
		List<Account> accounts = _accountServer.getAllAccounts();
		
		// Add the accounts
		if (_accountServer.newAccount("Checking",  "Checkingnewtest1", 100.0f) &&
		    _accountServer.newAccount("Savings",  "Savingsnewtest1", 100.0f)) {
		
		    // Now when I get the accounts again there should be 2 new ones
	        List<Account>updatedAccounts = _accountServer.getAllAccounts();
	        // this assert checks that we didn't blow away one that shouldn't be touched
	        assertTrue(updatedAccounts.containsAll(accounts));
	        // These check what we put in got in
	        assertNotNull(_accountServer.getAccount("Checkingnewtest1"));
	        assertNotNull(_accountServer.getAccount("Savingsnewtest1"));
		} else {
		    fail("failed to add new acocunts");
		}
	}

	@Test
	public void testGetAccount() {
		_accountServer.newAccount("Checking", "CheckingGetTest", 100.0f);
		
		// how were each of these tests arrived at?
		assertNotNull(_accountServer.getAccount("CheckingGetTest"));
		assertNull(_accountServer.getAccount(null));
		assertNull(_accountServer.getAccount(""));
		assertNull(_accountServer.getAccount("  blah   blah   "));
		assertNull(_accountServer.getAccount("CheckingGetTes"));
		assertNull(_accountServer.getAccount("heckingGetTest"));
		assertNull(_accountServer.getAccount("CheckingGetTest2"));
		assertNull(_accountServer.getAccount("Checking GetTest"));
		assertNull(_accountServer.getAccount("checkinggettest"));
	}
}
