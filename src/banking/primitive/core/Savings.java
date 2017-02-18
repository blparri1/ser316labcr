package banking.primitive.core;
/**
Class: Savings

Description: The main account class that deals with savings accounts
*/
public class Savings extends Account {
	//Fix Coding Standard - 4
	private static final long _SERIALVERSIONUID = 111L;
	private int _numWithdraws = 0;

	/**
	Method: Savings Constructor
	Inputs: String name of the account
	Returns: none

	Description: Creates a new Savings object with a name
	*/
	public Savings(String name) {
		super(name);
	}

	/**
	Method: Savings Constructor
	Inputs: String name of the account, float balance of the account
	Returns: none

	Description: Creates a new Savings object with a name and the balance
	*/
	public Savings(String name, float balance) throws IllegalArgumentException {
		super(name, balance);
	}

	/**
	Method: deposit
	Inputs: float amount to be deposited
	Returns: boolean true if the deposit was successful, otherwise false

	Description: A deposit comes with a fee of 50 cents per deposit
	*/
	public boolean deposit(float amount) {
		if (getState() != State.CLOSED && amount > 0.0f) {
			balance = balance + amount - 0.50F;
			if (balance >= 0.0f) {
				setState(State.OPEN);
			}
			return true;
		}
		return false;
	}

	/**
	Method: withdrawl
	Inputs: float amount to be withdrawn
	Returns: boolean true if withdraw was successful

	Description: A withdrawal. After 3 withdrawals a fee of $1 is added to each withdrawal. An account whose balance dips below 0 is in an OVERDRAWN state
	*/
	public boolean withdraw(float amount) {
		if (getState() == State.OPEN && amount > 0.0f) {
			balance = balance - amount;
			_numWithdraws++;
			//Fix for Coding Standards - 8
			if (_numWithdraws > 3) {
				balance = balance - 1.0f;
			}
				
			
			if (balance < 0.0f) {
				setState(State.OVERDRAWN);
			}
			return true;
		}
		return false;
	}
	
	public String getType() { return "Checking"; }

	/**
	Method: toString
	Inputs: none
	Returns: Formatted string object

	Description: Returns a Savings object formatted as a string
	*/
	public String toString() {
		return "Savings: " + getName() + ": " + getBalance();
	}
}
