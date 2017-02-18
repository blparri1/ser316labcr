/*
  File:	Checking.java
  Author: kevinagary
  Date:	Spring 2017
  
  Description: Actual implementation of the checking account for clients
*/
package banking.primitive.core;

public class Checking extends Account {
	//Fix Coding Standard - 4
	private static final long _serialVersionUID = 11L;
	private int _numWithdraws = 0;
	
	/**
	Method: Checking Constructor
	Inputs: String name
	Returns: none

	Description: Creates a new Checking object with the name of the account 
	*/
	private Checking(String name) {
		super(name);
	}

	/**
	Method: createChecking
	Inputs: String name
	Returns: A new Checking object

	Description: Creates a new checking object with the parameter name
	*/
    public static Checking createChecking(String name) {
        return new Checking(name);
    }

    /**
    Method: Checking Constructor
    Inputs: String name, float balance
    Returns: none

    Description: Creates a new Checking object with the name and balance for the account
    */
	public Checking(String name, float balance) {
		super(name, balance);
	}

	/**
	Method: deposit
	Inputs: float amount to be deposited
	Returns: boolean true if successful, false if not

	Description: A deposit may be made unless the Checking account is closed
	*/
	public boolean deposit(float amount) {
		if (getState() != State.CLOSED && amount > 0.0f) {
			balance = balance + amount;
			if (balance >= 0.0f) {
				setState(State.OPEN);
			}
			return true;
		}
		return false;
	}

	/**
	Method: Withdrawal
	Inputs: float amount to be withdrawn
	Returns: boolean true if successful, false if not

	Description: After 10 withdrawals a fee of $2 is charged per transaction You may continue to withdraw an overdrawn account until the balance is below -$100
	*/
	public boolean withdraw(float amount) {
		if (amount > 0.0f) {		
			// KG: incorrect, last balance check should be >=
			if (getState() == State.OPEN || (getState() == State.OVERDRAWN && balance > -100.0f)) {
				balance = balance - amount;
				_numWithdraws++;
				if (_numWithdraws > 10)
					balance = balance - 2.0f;
				if (balance < 0.0f) {
					setState(State.OVERDRAWN);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Getter
	 */
	public String getType() { 
		return "Checking"; 
	}
	
	/**
	Method: toString
	Inputs: none
	Returns: String proper formatted String object

	Description: Will return a formatted Checking object as a String
	*/
	public String toString() {
		return "Checking: " + getName() + ": " + getBalance();
	}
}
