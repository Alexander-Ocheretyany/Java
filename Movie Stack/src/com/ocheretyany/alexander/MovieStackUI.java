package com.ocheretyany.alexander;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MovieStackUI {

	public static String ADD_BUTTON_LABEL = "Add movie!";
	public static String GET_BUTTON_LABEL = "Get movie!";
	
	private final String TITLE = "MovieStack";
	private final JFrame MAIN_FRAME;
	private final int WIDTH_OF_MAIN_FRAME = 300;
	private final int HEIGHT_OF_MAIN_FRAME = 70;
	private final JButton GET_MOVIE_BUTTON = new JButton();
	private final JButton ADD_MOVIE_BUTTON = new JButton();
	private final ActionListener MAIN_BUTTONS_LISTENER = new MainButtonsListener(); 
	
	public MovieStackUI() {
		
		MAIN_FRAME = new JFrame(TITLE);
		initAll();
		this.buildMainPanel();
		MAIN_FRAME.setVisible(true);
		
	}
	
	private void initMainFrame() {
		
		MAIN_FRAME.setSize(WIDTH_OF_MAIN_FRAME, HEIGHT_OF_MAIN_FRAME);
		MAIN_FRAME.setResizable(false);
		MAIN_FRAME.setLocationRelativeTo(null);
		MAIN_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	private void initButtons() {
		
		ADD_MOVIE_BUTTON.setText(ADD_BUTTON_LABEL);
		GET_MOVIE_BUTTON.setText(GET_BUTTON_LABEL);
		
		ADD_MOVIE_BUTTON.addActionListener(MAIN_BUTTONS_LISTENER);
		GET_MOVIE_BUTTON.addActionListener(MAIN_BUTTONS_LISTENER);
		
	}
	
	private void initAll() {
		initMainFrame();
		initButtons();
	}
	
	private void buildMainPanel() {
	
		Container container = MAIN_FRAME.getContentPane();
		
		container.setLayout(new FlowLayout());

		container.add(ADD_MOVIE_BUTTON);		
		container.add(GET_MOVIE_BUTTON);
		
		container.setSize(WIDTH_OF_MAIN_FRAME, HEIGHT_OF_MAIN_FRAME);
		
	}

}
