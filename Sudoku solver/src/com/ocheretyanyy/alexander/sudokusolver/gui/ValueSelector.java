package com.ocheretyanyy.alexander.sudokusolver.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Singleton class for Sudoku solver's cell value selector
 * 
 * @author Alexander Ocheretyanyy
 * @since 29-01-2021
 * @version 1.0
 */
public class ValueSelector extends JPanel implements MouseListener {

	private static final long serialVersionUID = -2442099537214003796L;
	
	private static final ValueSelector DEFAULT_SELECTOR = new ValueSelector();
	
	private static final int gapBetweenCells = 1;
	private static final int numOfValues = 9;
	private static final int width = 3;
	private static final int height = 3;
	private static final Dimension dimension = new Dimension(150, 150);
	private static final Color standardColor = Color.LIGHT_GRAY;
	private static final Color selectionColor = Color.GREEN;
	
	private Integer chosenValue;

	private ValueSelector() {
		setPreferredSize(dimension);
		
		GridLayout layout = new GridLayout(height, width);
		layout.setHgap(gapBetweenCells);
		layout.setVgap(gapBetweenCells);
		setLayout(layout);
		
		addCells();
	}
	
	private void addCells() {
		for(int i = 0; i < numOfValues; i++) {
			SelectorCell cell = new SelectorCell(Integer.toString(i + 1));
			cell.setBackground(standardColor);
			cell.addMouseListener(this);
			add(cell);
		}
	}
	
	protected Integer getChosenValue() {
		return chosenValue;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getComponent() instanceof SelectorCell) {
			PuzzleCell cell = (PuzzleCell) e.getComponent();
			cell.setBackground(selectionColor);
			chosenValue = Integer.parseInt(cell.getText());
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getComponent() instanceof PuzzleCell) {
			PuzzleCell cell = (PuzzleCell) e.getComponent();
			cell.setBackground(standardColor);
			chosenValue = null;
		}
	}
	
	protected static JFrame getDefaultValueSelector(PuzzleRegion region) {
		restartSelector();
		
		JFrame frame = new JFrame();
		frame.setSize(150, 150);
		frame.setUndecorated(true);
		frame.setContentPane(DEFAULT_SELECTOR);
		frame.setLocation(region.getLocationOnScreen());
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		
		return frame;
	}
	
	private static void restartSelector() {
		Component[] allCells = DEFAULT_SELECTOR.getComponents();
		Arrays.stream(allCells).forEach(i -> ((SelectorCell)i).setBackground(standardColor));
	}
}