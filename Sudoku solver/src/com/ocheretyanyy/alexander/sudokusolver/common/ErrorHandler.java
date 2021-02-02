package com.ocheretyanyy.alexander.sudokusolver.common;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * @author Alexander Ocheretyanyy
 * @since 29-01-2021
 * @version 1.0
 */
public class ErrorHandler {
	private static final String ERROR = "Error!";
	private static final String WRONG_FILE = "Wrong file!";
	private static final String CANNOT_READ_FILE = "Cannot read the file!";
	private static final String INVALID_PUZZLE = "Invalid puzzle!";
	
	public static void wrongFileError(Component component) {
		JOptionPane.showMessageDialog(component, WRONG_FILE, ERROR, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void invalidPuzzleError(Component component) {
		JOptionPane.showMessageDialog(component, INVALID_PUZZLE, ERROR, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void cannotReadFileError(Component component) {
		JOptionPane.showMessageDialog(component, CANNOT_READ_FILE, ERROR, JOptionPane.ERROR_MESSAGE);
	}
}