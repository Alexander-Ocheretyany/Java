package com.ocheretyany.alexander;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/** This class is used for creation of a frame for "New file" option with
 * all necessary buttons and fields and contains only its constructor - NewFile()
 * which does the job.
 * @author Alexander Ocheretyany
 */
public class NewFile {
	
	private JFrame newFile;
	private JPanel mainPanel;
	private JPanel filePanel;
	private JPanel pathPanel;
	private JPanel buttonPanel;
	private JPanel upper;
	private JLabel nameOfFile;
	private JLabel path;
	private JTextField name;
	private JTextField link;
	private JButton browse; // Browse button
	private JButton ok; // OK Button
	private JButton cancel; // Cancel button
	private TextPanel textPanel; // Text panel
	
	public NewFile() {
		
		// Frame
		newFile = new JFrame("New file");
		newFile.setSize(400, 200);
		newFile.setResizable(false);
		newFile.setLocationRelativeTo(TextEditor.getGui().getMainFrame());
		newFile.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// -----
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1));
		
		filePanel = new JPanel();
		filePanel.setLayout(new GridLayout(1, 2));
		
		nameOfFile = new JLabel("Name:");
		name = new JTextField("File's name");
		name.selectAll();
		
		filePanel.add(nameOfFile);
		filePanel.add(name);
		
		pathPanel = new JPanel();
		pathPanel.setLayout(new GridLayout(1, 2));
		
		path = new JLabel("Path: ");
		link = new JTextField(TextEditor.getDefaultPath());
		
		browse = new JButton("Browse");
		browse.addActionListener(new ActionListener() {
			
			// If "browse" button pressed - open a file browser
			
			public void actionPerformed(ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("Select");
				chooser.setCurrentDirectory(new File(link.getText()));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(newFile);
				link.setText(chooser.getSelectedFile().getPath());
			}
			
		});
		pathPanel.add(path);
		pathPanel.add(link);
		//pathPanel.add(browse);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				newFile.dispose();
			}
			
		});
		ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				/* Look into the folder at the given path and try to find such a file. If it exists throw an error.
				 * If it doesn't exist - create a file and open a new tab corresponding to this file.
				 */	
				
				boolean fileExists = false; // Flag "File exists"
				
				// If the name does not have ".txt" type set up ".txt" at the end of its name
				String h = name.getText();
				if((h.length() < 4) || !(h.substring(h.length() - 4, h.length())).equals(".txt")) {
					name.setText(name.getText() + ".txt");
				}
				// --------------------------------------------------------------------------
				
				// Choosing of a file
				String s = link.getText();
				if(!link.getText().equals("/")) {
					s += "/";
				}
				s += name.getText();
				// -------------------
				
				// Check whether such a file exists
				File directory = new File(link.getText());
				File[] listOfFiles = directory.listFiles();
				for(File k: listOfFiles) {
					if(k.getName().equals(name.getText())) {
						fileExists = true;
						break;
					}
				}
				// ---------------------------------
				
				// Create a new file or throw an error if it exists
				if(!fileExists) {
					File f = new File(s); // File created
					// Form a text panel
					JFrame gui = TextEditor.getGui().getMainFrame();
					TextPanel previous = TextEditor.getCurrentTextPanel();
					
					if(previous != null) {
						TextEditor.getGui().getMainFrame().remove(previous);
					}
					textPanel = new TextPanel();
					
					gui.add(textPanel, BorderLayout.CENTER); // Creation of a new text panel
					TextEditor.setCurrentTextPanel(textPanel);
					gui.setTitle(name.getText());
					TextEditor.getGui().repaint();
					TextEditor.setOpenedFile(f); // Set the opened file
					TextEditor.getGui().activateSave();
					newFile.dispose(); // Dispose the "New file" frame
				} else {
					new Error("File exists!"); // File already exists
				}
				// -------------------------------------------------
			}
			
		});
		
		buttonPanel.add(cancel);
		buttonPanel.add(ok);
		
		upper = new JPanel();
		upper.setLayout(new GridLayout(1, 2));
		
		JPanel left_upper = new JPanel();
		left_upper.setLayout(new GridLayout(3, 1));
		JPanel right_upper = new JPanel();
		right_upper.setLayout(new GridLayout(3, 1));
		
		left_upper.add(nameOfFile);
		left_upper.add(path);
		
		right_upper.add(name);
		right_upper.add(link);
		right_upper.add(browse);
		
		upper.add(left_upper);
		upper.add(right_upper);
		
		JPanel lower = new JPanel();
		lower.setLayout(new GridLayout(1,2));
		
		lower.add(ok);
		lower.add(cancel);
		
		newFile.add(mainPanel);
		mainPanel.add(upper);
		mainPanel.add(lower);

		newFile.setVisible(true);
	}
	
}
