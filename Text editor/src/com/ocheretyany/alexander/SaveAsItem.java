package com.ocheretyany.alexander;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;

/**
 * Class to let a user to choose a new file to save their code 
 * @author Alexander Ocheretyany
 */
public class SaveAsItem {
	
	public SaveAsItem() {
		
		File path; // Path of a file
		String pathString; // Path as a string
		
		// Frame creation
		JFileChooser saveAs = new JFileChooser(); // File chooser
		saveAs.setDialogTitle("Save as...");
		saveAs.setCurrentDirectory(new File(TextEditor.getDefaultPath()));
		int result = saveAs.showSaveDialog(TextEditor.getGui().getMainFrame());
		// --------------
		
		// Getting of a path
		if(result == JFileChooser.APPROVE_OPTION) {
			path = saveAs.getSelectedFile();
			pathString = path.getPath();
			if(pathString.length() > 3 && !(pathString.substring(pathString.length() - 4, pathString.length())).equals(".txt")){
				pathString += ".txt";
			}
			path = new File(pathString);
			File newFile = new File(path.getPath());
			if(!newFile.exists()) {
				try {
					newFile.createNewFile();
				} catch (IOException e) {
					new Error("Cannot create a file!");
				}
				//TODO save text into the file
				try (BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(newFile)));){
					String text = TextEditor.getCurrentTextPanel().getText();
					writer.append(text);
					TextEditor.getGui().getMainFrame().setTitle(path.getName());
					TextEditor.setOpenedFile(newFile);
					TextEditor.setDefaultPath(path.toString().substring(0, path.toString().length() - path.getName().length()));
				} catch (IOException e) {}
			} else {
				new SaveAsItem();
				new Error("File exists!");
			}
		}
		// -----------------
	}
}