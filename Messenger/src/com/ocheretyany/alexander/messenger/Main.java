package com.ocheretyany.alexander.messenger;

import javax.swing.*;
import java.sql.*;

/**
 * Main class for Messenger program
 * 
 * @author Alexander Ocheretyany
 */
public class Main {

	private static Connection con; // Main connection to the server

	/** Main function which starts the program */
	public static void main(String[] args) {

		startConnection();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI();
			}
		});
	}

	/** The function starts connection with a database */
	public static void startConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			con = DriverManager
					.getConnection("jdbc:derby://localhost:1527/MessengerDB;create=true;user=madmin;password=12345");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unknown error!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1); // Exit if cannot connect to the database
		}
	}

	/** Returns a connection to the database */
	public static Connection getConnection() {
		return con;
	}
}
