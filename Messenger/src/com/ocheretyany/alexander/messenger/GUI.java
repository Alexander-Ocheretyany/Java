package com.ocheretyany.alexander.messenger;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * 
 * @author Alexander Ocheretyany
 * 
 *         The class is used to create the GUI for a client of the messenger.
 */
public class GUI implements ActionListener, FocusListener, KeyListener {

	private BufferedImage icon; // Main icon

	private JLabel logo; // Logo
	private JFrame mainFrame; // Main frame
	private JTextField login; // Login
	private JPasswordField password; // Password
	private JButton enter; // Enter button
	private JButton newAccount; // Create a new account

	/** Main constructor */
	GUI() {

		try {
			icon = ImageIO.read(new File("icon_white.png")); // Open the icon
		} catch (IOException e) {
		}

		logo = new JLabel(new ImageIcon(icon.getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
		logo.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainFrame = new JFrame("Talk2Me"); // Name
		mainFrame.setIconImage(icon); // Main icon
		mainFrame.setSize(256, 540); // Size
		mainFrame.setResizable(false); // Prohibit resizing
		mainFrame.getContentPane().setBackground(Color.BLACK);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null); // Stick to nothing, start at the centre of a screen
		mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

		// Definition of fields:
		login = new JTextField("Login");
		login.setText("Login");
		login.setBorder(null);
		login.setName("Login");
		login.addKeyListener(this);
		login.addFocusListener(this);

		password = new JPasswordField(8);
		password.setText("Password");
		password.setBorder(null);
		password.addActionListener(this);
		password.setName("Password");
		password.addKeyListener(this);
		password.addFocusListener(this);

		// Definition of buttons:
		enter = new JButton("Enter");
		enter.addActionListener(this);
		enter.setAlignmentX(Component.CENTER_ALIGNMENT);
		enter.setBorderPainted(false);
		enter.setBackground(null);
		enter.setForeground(Color.WHITE);
		enter.setFocusPainted(false);

		newAccount = new JButton("Create new account");
		newAccount.addActionListener(this);
		newAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
		newAccount.setBorderPainted(false);
		newAccount.setBackground(null);
		newAccount.setForeground(Color.WHITE);
		newAccount.setFocusPainted(false);

		mainFrame.add(Box.createRigidArea(new Dimension(0, 30)));
		mainFrame.add(logo);
		mainFrame.add(Box.createRigidArea(new Dimension(0, 50)));
		mainFrame.add(login);
		mainFrame.add(Box.createRigidArea(new Dimension(0, 10)));
		mainFrame.add(password);
		mainFrame.add(Box.createRigidArea(new Dimension(0, 10)));
		mainFrame.add(enter);
		mainFrame.add(Box.createRigidArea(new Dimension(0, 120)));
		mainFrame.add(newAccount);
		mainFrame.add(Box.createRigidArea(new Dimension(0, 10)));
		mainFrame.getRootPane().setDefaultButton(enter);

		mainFrame.setVisible(true); // Show the frame
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Create new account")) {
			new newAccount();
		} else if (ae.getActionCommand().equals("Enter")) {
			new LogIn(login.getText(), new String(password.getPassword()), mainFrame);
		}
	}

	@Override
	public void focusGained(FocusEvent fe) {
		switch (fe.getComponent().getName()) {
		case "Login":
			JTextField field_1 = (JTextField) fe.getComponent();
			field_1.selectAll();
			break;
		case "Password":
			JPasswordField field_2 = (JPasswordField) fe.getComponent();
			field_2.selectAll();
			break;
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			enter.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
