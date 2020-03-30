package com.ocheretyany.alexander;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddMovieFrameListener implements ActionListener{

	private final AddMovieFrame PARENT_FRAME;
	
	public AddMovieFrameListener(AddMovieFrame parentFrame) {
		
		this.PARENT_FRAME = parentFrame;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(!PARENT_FRAME.getMovieTitle().equals("")) {
			try {
				MovieStackUtilities.addMovieTitle(PARENT_FRAME.getMovieTitle());
				PARENT_FRAME.dispose();
			} catch (IOException e1) {
				MovieStackUtilities.reportErrorAndFinish();
			}
		}
			
	}

}
