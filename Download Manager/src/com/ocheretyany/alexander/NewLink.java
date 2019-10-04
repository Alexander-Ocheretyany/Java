package com.ocheretyany.alexander;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class NewLink {
	
	private JFrame newLink;
	private JTextField address;
	private JButton paste;
	private JButton add;
	private JPanel mainPanel, buttonPanel, linkPanel;
	
	public NewLink() {
		
		/* FRAME */
		newLink = new JFrame("Add link");
		newLink.setSize(260, 100);
		ImageIcon icon = new ImageIcon("src/Images/add_link.png");
		newLink.setResizable(false);
		/* END OF FRAME */
		
		/* MAIN PANEL */
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		address = new JTextField("Paste URL here");
		address.setPreferredSize(new Dimension(220, 20));
		
		/* BUTTON PANEL */
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		paste = new JButton("Paste from buffer");
		paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String str = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
					
					StringBuilder data = new StringBuilder(str);
					StringBuilder rightAddress = new StringBuilder();
					
					for(int i = 0; i < data.length(); i++){
						if(data.charAt(i) != ' '){
							rightAddress.append(data.charAt(i));
						}
					}
					address.setText(rightAddress.toString());
				} catch (HeadlessException | UnsupportedFlavorException | IOException | ClassCastException e1) {
					address.setText("The buffer is empty!");
				} 				
			}
		});
		add = new JButton("Add link");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean er = false;
				Data queue = DownloadQueue.getHead();
				Data item = new Data(address.getText(), newLink);
				
				if(!item.wrong){
					for (int i = 0; i < DownloadQueue.getDepth(); i++) {
						if (item.getName().equals(queue.getName())) {
							Errors.popError("Already added!");
							i = DownloadQueue.getDepth();
							er = true;
						}
						queue = queue.getNext();
					}
	
					if(!er){
						Manager.getQueue().enqueue(item);
						GUI.model.setPanel(item);
						GUI.setPanel();
					}
				}
			}
		});
		buttonPanel.add(paste);
		buttonPanel.add(add);
				
		/* LINK PANEL */
		linkPanel = new JPanel();
		linkPanel.setLayout(new FlowLayout());
		linkPanel.add(address);
		
		mainPanel.add(linkPanel);
		mainPanel.add(buttonPanel);
		
		newLink.add(mainPanel);
		newLink.setLocationRelativeTo(GUI.mainFrame);
		newLink.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newLink.setIconImage(icon.getImage());
		newLink.setVisible(true);
	}
}
