package com.ocheretyany.alexander;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Open {

	/** This class is used for creation of a frame for "Open" option with
	 * all necessary buttons and fields and contains only its constructor -
	 * Open() which does the job.
	 */
	
	private JFrame open; // Main frame
	private JPanel mainPanel;
	private JPanel filePanel;
	private JPanel buttonPanel;
	private JPanel upper;
	private JLabel file;
	private JTextField link;
	private JButton browse; // Browse button
	private JButton ok; // OK Button
	private JButton cancel; // Cancel button
	
	/** This constructor takes no arguments and it creates a frame with which a user 
	 * can open a file.
	 */
	public Open(){
		
		// Frame
		open = new JFrame("Open file");
		open.setSize(400, 200);
		open.setResizable(false);
		open.setLocationRelativeTo(TextEditor.getGui().getMainFrame());
		open.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// -----
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1));
		
		filePanel = new JPanel();
		filePanel.setLayout(new GridLayout(1, 2));
		
		file = new JLabel("File: ");
		link = new JTextField(TextEditor.getDefaultPath());

		browse = new JButton("Browse");
		browse.addActionListener(new ActionListener() {
			
			// If "browse" button pressed - open a file browser
			
			public void actionPerformed(ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("Select");
				chooser.setCurrentDirectory(new File(link.getText()));
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text", "plain text document");
				chooser.setFileFilter(filter);
				chooser.showOpenDialog(open);
				// Check whether a user has chosen a file
				File selected = chooser.getSelectedFile();
				if(selected != null) {
					String p = chooser.getSelectedFile().getPath();
					if(!p.equals(TextEditor.getDefaultPath())) {
						link.setText(chooser.getSelectedFile().getPath());
					}
				}
			}	
		});
	
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				open.dispose();
			}
			
		});
		ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				/* Look into the folder at the given path and try to find such a file. If it exists throw an error.
				 * If it doesn't exist - create a file and open a new tab corresponding to this file.
				 */	
				// Form a text panel
				if(link.getText().equals(TextEditor.getDefaultPath())) {
					new Error("File hasn't been chosen!");
					open.dispose();
				} else {
					String filePath = link.getText();
					File f = new File(filePath); // Open the requested file
					TextPanel panel = TextEditor.getCurrentTextPanel(); // Get current panel of the main frame
					JFrame gui = TextEditor.getGui().getMainFrame(); // Get the main frame
					if(panel != null) {
						gui.remove(panel); // Remove the current pane
					}
					panel = new TextPanel(f); // New text panel from the chosen file
	
					gui.add(panel, BorderLayout.CENTER); // Creation of a new text panel
					gui.setTitle(f.getName());
					TextEditor.getGui().repaint();
					TextEditor.setOpenedFile(f); // Set the opened file
					open.dispose(); // Dispose the "New file" frame
	
					// Get the name of a chosen file
					StringBuilder fileName = new StringBuilder();
					for(int i = filePath.length() - 1; i >= 0; i--) {
						if(filePath.charAt(i) != '/') {
							fileName.append(filePath.charAt(i));
						} else {
							break;
						}
					}
					fileName.reverse(); // Since we got the name reversed
					
					// Get folder's path and set it as the default folder
					String folder = filePath.substring(0, filePath.length() - fileName.toString().length());
					TextEditor.setDefaultPath(folder);
					TextEditor.getGui().activateSave();
				}
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
		
		left_upper.add(file);
		
		right_upper.add(link);
		right_upper.add(browse);
		
		upper.add(left_upper);
		upper.add(right_upper);
		
		JPanel lower = new JPanel();
		lower.setLayout(new GridLayout(1,2));
		
		lower.add(ok);
		lower.add(cancel);
		
		open.add(mainPanel);
		mainPanel.add(upper);
		mainPanel.add(lower);

		open.setVisible(true);
	}
}
