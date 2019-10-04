package com.ocheretyany.alexander;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;

public class downloadPanel {
	
	private JPanel mainPanel, infoPanel, progressPanel;
	public JProgressBar progressBar;
	public JLabel status;
	
	public downloadPanel(Data a) {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1));
		
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		
		progressPanel = new JPanel();
		progressPanel.setLayout(new FlowLayout());
		progressPanel.setSize(200, 80);
		
		progressBar = new JProgressBar();
		progressBar.setMaximum(100);
		progressBar.setMinimum(0);
		progressBar.setPreferredSize(new Dimension(270, 20));
		progressBar.setStringPainted(true);
	
		
		JLabel nameOfFile = new JLabel("File name: " + a.getName());
		nameOfFile.setToolTipText(a.getName());
		infoPanel.add(nameOfFile);
		infoPanel.add(new JLabel("Size: " + (double)a.getSize()/1024/1024 + " MB"));
		infoPanel.add(new JLabel("Directory: " + Manager.downloadPath));
		infoPanel.add(new JSeparator());
		status = new JLabel("Status: " + a.getStatus());
		infoPanel.add(status);
		progressPanel.add(progressBar);
		
		mainPanel.add(infoPanel);
		mainPanel.add(progressPanel);
	}
	
	public JPanel getPanel(){
		return mainPanel;
	}
}