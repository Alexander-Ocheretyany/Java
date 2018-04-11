package com.ocheretyany.alexander;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;

/**
 * This is the core class of the program where all necessary data is stored
 * @author Alexander Ocheretyany
 * @version 1.0
 */
public final class TextEditor {
	
	private static GUI gui; // Main GUI
	private static File settings; // Setting file
	private static String defaultPath; // Default path where files are stored
	private static ArrayList<String> listOfLanguages; // All loaded languages
	private static String language; // Currently chosen programming language 
	private static BufferedReader settingsReader; // Reader of default settings
	private static ArrayList<String> dictionary; // List of keywords
	private static File openedFile; // Opened file
	private static TextPanel currentTextPanel; // Current text panel
		
	/**
	 * The main method
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Reading of settings
		settings = new File("settings.sys");
		if(!settings.exists()) {
			try {
				settings.createNewFile();
			} catch (IOException e) {
				new Error("Cannot create the settings file!");
			}
		}
		// --------------------
		
		setDefaults(); // Set default settings
		loadLanguages(); // Loading of existing languages
		
		// Run the main GUI
		SwingUtilities.invokeLater(new Runnable(){

			public void run() {
				gui = new GUI();
			}
			
		});
		// -----------------
	}
	
	public static GUI getGui() {
		return gui;
	}
	
	/**
	 * This method sets up the default path according to its argument
	 * @param path is the path to be set as the default
	 */
	public static void setDefaultPath(String path) {
		// Sets the default path to the given string
		defaultPath = path;		
		try {
			ArrayList<String> text = new ArrayList<>();
			settingsReader = new BufferedReader(new FileReader(settings));
			String s = settingsReader.readLine();
			while(s != null) {
				text.add(s);
				s = settingsReader.readLine();
			}
			if(text.size() > 1) {
				text.set(1, path + "\n");
			} else {
				text.add(path);
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(settings));
			for(String str: text) {
				writer.write(str + "\n");
			}
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			new Error("Cannot rewrite setting file!");
		}
	}

	/**
	 * The method sets up the default language
	 */
	private static void setDefaultLanguage() {
		try {
			ArrayList<String> text = new ArrayList<>();
			settingsReader = new BufferedReader(new FileReader(settings));
			String s = settingsReader.readLine();
			while(s != null) {
				text.add(s);
				s = settingsReader.readLine();
			}
			text.set(0, language + "\n");
			BufferedWriter writer = new BufferedWriter(new FileWriter(settings));
			for(String str: text) {
				writer.write(str);
			}
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			new Error("Cannot rewrite setting file!");
		}

	}

	/**
	 * Returns the default path
	 * @return default path
	 */
	public static String getDefaultPath() {
		return defaultPath;
	}
	
	/**
	 * Method goes through the folder "Languages" and loads the names of all available languages
	 */
	private static void loadLanguages() {
		
		// Go through the folder "Languages" and load names of all files there
		listOfLanguages = new ArrayList<String>();
		File file = new File("Languages");
		String[] s = file.list();
		
		for(String k: s) {
			listOfLanguages.add(k.substring(0, k.length() - 5));
		}
		
	}
	
	/**
	 * Returns a list of available languages 
	 * @return list of available languages
	 */
	public static ArrayList<String> getListOfLanguages(){
		return listOfLanguages;
	}
	
	/**
	 * Sets the current language according to its argument
	 * @param s name of the language to be set
	 */
	public static void setLanguage(String s) {
		/* This function sets the default language,
		 * opens the language file and downloads
		 * the whole dictionary
		 */
		language = s;
		setDefaultLanguage();
		downloadDictionary();
	}
	
	/**
	 * Returns current language
	 * @return current language
	 */
	public static String getCurrentLanguage() {
		return language;
	}

	/**
	 * Sets up the default state
	 */
	private static void setDefaults() {
	
		openedFile = null;
		currentTextPanel = null;
		
		// Set default language, default path
		try {
			settingsReader = new BufferedReader(new FileReader(settings));
			String s = settingsReader.readLine();
			if(s != null) {
				settingsReader.close();
				setLanguage(s);
			}
			settingsReader = new BufferedReader(new FileReader(settings));
			settingsReader.readLine();
			s = settingsReader.readLine();
			if(s != null) {
				settingsReader.close();
				setDefaultPath(s);
			} else {
				File[] list = FileSystemView.getFileSystemView().getRoots();
				String root = FileSystemView.getFileSystemView().getSystemDisplayName(list[0]);
				
				setDefaultPath(root);
				settingsReader.close();
			}
		} catch (IOException e) {
			new Error("Cannot read the settings file!");
		}

	}

	/**
	 * Loads the keywords of the current language
	 */
	private static void downloadDictionary() {
		StringBuilder path = new StringBuilder("Languages/");
		path.append(language);
		path.append(".lang");
		File lang = new File(path.toString());
		BufferedReader wordReader;
		dictionary = new ArrayList<String>();
		try {
			wordReader = new BufferedReader(new FileReader(lang));
			String str = wordReader.readLine();
			while(str != null) {
				dictionary.add(str);
				str = wordReader.readLine();
			}
			wordReader.close();
		} catch (IOException e) {
			new Error("Cannot read the keywords!");
		}
	}
	
	/**
	 * Returns a list of the keywords of the currently chosen language
	 * @return list of keywords
	 */
	public static ArrayList<String> getCurrentDictionary(){
		return dictionary;
	}

	/**
	 * Sets the currently opened file
	 * @param f currently opened file
	 */
	public static void setOpenedFile(File f) {
		openedFile = f;
	}

	/**
	 * Returns currently opened file
	 * @return currently opened file
	 */
	public static File getOpenedFile() {
		return openedFile;
	}

	/**
	 * Sets current text panel
	 * @param panel current text panel
	 */
	public static void setCurrentTextPanel(TextPanel panel) {
		currentTextPanel = panel;
	}
	
	/**
	 * Returns current text panel
	 * @return current text panel
	 */
	public static TextPanel getCurrentTextPanel() {
		return currentTextPanel;
	}
}
