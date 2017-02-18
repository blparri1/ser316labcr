/*
  File: Account.java
  Author: kevinagary
  Date:	Spring 2017
  
  Description: Abstract account class for inheritance 
*/
package banking.primitive.core;
/**
Class:	Account

Description: The abstract class that other account type classes
will use to inherit from.
*/
public abstract class Account implements java.io.Serializable {
    protected enum State {
        OPEN, CLOSED, OVERDRAWN
    };

    /**
    Method: Account Constructor
    Inputs: String n that is the name of the accont
    Returns: none

    Description: Account constructor that will create a new account with a name, and set the state to open
    */
    protected Account(String n) {
        name = n;
        _state = State.OPEN;
    }

    /**
    Method: Account Constructor
    Inputs: String n that is the name of the account, float b is the balance
    Returns: none

    Description: Account constructor that will create a new account with a name, assign the balance, and set the state to open
    */
    protected Account(String n, float b) {
        this(n); // <-- MAKE THIS CHANGE HERE
        balance = b;
    }
    /**
     * @return balance in the Account
     */
    public final float getBalance() {
        return balance;
    }
    
    /**
     * @return name of the Account
     */
    public final String getName() {
        return name;
    }

    /**
    Method: deposit
    Inputs: float amount is a deposit and must be > 0
    Returns:true if the deposit was successful, false if not due to amount or invalid state

    Description: Adds money to an account. May not be done if the account is CLOSED
    */

    public abstract boolean deposit(float amount);

    /** 
    Method: withdraw
    Inputs: float amount is a withdrawal and must be > 0
    Returns: true if the deposit was successful, false if not due to amount or  invalid state

    Description: Takes money out of an account. If the balance falls below 0 then the account is moved to an OVERDRAWN state
    */
    public abstract boolean withdraw(float amount);

    /**
     * @return either "Checking" or "Savings"
     */
    public abstract String getType();

    protected final State getState() {
        return _state;
    }

    protected final void setState(State s) {
        _state = s;
    }

    /**
    Method: toString
    Inputs: none
    Returns: Formatted String

    Description: Returns a formatted Account object as a string
    */
    public String toString() {
        return "Account " + name + " has $" + balance + "and is " + getState()
                + "\n";
    }
    private static final long SERIALVERSIONUID = 1L;
    protected float balance = 0.0F;
    protected String name;
    //Fix Coding Standard - 4
    private State _state;
}
