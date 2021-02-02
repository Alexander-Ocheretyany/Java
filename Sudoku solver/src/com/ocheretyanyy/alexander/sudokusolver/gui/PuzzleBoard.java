package com.ocheretyanyy.alexander.sudokusolver.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author Alexander Ocheretyanyy
 * @since 29-01-2021
 * @version 1.0
 */
public class PuzzleBoard extends JPanel {
	private static final long serialVersionUID = 8726175281895440722L;
	private Dimension dimension = new Dimension(500, 500);
	private final int borderWidth = 4;
	private final int gap = 2;
	private int height = 3;
	private int width = 3;
	private int lengthOfRaw = 9;
	private PuzzleRegion[][] puzzleRegions;
	private boolean solved = false;
	
	public PuzzleBoard() {
		puzzleRegions = new PuzzleRegion[height][width];
		setPreferredSize(dimension);
		setSize(dimension);
		
		GridLayout layout = new GridLayout(height, width);
		layout.setHgap(gap);
		layout.setVgap(gap);
		setLayout(layout);
		
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createLineBorder(Color.black, borderWidth));
		
		initAndAddPuzzleRegions();
		
		setVisible(true);
	}
	
	private void initAndAddPuzzleRegions() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				PuzzleRegion region = new PuzzleRegion();
				puzzleRegions[i][j] = region;
				add(region);
			}
		}
	}
	
	protected List<Integer> getRow(int row) {
		List<Integer> rowValues = new ArrayList<>();
		
		int regionRow = row / height;
		int relativeRow = row % height;
		
		for(int i = 0; i < width; i++) {
			rowValues.addAll(puzzleRegions[regionRow][i].getRow(relativeRow));
		}
		
		return rowValues;
	}

	protected void writeRow(List<Integer> values, int row) {
		for(int i = 0; i < lengthOfRaw; i++) {
			int regionRow = row / height;
			int regionColumn = i / width;
			
			int relativeRow = row % height;
			int relativeColumn = i % width;
			
			puzzleRegions[regionRow][regionColumn].setValueAt(values.get(i), relativeRow, relativeColumn);
		}
	}

	protected void markAsSolved() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				puzzleRegions[i][j].makeReadOnly();
			}
		}
		solved = true;
	}
	
	protected boolean isSolved() {
		return solved;
	}

	protected void clear() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				puzzleRegions[i][j].clear();
			}
		}
		solved = false;
	}
}