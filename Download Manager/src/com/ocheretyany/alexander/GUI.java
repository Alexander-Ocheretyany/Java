/**
 * This class is used to run GUI for Download Manager
 */

package com.ocheretyany.alexander;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI {
	
	public static JFrame mainFrame; // Main program frame
	private JMenuBar mainMenuBar; // Main menu bar
	public static JButton start, pause, stop; // Buttons
	private static JPanel mainPanel, listPanel, buttonPanel, infoPanel, filePanel, statPanel; // Panels for elements
	private JMenu fileItem, editItem;
	private JMenuItem newItem, exitItem;
	public static JList<String> list;
	public static Panel model;
	private JScrollPane scrollPane;
	
	public GUI(){
		
		/* MAIN FRAME */
		mainFrame = new JFrame("Download Manager"); // Create a new frame setting its name
		mainFrame.setSize(500, 400); // Setting of the initial size
		mainFrame.setIconImage(new ImageIcon("src/Images/download.png").getImage()); // Setting of the icon image
		mainFrame.setLocationRelativeTo(null); // Set the initial position at the center of a display
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Default operation when the cross is pressed
		mainMenuBar = new JMenuBar();
		fileItem = new JMenu("File");
		editItem = new JMenu("Edit");
		mainMenuBar.add(fileItem);
		mainMenuBar.add(editItem);
		JMenuItem choosePath = new JMenuItem("Choose path");
		choosePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Path();			
			}
		});
		newItem = new JMenuItem("New");
		exitItem = new JMenuItem("Exit");
		fileItem.add(newItem);
		newItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new NewLink();
			}
		});
		fileItem.addSeparator();
		fileItem.add(exitItem);
		exitItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
				System.exit(0);
			}
		});
		
		
		editItem.add(choosePath);
		mainFrame.setJMenuBar(mainMenuBar);
		mainFrame.setResizable(false);
		/* END OF MAIN FRAME */		
		
		
		list = new JList<>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Only one item can be selected at one time
		model = new Panel();
		list.setMaximumSize(new Dimension(50, 100));
		scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(190, 320));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		/* MAIN PANEL */
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		/* END OF MAIN PANEL */
		
		/* LIST PANEL */
		listPanel = new JPanel();
		listPanel.setBorder(BorderFactory.createTitledBorder("List of files"));
		listPanel.setMinimumSize(new Dimension(200,1));
		listPanel.setPreferredSize(new Dimension(200, 1));
		listPanel.add(scrollPane);
		/* END OF LIST PANEL */
		
		/* BUTTON PANEL */
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
	
		start = new JButton("Start");
		start.setMnemonic('s');
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String item = list.getSelectedValue();
				Data piece = DownloadQueue.getHead(); 
				
				for(int i = 0; i < DownloadQueue.getDepth(); i++){
					if(piece != null && piece.getName().equals(item)){
						piece.startDownloading();
						i = DownloadQueue.getDepth();
					}
					piece = piece.getNext();
				}
			}
		});
		pause = new JButton("Pause");
		pause.setMnemonic('p');
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String item = list.getSelectedValue();
				Data piece = DownloadQueue.getHead(); 
				
				for(int i = 0; i < DownloadQueue.getDepth(); i++){
					if(piece != null && piece.getName().equals(item)){
						piece.pauseDownloading();
						i = DownloadQueue.getDepth();
					}
					piece = piece.getNext();
				}
			}
		});
		stop = new JButton("Stop");
		stop.setMnemonic('t');
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String item = list.getSelectedValue();
				Data piece = DownloadQueue.getHead(); 
				
				for(int i = 0; i < DownloadQueue.getDepth(); i++){
					if(piece != null && piece.getName().equals(item)){
						piece.stopDownloading();
						i = DownloadQueue.getDepth();
					}
					piece = piece.getNext();
				}
			}
		});
		buttonPanel.add(start);
		buttonPanel.add(pause);
		buttonPanel.add(stop);		
		/* END OF BUTTON PANEL */
		
		/* STAT PANEL */
		statPanel = new JPanel();
		JLabel label = new JLabel("Hello!");
		statPanel.add(label);
		/* END OF STAT PANEL */
		
		
		/* INFO PANEL */
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setPreferredSize(new Dimension(100, 100));
		infoPanel.setBorder(BorderFactory.createTitledBorder("File statistics"));
		infoPanel.add(statPanel);
		/* END OF INFO PANEL */
		
		/* FILE PANEL */
		filePanel = new JPanel();
		filePanel.setLayout(new BorderLayout());
		filePanel.add(buttonPanel, BorderLayout.NORTH);
		filePanel.add(infoPanel, BorderLayout.CENTER);
		/* END OF FILE PANEL */
		
		
		mainFrame.add(mainPanel);
		mainPanel.add(listPanel);
		mainPanel.add(filePanel);

		
		list.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				String item = list.getSelectedValue();
				Data piece = DownloadQueue.getHead(); 
				
				for(int i = 0; i < DownloadQueue.getDepth(); i++){
					if(piece != null && piece.getName().equals(item)){
						infoPanel.remove(statPanel);
						statPanel = piece.getPanel().getPanel();
						infoPanel.add(statPanel);
						i = DownloadQueue.getDepth();
					}
					piece = piece.getNext();
				}
			}
		});
		
		list.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getModifiersEx()  == InputEvent.BUTTON3_DOWN_MASK){	
					String item = list.getSelectedValue();
					JPopupMenu m = new JPopupMenu();
					JMenuItem add = new JMenuItem("Add link");
					add.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							new NewLink();
						}
					});
					JMenuItem delete = new JMenuItem("Delete");
					delete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String item = list.getSelectedValue();
							Data piece = DownloadQueue.getHead();
							for (int i = 0; i < DownloadQueue.getDepth(); i++) {
								if (piece != null && piece.getName().equals(item)) {
									model.deleteFromModel(piece);
									Manager.getQueue().delete(piece);
									i = DownloadQueue.getDepth();
									infoPanel.remove(statPanel);
								}
								piece = piece.getNext();
							}
						}
					});
					m.add(add);
					m.addSeparator();
					m.add(delete);
					if(item == null) delete.setEnabled(false);
					m.show(list, e.getX(), e.getY());
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		System.setProperty("http.agent", "Chrome");
		mainFrame.setVisible(true);
	}	
	
	public void re(){
		Manager.getQueue();
	}
	
	public static void setPanel(){
		list.setModel(model.getModel());
	}
	
	public static void renewPanel(){
		Data piece = DownloadQueue.getHead();
		if(piece != null){
			infoPanel.remove(statPanel);
			statPanel = piece.getPanel().getPanel();
			infoPanel.add(statPanel);
			mainFrame.validate();
			mainFrame.repaint();
		}
	}
	
	public static Data getSelectedItem(){
		Data cell = null;
		Data piece = DownloadQueue.getHead();
		String item = list.getSelectedValue();
		for(int i = 0; i < DownloadQueue.getDepth(); i++){
			if(piece != null && piece.getName().equals(item)){
				cell = piece;
				i = DownloadQueue.getDepth();
			}
			piece = piece.getNext();
		}
		return cell;
	}
}
