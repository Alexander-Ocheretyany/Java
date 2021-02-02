package com.ocheretyanyy.alexander.sudokusolver.gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;

import com.ocheretyanyy.alexander.sudokusolver.SudokuSolverManager;

/**
 * @author Alexander Ocheretyanyy
 * @since 29-01-2021
 * @version 1.0
 */
public class PuzzleFrame extends JFrame {

	private static final long serialVersionUID = 1316343518347737163L;
	
	private final SudokuSolverManager solver;
	private final String title = "Sudoku solver";
	private final String iconPath = "graphics/icon.png";
	
	private PuzzleBoard board;

	protected void solve() {
		solver.solve();
	}
	
	public PuzzleFrame(SudokuSolverManager solver) {
		this.solver = solver;
		board = new PuzzleBoard();
		setTitle(title);
		setSize(520, 650);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(iconPath));
		
		setLayout(new BorderLayout());
		
		NavigationPanel navigationPanel = new NavigationPanel(this);
		getContentPane().add(board, BorderLayout.CENTER);
		getContentPane().add(navigationPanel, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	public List<Integer> getRow(int row) {
		return board.getRow(row);
	}
	
	public void writeRow(List<Integer> values, int row) {
		board.writeRow(values, row);
	}
	
	public void clear() {
		board.clear();
		invalidate();
		repaint();
	}

	public void makeNonEditable() {
		board.markAsSolved();
	}
}