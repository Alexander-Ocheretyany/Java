package com.ocheretyanyy.alexander.sudokusolver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import com.ocheretyanyy.alexander.sudokusolver.common.ErrorHandler;
import com.ocheretyanyy.alexander.sudokusolver.gui.PuzzleFrame;
import com.ocheretyanyy.alexander.sudokusolver.puzzle.Puzzle;
import com.ocheretyanyy.alexander.sudokusolver.solver.BacktrackingSolver;
import com.ocheretyanyy.alexander.sudokusolver.solver.InvalidPuzzleException;
import com.ocheretyanyy.alexander.sudokusolver.solver.Solver;

/**
 * @author Alexander Ocheretyanyy
 * @since 28-01-2021
 * @version 1.0
 */
public class SudokuSolverManager {
	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE = 9;
	public static final int REGION_WIDTH = 3;
	public static final int REGION_HEIGHT = 3;
	public static final int EMPTY_CELL = 0; 
	public static final int NUM_OF_HORIZONTAL_REGIONS = 3;
	public static final int NUM_OF_VERTICAL_REGIONS = 3;
	
	private static SudokuSolverManager defaultManager = new SudokuSolverManager();
	private static PuzzleFrame puzzleFrame;
	
	private int width = 9;
	private int height = 9;
	private Puzzle puzzle;
	private Solver solver;
	
	public SudokuSolverManager() {
		puzzle = new Puzzle();
		solver = new BacktrackingSolver(puzzle);
		
		SudokuSolverManager solver = this;
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				puzzleFrame = new PuzzleFrame(solver);
			}
		});
	}
	
	private void readBoard() {
		for(int i = 0; i < height; i++) {
			List<Integer> rowValues = puzzleFrame.getRow(i);
			
			for(int j = 0; j < width; j++) {
				int value = rowValues.get(j);
				
				if(value != 0) {
					puzzle.setNonEditableValueAt(value, i, j);
				} else {
					puzzle.setEditableValueAt(value, i, j);
				}
			}
		}
	}

	private void fillInFromPuzzle() {
		for(int i = 0; i < height; i++) {
			List<Integer> values = puzzle.getRowAsValues(i);
			puzzleFrame.writeRow(values, i);
		}
	}
	
	public void solve() {
		readBoard();
		try {
			solver.solve();
			fillInFromPuzzle();
			puzzleFrame.makeNonEditable();
		}
		catch (InvalidPuzzleException e) {
			ErrorHandler.invalidPuzzleError(puzzleFrame);
			puzzle.clear();
		}
	}
	
	public void openFile(String path) {		
		clearPuzzleAndBoard();
		
		try(FileInputStream stream = new FileInputStream(path)) {
			Scanner scanner = new Scanner(stream);
			
			readUsingScanner(scanner);
			
			scanner.close();
			
			fillInFromPuzzle();
		}
		catch(IOException e) {
			ErrorHandler.cannotReadFileError(puzzleFrame);
		}
	}
	
	public void clearPuzzleAndBoard() {
		puzzle.clear();
		puzzleFrame.clear();
	}
	
	private void readUsingScanner(Scanner scanner) {
		for(int k = 0; k < height; k++) {
			while(!scanner.hasNext()) {}
			String[] values = scanner.nextLine().split(" ");
			
			if(values.length != SudokuSolverManager.NUM_OF_HORIZONTAL_REGIONS * SudokuSolverManager.REGION_WIDTH) {
				ErrorHandler.wrongFileError(puzzleFrame);
			}
			
			for(int i = 0; i < values.length; i++) {
				try {
					int value = Integer.parseInt(values[i]);
					
					if(value != SudokuSolverManager.EMPTY_CELL
								&& (value < SudokuSolverManager.MIN_VALUE || value > SudokuSolverManager.MAX_VALUE)) {
						ErrorHandler.wrongFileError(puzzleFrame);
					}
					
					if(value != SudokuSolverManager.EMPTY_CELL) {
						puzzle.setNonEditableValueAt(value, k, i);
					} else {
						puzzle.setEditableValueAt(value, k, i);
					}
				}
				catch(NumberFormatException e) {
					ErrorHandler.wrongFileError(puzzleFrame);
				}
			}
		}
	}
	
	public static SudokuSolverManager getDefaultManager() {
		return defaultManager;
	}
	
	public static void main(String[] args) {
	}
}