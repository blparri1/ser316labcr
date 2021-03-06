/*
  File:	MainFrame.java
  Author: kevinagary
  Date:	Spring 2017
  
  Description: GUI creation and handling class
*/
package banking.gui;

import banking.primitive.core.Account;
import banking.primitive.core.AccountServer;
import banking.primitive.core.AccountServerFactory;

import java.io.*;
import java.util.*;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;
/**
Class: MainFrame

Description: Main class for user interfaces
*/
@SuppressWarnings("serial")
class MainFrame extends JFrame {
	/**
	Method: MainFrame Constructor
	Inputs: String propertyFile
	Returns: nothing

	Description: Constructor for the MainFrame class
	*/
	public MainFrame(String propertyFile) throws IOException {

		//** initialize myServer
		myServer = AccountServerFactory.getMe().lookup();

		props = new Properties();
		// FB Lab 3 Activity 1 Step 5
		FileInputStream fis = null; 
		try {
			fis =  new FileInputStream(propertyFile);
			props.load(fis);
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
		_constructForm();
	}

	/**
	Method: _constructForm()
	Inputs: none
	Returns: void

	Description: Builds the GUI for the program
	*/
	//Fix Coding Standard - 4
	private void _constructForm() {
		//*** Make these read from properties
		typeLabel		= new JLabel(props.getProperty("TypeLabel"));
		nameLabel		= new JLabel(props.getProperty("NameLabel"));
		balanceLabel	= new JLabel(props.getProperty("BalanceLabel"));

		Object[] accountTypes = {"Savings", "Checking"};
		typeOptions = new JComboBox(accountTypes);
		nameField = new JTextField(20);
		balanceField = new JTextField(20);

		newAccountButton = new JButton("New Account");
		JButton depositButton = new JButton("Deposit");
		JButton withdrawButton = new JButton("Withdraw");
		JButton saveButton = new JButton("Save Accounts");
		displayAccountsButton = new JButton("List Accounts");
		JButton displayAllAccountsButton = new JButton("All Accounts");

		this.addWindowListener(new FrameHandler());
		newAccountButton.addActionListener(new NewAccountHandler());
		displayAccountsButton.addActionListener(new DisplayHandler());
		displayAllAccountsButton.addActionListener(new DisplayHandler());
		depositButton.addActionListener(new DepositHandler());
		withdrawButton.addActionListener(new WithdrawHandler());
		saveButton.addActionListener(new SaveAccountsHandler());		
		
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		
		JPanel panel1 = new JPanel();
		panel1.add(typeLabel);
		panel1.add(typeOptions);
		
		JPanel panel2 = new JPanel();
		panel2.add(displayAccountsButton);
		panel2.add(displayAllAccountsButton);
		panel2.add(saveButton);
		
		JPanel panel3 = new JPanel();
		panel3.add(nameLabel);
		panel3.add(nameField);
		
		JPanel panel4 = new JPanel();
		panel4.add(balanceLabel);
		panel4.add(balanceField);
		
		JPanel panel5 = new JPanel();
		panel5.add(newAccountButton);
		panel5.add(depositButton);
		panel5.add(withdrawButton);

		pane.add(panel1);
		pane.add(panel2);
		pane.add(panel3);
		pane.add(panel4);
		pane.add(panel5);
		
		setSize(400, 250);
	}
	/**
	  Class: DisplayHandler
	  
	  Description: Handles the display generically to the user with regard to GUI
	*/
	class DisplayHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<Account> accounts = null;
			if (e.getSource() == displayAccountsButton) {
				accounts = myServer.getActiveAccounts();
			} else {
				accounts = myServer.getAllAccounts();
			}
			StringBuffer sb = new StringBuffer();
			Account thisAcct = null;
			for (Iterator<Account> li = accounts.iterator(); li.hasNext();) {
				thisAcct = (Account)li.next();
				sb.append(thisAcct.toString()+"\n");
			}

			JOptionPane.showMessageDialog(null, sb.toString());
		}
	}
	/**
	  Class: NewAccountHandler
	  
	  Description: Helps in the creation of new acccount with regard to GUI
	*/
	// Complete a handler for new account button
	class NewAccountHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String type = typeOptions.getSelectedItem().toString();
			String name = nameField.getText();
			String balance = balanceField.getText();

			if (myServer.newAccount(type, name, Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, "Account created successfully");
			} else {
				JOptionPane.showMessageDialog(null, "Account not created!");
			}
		}
	}
	/**
	  Class: SaveAccountsHandler
	  
	  Description: Handles the GUI with regard to savings accounts
	*/	
	// Complete a handler for new account button
	class SaveAccountsHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				myServer.saveAccounts();
				JOptionPane.showMessageDialog(null, "Accounts saved");
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(null, "Error saving accounts");
			}
		}
	}
	/**
	  Class: DepositHandler
	  
	  Description: Handles the GUI with regard to deposits
	*/
	// Complete a handler for deposit button
	class DepositHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = nameField.getText();
			String balance = balanceField.getText();
			Account acc = myServer.getAccount(name);
			if (acc != null && acc.deposit(Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, "Deposit successful");
			} else {
				JOptionPane.showMessageDialog(null, "Deposit unsuccessful");
			}		
		}
	}
	/**
	  Class: WithdrawHandler	
	  
	  Description: Handles the GUI with regard to deposits
	*/	
	// Complete a handler for deposit button
	class WithdrawHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = nameField.getText();
			String balance = balanceField.getText();
			Account acc = myServer.getAccount(name);
			if (acc != null && acc.withdraw(Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, "Withdrawal successful");
			} else {
				JOptionPane.showMessageDialog(null, "Withdrawal unsuccessful");
			}		
		}
	}
	/**
	  Class: FrameHandler
	  
	  Description: Exits program from GUI
	*/	
	//** Complete a handler for the Frame that terminates 
	//** (System.exit(1)) on windowClosing event
	// FB Lab 3 Activity 1 Step 5
	static class FrameHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {

			System.exit(0);
		}
	}
	AccountServer	myServer;
	private	Properties		props;
	private	JLabel			typeLabel;
	private	JLabel			nameLabel;
	private	JLabel			balanceLabel;
	private	JComboBox		typeOptions;
	private	JTextField		nameField;
	private	JTextField		balanceField;
	private	JButton 		depositButton;
	private	JButton 		withdrawButton;
	private	JButton			newAccountButton;
	private	JButton			displayAccountsButton;
	private	JButton			displayODAccountsButton;
}
