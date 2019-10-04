package com.ocheretyany.alexander;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import javax.swing.JFrame;

public class Data {
	
	private String name;
	private downloadPanel panel;
	private String link;
	private JFrame caller;
	private Data next;
	private Data previous;
	private Thread downloadThread;
	private double size;
	private String status;
	private int downloaded;
	public boolean pause = false;
	public boolean wrong = false;
	
	public Data(String link, JFrame caller){
		this.link = link; // Link to the file
		this.caller = caller; // Set caller's frame
		next = null;
		previous = null;
		status = "Stopped";
		downloaded = 0;
		getInfo();
		panel = new downloadPanel(this);
	}
	
	public Data getNext() {
		return next;
	}

	public void setNext(Data next) {
		this.next = next;
	}

	public Data getPrevious() {
		return previous;
	}

	public void setPrevious(Data previous) {
		this.previous = previous;
	}
		
	private void getInfo(){
		try {
			new URL(link); // Try to get the link
			caller.dispose(); // Dispose caller's frame since operation is successful
			StringBuilder namer = new StringBuilder(); // String for getting name

			for(int i = 0; i < link.length(); i++){
				if(link.charAt(i) == '/'){
					namer = new StringBuilder();
				}
				else{
					namer.append(link.charAt(i)); // Store everything after '/'	
				}
			}
			name = namer.toString();
			if(name.length() > 12){
				int pos = 0;
				for(int i = 0; i < name.length(); i++){
					if(name.charAt(i) == '.') pos = i;
				}
				String ext = name.substring(pos);
				name = name.substring(0, 12);
				if(ext.length() < 5) name = name.concat(ext);
			}
		} catch (MalformedURLException e) {
			wrong = true;
			Errors.popError("Wrong URL");
		}
	}
	
	public String getName() {
		return name;
	}
	
	public downloadPanel getPanel() {
		return panel;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void startDownloading() {
		if (pause == true) {
			pause = false;
			synchronized(downloadThread){
				downloadThread.notify();
			}
		} else {
			pause = false;
			downloadThread = new Thread() {
				public void run() {
					URL linkToFile = null;
					URLConnection connection = null;

					try {
						linkToFile = new URL(link);
					} catch (MalformedURLException e) {
						panel.status.setText("Status: Error!");
						wrong = true;
						Errors.popError("Error in URL!");
					}

					try {
						connection = linkToFile.openConnection();
						connection.getContentLength();
					} catch (IOException e) {
						panel.status.setText("Status: Error!");
						Errors.popError("Can't setup connection!");
					}

					BufferedInputStream reader = null;

					try {
						reader = new BufferedInputStream(connection.getInputStream());
					} catch (IOException e) {
						panel.status.setText("Status: Error!");
						Errors.popError("No connection!");
					}

					FileOutputStream out = null;
					File myFile = new File(Manager.downloadPath + '\\' + name);

					try {
						out = new FileOutputStream(myFile);
					} catch (FileNotFoundException e) {
						panel.status.setText("Status: Error!");
						Errors.popError("Can't create a file!");
					}

					int count = 0;
					byte[] data = new byte[1024];

					try {
						double block = (double) 100 / (size / 1024);
						double cur = 0;

						while ((count = reader.read(data, 0, 1024)) != -1 && !Thread.currentThread().isInterrupted()) {
							cur = cur + block;
							out.write(data, 0, count);
							panel.progressBar.setValue((int) cur);
							while (pause) {
								boolean key = false;
								synchronized (this) {
									try {
										this.wait();
									} catch (InterruptedException e) {
										key = true;
									}
									if(!key) panel.status.setText("Status: Downloading");
								}
							}
						}
						if (!Thread.currentThread().isInterrupted()){
							panel.progressBar.setValue(100);
							panel.status.setText("Status: Downloaded!");
						}

					} catch (IOException | NullPointerException e1) {
						panel.status.setText("Status: Error!");
						Errors.popError("Can't read data from server!");
					}

					try {
						out.close();
					} catch (IOException e) {
						panel.status.setText("Status: Error!");
						Errors.popError("Can't close file!");
					}
				}
			};

			downloadThread.setName("Download " + name);
			panel.status.setText("Status: Downloading");
			downloadThread.start();
		}
	}

	public void pauseDownloading() {
		if (downloadThread != null && downloadThread.isAlive()) {
				panel.status.setText("Status: Paused");
				pause = true;
		}	
	}

	public void stopDownloading(){
		try {
			synchronized(downloadThread){
				downloadThread.interrupt();
			}
			status = "Stopped";
			panel.status.setText("Status: Stopped");
			panel.progressBar.setValue(0);
		} catch (NullPointerException e) {
		}
	}
	
	public double getSize(){
		URL linkToFile = null;
		URLConnection connection = null;
		
		try {
			linkToFile = new URL(link);
		} catch (MalformedURLException e) {
		}

		try {
			if(linkToFile != null){
				connection = linkToFile.openConnection();
				size = connection.getContentLength();
			}
		} catch (IOException e) {
			panel.status.setText("Status: Error!");
			Errors.popError("Problem with connection!");
		}
		return size;
	}
	
	public int getDownloaded(){
		return downloaded;
	}
	
	public double mySize(){
		return size;
	}
}