package com.ocheretyany.alexander;

import java.awt.Desktop;
import java.net.URI;

import javax.swing.JOptionPane;

public class GetMovieFrame extends JOptionPane {
	
	private static final long serialVersionUID = 1L;
	
	private final String FRAME_TITLE = "Get movie!";
	private final String SEARCH_ENGINE = "www.yandex.ru";
	private final String SEARCH_PREFIX = "/search/?text=";
	private final String SEARCH_TEXT = "смотреть+онлайн";

	public GetMovieFrame() {
		initAll();
	}
	
	private void initFrame() {
		
		try {

			int choice = JOptionPane.NO_OPTION;
			String movieTitle = null;
			
			while(choice == JOptionPane.NO_OPTION) {
				movieTitle = MovieStackUtilities.getMovieTitle();
				choice = JOptionPane.showConfirmDialog(null, movieTitle, FRAME_TITLE, YES_NO_OPTION);
			}
			
			if(choice == JOptionPane.YES_OPTION) {
				
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
					Desktop.getDesktop().browse(new URI(SEARCH_ENGINE + SEARCH_PREFIX + movieTitle.replace(' ', '+') + "+" + SEARCH_TEXT));

				}
				
				MovieStackUtilities.removeMovieTitle(movieTitle);
				
			}

		} catch (NoMovieTitlesException e) {
			JOptionPane.showConfirmDialog(null,"No movies in the database!", FRAME_TITLE,
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		} catch (Exception e) {
			MovieStackUtilities.reportErrorAndFinish();
		}
		
	}
	
	private void initAll() {
		initFrame();
	}
	
}
