/** 
* The main class used for starting the Download Manager.
* After initialization a path to download directory is being gotten. The path is stored into
* static variable to be used outside the class.
*/

package com.ocheretyany.alexander;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Manager {

	private static DownloadQueue queue = new DownloadQueue(); // Queue of files to download
	public static GUI gui;
	public static String downloadPath; // Download path
	public static Thread mainThread;
	
	public static void main(String[] args){
		mainThread = new Thread(){
			public void run(){
				getPath();
				gui = new GUI(); // Start GUI
				while(true){
					GUI.mainFrame.validate();
					GUI.mainFrame.repaint(); // Repaint GUI
					String select = null;
					try{
						select = GUI.list.getSelectedValue();
					}
					catch(ArrayIndexOutOfBoundsException e){
						select = null;
					}
					if(select == null){
						GUI.start.setEnabled(false);
						GUI.pause.setEnabled(false);
						GUI.stop.setEnabled(false);
					} else {
						Data f = GUI.getSelectedItem();
						if(f.getPanel().status.getText().equals("Status: Downloaded!")){
							GUI.start.setEnabled(false);
							GUI.pause.setEnabled(false);
							GUI.stop.setEnabled(false);
						}
						else if(f.getPanel().status.getText().equals("Status: Paused")){
							GUI.start.setEnabled(true);
							GUI.pause.setEnabled(false);
							GUI.stop.setEnabled(true);
						}
						else if(f.getPanel().status.getText().equals("Status: Stopped")){
							GUI.start.setEnabled(true);
							GUI.pause.setEnabled(false);
							GUI.stop.setEnabled(false);
						}
						else{
							GUI.start.setEnabled(false);
							GUI.pause.setEnabled(true);
							GUI.stop.setEnabled(true);
						}
					}
				}
			}
		};
		mainThread.setName("MAIN THREAD"); // Set up name
		mainThread.start(); // Start Main thread
	}
	
	public static DownloadQueue getQueue() {
		return queue;
	}
	
	public static void setPath(String path){
		if(path == null){
			new File(".directory");	
			String home = System.getProperty("user.home");
			downloadPath = home + "\\Downloads\\";
			path = downloadPath;
		}
		PrintWriter out;
		try {
			out = new PrintWriter(".directory");
			out.print(path);
			out.close();
			downloadPath = path;
		}
		catch (FileNotFoundException e1) {
			Manager.setPath(null);
			Manager.setPath(path);
		}
	}
	
	public static void getPath(){
		Scanner in = null;
		try {
			in = new Scanner(new FileReader(".directory"));
			try{
				downloadPath = in.nextLine();
				in.close();
			}
			catch (NoSuchElementException e){
				setPath(null);
			}
		}
		catch (FileNotFoundException e) {
			setPath(null);
		}
	}
}