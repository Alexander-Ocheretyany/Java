package com.ocheretyany.alexander;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * The class is used to save all the changes made by a user in the edited code.
 * @author Alexander Ocheretyany
 */
public class Save {
	
	/**
	 * Opens a writer associated with the current file and writes the whole text
	 * from the main text area into the file.
	 */
	public static synchronized void saveFile(){
		File file = TextEditor.getOpenedFile();
		if(file != null) {
			try {
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
				TextPanel textPanel = TextEditor.getCurrentTextPanel();
				writer.write(textPanel.getText());
				writer.close();
			} catch (IOException e) {
				new Error("Something went wrong!");
			}
		}
	}
}
