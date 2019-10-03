package com.ocheretyany.alexander.messenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The class is used for logging in purposes.
 * 
 * @author Alexander Ocheretyany
 */
public class LogIn {

	/** Main constructor */
	LogIn(String login, String password, JFrame frame) {
		if (exists(login, password)) {
			frame.dispose();
			new Chat(login);
		} else {
			JOptionPane.showMessageDialog(null, "Wrong login or password!", "Wrong data", JOptionPane.ERROR_MESSAGE);
		}
	}

	/** The function checks whether a user exists */
	private boolean exists(String login, String password) {
		try {
			Connection con = Main.getConnection();
			Statement st = con.createStatement();
			ResultSet set = st.executeQuery("SELECT login, password FROM data");
			while (set.next()) {
				if (set.getString(1).equals(login)) {
					if (set.getString(2).equals(password)) {
						String query = "UPDATE data SET online = 'TRUE' WHERE login = ?";
						PreparedStatement statement = con.prepareStatement(query);
						statement.setString(1, login);
						statement.executeUpdate();
						return true;
					} else {
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
