package banking.primitive.core;


public class AccountServerFactory {

	protected static AccountServerFactory singleton = null;

	/**
	Method: AccountServerFactor constructor
	Inputs: none
	Returns: none

	Description: Does nothing
	*/
	protected AccountServerFactory() {

	}

	/**
	Method: getMe
	Inputs: none
	Returns: The AccountServerFactory object

	Description: Returns the AccountServerFactory object that is a singleton
	*/
	public static AccountServerFactory getMe() {
		if (singleton == null) {
			singleton = new AccountServerFactory();
		}

		return singleton;
	}

	/**
	Method: lookup
	Inputs: none
	Returns: AccountServer object

	Description: returns the ServerSolution object
	*/
	public AccountServer lookup() {
		return new ServerSolution();
	}
}
