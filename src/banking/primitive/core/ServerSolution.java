/*
  File:	ServerSolution.java
  Author: kevinagary
  Date:	Spring 2017
  
  Description: Assists in the creation and usability of servers
*/
package banking.primitive.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

import banking.primitive.core.Account.State;
/**
Class: ServerSolution

Description: Supports the overall program with creation and usage of accounts
*/
class ServerSolution implements AccountServer {
	//Fix Coding Standards - 6

	/**
	Method: ServerSolution Constructor
	Inputs: none
	Returns: none

	Description: Creates the ServerSolution object
	*/
	public ServerSolution() {
		accountMap = new HashMap<String,Account>();
		File file = new File(fileName);
		ObjectInputStream in = null;
		try {
			if (file.exists()) {
				System.out.println("Reading from file " + fileName + "...");
				in = new ObjectInputStream(new FileInputStream(file));

				Integer sizeI = (Integer) in.readObject();
				int size = sizeI.intValue();
				for (int i=0; i < size; i++) {
					Account acc = (Account) in.readObject();
					//Fix for Coding Standards - 8
					if (acc != null) {
						accountMap.put(acc.getName(), acc);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
	
	/**
	Method: newAccountFactory
	Inputs: String type of account, String name of the account, float balance of the account
	Returns: boolean true if successful, false otherwise

	Description: Will create a new account based on the parameters passed
	*/
	private boolean newAccountFactory(String type, String name, float balance)
		throws IllegalArgumentException {
		
		Account acc;
		if ("Checking".equals(type)) {
			acc = new Checking(name, balance);

		} else if ("Savings".equals(type)) {
			acc = new Savings(name, balance);

		} else {
			throw new IllegalArgumentException("Bad account type:" + type);
		}
		try {
			accountMap.put(acc.getName(), acc);
		} catch (Exception exc) {
			return false;
		}
		return true;
	}

	/**
	Method: newAccount
	Inputs: String type of the account, String name of the account, float balance of the account
	Returns: boolean true if successful, false otherwise

	Description: Creates new account with the parameters passed
	*/
	public boolean newAccount(String type, String name, float balance) 
		throws IllegalArgumentException {
		//Fix for Coding Standards - 8
		if (balance < 0.0f) {
			throw new IllegalArgumentException("New account may not be started with a negative balance");
		}
		
		return newAccountFactory(type, name, balance);
	}
	
	/**
	Method: closeAcccount	
	Inputs: String name of the account to be closed
	Returns: boolean true if successful, false otherwise

	Description: Will close the account of the passed name
	*/
	public boolean closeAccount(String name) {
		Account acc = accountMap.get(name);
		if (acc == null) {
			return false;
		}
		acc.setState(State.CLOSED);
		return true;
	}

	public Account getAccount(String name) {
		return accountMap.get(name);
	}

	public List<Account> getActiveAccounts() {
		List<Account> result = new ArrayList<Account>();

		for (Account acc : accountMap.values()) {
			if (acc.getState() != State.CLOSED) {
				result.add(acc);
			}
		}
		return result;
	}
	
	public List<Account> getAllAccounts() {
		return new ArrayList<Account>(accountMap.values());
	}

	
	/**
	Method: saveAccounts
	Inputs: none
	Returns: none

	Description: Saves the state of the server
	*/
	public void saveAccounts() throws IOException {
		ObjectOutputStream out = null; 
		try {
			out = new ObjectOutputStream(new FileOutputStream(fileName));

			out.writeObject(Integer.valueOf(accountMap.size()));
			//Fix for Logic Error - 2
			for (String key : accountMap.keySet()){
				out.writeObject(accountMap.get(key));
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Could not write file:" + fileName);
		} 
		finally 
		{
			if (out != null) 
			{
				try {
					out.close();
				} 
				catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
	static final String fileName = "accounts.ser";
	private Map<String,Account> accountMap = null;
}
