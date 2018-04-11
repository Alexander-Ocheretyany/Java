package com.ocheretyany.alexander;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This class is used for error generation
 * @author Alexander Ocheretyany
 */
public class Error {
	
	private JFrame errorFrame; // Error frame
	private JLabel message; // Printed message
	
	/**
	 * The constructor creates a frame with an error passed through the argument
	 * @param msg message to be printed as an error
	 */
	public Error(String msg) {
		
		errorFrame = new JFrame("Error!");
		errorFrame.setSize(300, 100);
		errorFrame.setLocationRelativeTo(TextEditor.getGui().getMainFrame());
		errorFrame.setAlwaysOnTop(true);
		errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		message = new JLabel(msg);
		errorFrame.add(message);
		
		errorFrame.setVisible(true);
		
	}
}
