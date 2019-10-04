/**
 * This class is used for a frame with download path chooser. In the chooser a user can choose
 * download directory where all downloaded files will be stored.
 * Default folder is: user/Downloads.
 * After setting the path is stored in outer file.
 */

package com.ocheretyany.alexander;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Path {
	
	private JFrame path;
	private JFileChooser chooser;
	private String directory;
	
	public Path(){
		path = new JFrame("Choose path"); // Name of frame
		path.setSize(500, 400); // Size of frame
		ImageIcon icon = new ImageIcon("src/Images/choose.png"); // Set path to icon
		path.setIconImage(icon.getImage()); // Set icon
		path.setLocationRelativeTo(GUI.mainFrame); // Shoe the frame in the centre of the main frame
		chooser = new JFileChooser(); // Create path chooser
		path.add(chooser); // Add chooser to the frame
		File file = new File(Manager.downloadPath); // Open chooser starting with the current downloading directory
		chooser.setCurrentDirectory(file);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Only directories can be choosen
		chooser.setApproveButtonText("Select"); // Set name of the left button
		chooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("ApproveSelection")){
					File dir = chooser.getSelectedFile();
					directory = dir.toString();
					Manager.setPath(directory);
				}
				path.dispose(); // Dispose the frame
			}
		});
		path.setVisible(true); // Frame is visible
	}
}