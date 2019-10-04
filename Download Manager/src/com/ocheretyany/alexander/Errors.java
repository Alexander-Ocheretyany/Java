package com.ocheretyany.alexander;

import java.awt.BorderLayout;

import javax.swing.*;

public class Errors {
	
	static void popError(String error){
		JFrame frame = new JFrame("Error!"); // Set label
		frame.setSize(300, 80); // Size of a frame
		frame.setResizable(false); // Cannot be resized
		frame.setIconImage(new ImageIcon("src/Images/error.png").getImage()); // Setting of icon
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose the frame if cross is pressed
		
		JLabel text = new JLabel(error); // Message
		text.setHorizontalAlignment(JLabel.CENTER);
		
		frame.getContentPane().add(text, BorderLayout.CENTER); // Add message to the frame
		frame.setLocationRelativeTo(GUI.mainFrame); // Relative to the main frame
		
		frame.setVisible(true); // Visible
	}
}
