package com.ocheretyany.alexander;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

/**
 * The class is used to create a GUI for the program
 * @author Alexander Ocheretyany
 */
public final class GUI {
	
	private JFrame mainFrame; // Main frame
	private JPanel mainPanel; // Main work panel
	private JMenuBar menuBar; // Main menu bar
	
	private JMenu file;
	private JMenu edit;
	private JMenu syntax;
	
	private JMenuItem newFile;
	private JMenuItem openFile;
	private JMenuItem saveFile;
	private JMenuItem saveFileAs;
	private JMenuItem exit;
	
	private ButtonGroup languages;
	private ArrayList<JRadioButtonMenuItem> listOfRadioButtons;
	
	/**
	 * Main constructor which creates a frame, sets its parameters, adds listeners and
	 * so forth
	 */
	GUI(){
		
		// Frame construction
		mainFrame = new JFrame("Code editor"); // Main frame
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close if the cross is pressed
		mainFrame.setLocationRelativeTo(null); // Place the frame in the middle of the screen
		Dimension maxDim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension minDim = new Dimension(500, 400);
		mainFrame.setMaximumSize(maxDim);
		mainFrame.setMinimumSize(minDim);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximized the size of the frame
		
		// Adding a panel to the main frame
		mainPanel = new JPanel(); // Main panel
		//mainPanel.setLayout(new FlowLayout());
		mainFrame.add(mainPanel);
		
		// Construction of a menu bar
		menuBar = new JMenuBar(); // Main menu bar
		
		file = new JMenu("File");
		file.setMnemonic('F');
		edit = new JMenu("Edit");
		edit.setMnemonic('E');
		
		newFile = new JMenuItem("New");
		newFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new NewFile();
			}
			
		});
		openFile = new JMenuItem("Open");
		openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Open();
			}
		});
		saveFile = new JMenuItem("Save");
		saveFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						Save.saveFile();
					}
				});
			}
			
		});
		saveFileAs = new JMenuItem("Save as...");
		saveFileAs.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new SaveAsItem();
			}
			
		});
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				mainFrame.dispose();
				System.gc();
				System.exit(0);
			}
			
		});
		file.add(newFile);
		file.addSeparator();
		file.add(openFile);
		file.add(saveFile);
		file.add(saveFileAs);
		file.addSeparator();
		file.add(exit);
		
		syntax = new JMenu("  Syntax  ");
		languages = new ButtonGroup();
		
		class Listener implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				TextEditor.setLanguage(e.getActionCommand().trim());
			}	

		}
		
		Listener listener = new Listener();
		
		// For all languages we add a button
		
		if (TextEditor.getListOfLanguages() != null){
				
			listOfRadioButtons = new ArrayList<>();
			int iterator = 0;
			int selection = 0;
			String defaultLanguage = TextEditor.getCurrentLanguage();
			
			for(String s: TextEditor.getListOfLanguages()) {
				
				listOfRadioButtons.add(iterator++, new JRadioButtonMenuItem("  " + s + "  "));
				listOfRadioButtons.get(iterator - 1).addActionListener(listener);
				languages.add(listOfRadioButtons.get(iterator - 1));
				syntax.add(listOfRadioButtons.get(iterator - 1));
				
				if(s.equals(defaultLanguage)) {
					selection = iterator - 1;
				}
				
			}
				
				listOfRadioButtons.get(selection).setSelected(true); // Set default language
			
		}
		
		edit.add(syntax);	
		menuBar.add(file);
		menuBar.add(edit);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setJMenuBar(menuBar);
		saveFile.setEnabled(false);
		saveFileAs.setEnabled(false);
		mainFrame.setVisible(true);	
	}
	
	/**
	 * Returns the main frame
	 * @return JFrame object related to the main frame of the program
	 */
	public JFrame getMainFrame() {
		return mainFrame;
	}
	
	/**
	 * Returns chosen language
	 * @return chosen language as a String object
	 */
	public String getChosenLanguage() {
		return languages.getSelection().toString();	
	}
	
	/*
	 * Repaints the main frame
	 */
	public void repaint() {
		mainFrame.validate();
		mainFrame.repaint();
	}
	
	/*
	 * Allows the menus "Save" and "SaveAs..." to be active
	 */
	public void activateSave() {
		saveFile.setEnabled(true);
		saveFileAs.setEnabled(true);
	}
}
