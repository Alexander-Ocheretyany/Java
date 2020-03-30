package com.ocheretyany.alexander;

import javax.swing.SwingUtilities;

public class MovieStackEngine {
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MovieStackUI();
			}
		});
		
	}
	
}
