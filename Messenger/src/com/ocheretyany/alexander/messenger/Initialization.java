package com.ocheretyany.alexander.messenger;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class is used to create a database for the program
 * 
 * @author Alexander Ocheretyany
 */
public class Initialization {

	/**
	 * The function creates a database for Messenger program. If it already exists,
	 * the function does nothing
	 */
	public static void main(String[] args) {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			Connection con = DriverManager
					.getConnection("jdbc:derby://localhost:1527/MessengerDB;create=true;user=madmin;password=12345");
			Statement st = con.createStatement();
			st.execute("CREATE TABLE data (\n" + "    id INTEGER, \n" + "    login VARCHAR(16), \n"
					+ "    password VARCHAR(8), \n" + "    online BOOLEAN, \n" + "    news LONG VARCHAR \n" + ")");
			st.execute(
					"CREATE TABLE chats (\n" + "    conversation LONG VARCHAR, \n" + "    names VARCHAR(37) \n" + ")");
			st.close();

		} catch (Exception e) {
			Pattern pattern = Pattern.compile("X0Y32");
			Matcher match = pattern.matcher(e.getCause().toString());
			if (!match.find()) { // If an error caused not by existence
				e.printStackTrace();
			}
		}
	}

}
