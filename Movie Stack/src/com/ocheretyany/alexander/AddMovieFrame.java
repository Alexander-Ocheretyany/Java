package com.ocheretyany.alexander;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class AddMovieFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	private final int FRAME_WIDTH = 350;
	private final int FRAME_HEIGHT = 80;
	private final String FRAME_TITLE = "Add movie!";
	private final String BUTTON_LABEL = "Add!";
	private final String MOVIE_TITLE_FIELD_DEFAULT_TEXT = "Title...";
	private final int MOVIE_TITLE_FIELD_DEFAULT_WIDTH = 20;
	private final JTextField MOVIE_TITLE_FIELD = new JTextField(MOVIE_TITLE_FIELD_DEFAULT_TEXT, MOVIE_TITLE_FIELD_DEFAULT_WIDTH);
	private final JButton ACCEPT_BUTTON = new JButton();
	
	public AddMovieFrame() {	
		initAll();		
	}
	
	private void initButton() {	
		ACCEPT_BUTTON.setText(BUTTON_LABEL);
		ACCEPT_BUTTON.addActionListener(new AddMovieFrameListener(this));	
	}
	
	private void initFrame() {	
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle(FRAME_TITLE);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new FlowLayout());
		
		MOVIE_TITLE_FIELD.selectAll();
		
		this.add(MOVIE_TITLE_FIELD);
		this.add(ACCEPT_BUTTON);
		
		this.getRootPane().setDefaultButton(ACCEPT_BUTTON);
		
		this.setVisible(true);	
	}
	
	private void initAll() {	
		initButton();
		initFrame();
	}
	
	public String getMovieTitle() {
		return MOVIE_TITLE_FIELD.getText().trim();
	}
}
