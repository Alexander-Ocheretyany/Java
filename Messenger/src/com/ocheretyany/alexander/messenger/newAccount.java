package com.ocheretyany.alexander.messenger;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

/**
 * The class for creating new accounts.
 * 
 * @author Alexander Ocheretyany
 */
public class newAccount implements ActionListener {

	private JFrame mainFrame; // Main frame
	private JLabel loginLab; // Login label
	private JLabel passLab; // Password label
	private JTextField login; // Login field
	private JPasswordField password; // Password field
	private JButton cancel; // "Cancel" button
	private JButton create; // "Create" button

	/** Main constructor */
	public newAccount() {

		mainFrame = new JFrame("Create new");
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setSize(200, 120);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);

		loginLab = new JLabel("Login:");
		passLab = new JLabel("Password:");
		login = new JTextField(8);
		login.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				if (login.getText().length() >= 8
						&& !(evt.getKeyChar() == KeyEvent.VK_DELETE || evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
					evt.consume();
				}
			}
		});
		login.setName("Login");
		password = new JPasswordField(8);
		password.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				if (password.getPassword().length >= 8
						&& !(evt.getKeyChar() == KeyEvent.VK_DELETE || evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
					evt.consume();
				}
			}
		});
		password.setName("Password");

		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		create = new JButton("Create");
		create.addActionListener(this);

		mainFrame.setLayout(new FlowLayout());

		mainFrame.getContentPane().add(loginLab);
		mainFrame.getContentPane().add(login);
		mainFrame.getContentPane().add(passLab);
		mainFrame.getContentPane().add(password);
		mainFrame.getContentPane().add(cancel);
		mainFrame.getContentPane().add(create);

	}

	/** Listener */
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getActionCommand().equals("Cancel")) {
			mainFrame.dispose();
		} else if (ae.getActionCommand().equals("Create")) {
			// TODO create account
			String login = "";
			char[] password = null;
			Component[] components = mainFrame.getContentPane().getComponents();
			for (Component c : components) {
				if (c.getName() != null && c.getName().equals("Login")) {
					login = ((JTextField) c).getText();
				} else if (c.getName() != null && c.getName().equals("Password")) {
					password = ((JPasswordField) c).getPassword();
				}
			}
			if (isCorrect(login, password)) {
				String pass = new String(password);
				Connection con = Main.getConnection();
				Statement st;
				try {
					boolean alreadyExists = false;
					st = con.createStatement();
					ResultSet set = st.executeQuery("SELECT login FROM data");
					while (set.next()) {
						if (set.getString(1).equals(login)) {
							JOptionPane.showMessageDialog(mainFrame, "Login exists!", "Exists",
									JOptionPane.ERROR_MESSAGE);
							alreadyExists = true;
						}
					}
					if (!alreadyExists) {
						ResultSet set2 = st.executeQuery("SELECT id FROM data ORDER BY id DESC");
						int lastId = 1;
						if (set2.next()) {
							lastId = set2.getInt(1);
						}
						String query = "INSERT INTO data VALUES (?, ?, ?, false, null)";
						PreparedStatement pst = con.prepareStatement(query);
						pst.setInt(1, ++lastId);
						pst.setString(2, login);
						pst.setString(3, pass);
						pst.executeUpdate();

						// Create a table with news
						query = "CREATE TABLE " + login + "(\n" + "	login VARCHAR(16), \n" + "	news LONG VARCHAR \n"
								+ ")";
						st.execute(query);
						query = "INSERT INTO " + login + " VALUES (?, null)";
						pst = con.prepareStatement(query);
						pst.setString(1, login);
						pst.executeUpdate();
					}
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(mainFrame, "Unknown error!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(mainFrame, "Wrong data!", "Wrong data", JOptionPane.ERROR_MESSAGE);
			}
			mainFrame.dispose();
		}
	}

	/** The function checks whether data (login and password) is correct */
	private boolean isCorrect(String str, char[] pass) {
		if (str != null) {
			if (str.length() < 3 || pass.length < 4)
				return false;
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == ':')
					return false;
			}
			return true;
		} else {
			return false;
		}
	}

}
