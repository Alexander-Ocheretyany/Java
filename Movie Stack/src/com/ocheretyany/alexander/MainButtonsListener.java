package com.ocheretyany.alexander;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainButtonsListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(MovieStackUI.ADD_BUTTON_LABEL)) {
			
			new AddMovieFrame();
			
		}
		else if(e.getActionCommand().equals(MovieStackUI.GET_BUTTON_LABEL)) {
			
			new GetMovieFrame();
			
		}
	}
}
