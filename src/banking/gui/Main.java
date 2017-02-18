/*
  File:	Main.java
  Author: kevinagary
  Date:	Spring 2017
  
  Description: Main program for entry point of code
*/
package banking.gui;

// STYLE
import javax.swing.JFrame;

/**
Class: Main

Description: Java main class for entry point
*/
final class Main {
	/**
	Method: Main Constructor
	Inputs: None
	Returns: None

	Description: Blank because it should not exist
	*/
	private Main() {
	}
	
	/**
	 * All methods should have a Javadoc according to STYLE.
	 * @param args command-line arguments
	 * @throws Exception as per typical main specifications
	 */
	/**
	Method: main
	Inputs: String[] args
	Returns: nothing

	Description: Main method of the program
	*/
	public static void main(final String[] args) throws Exception {

		if (args.length != 1) {
			System.out.println("Usage: java FormMain <property file>");
			System.exit(1);
		}

		String propertyFile = args[0];
		JFrame frame = new MainFrame(propertyFile);
		frame.setVisible(true);

	}
}
