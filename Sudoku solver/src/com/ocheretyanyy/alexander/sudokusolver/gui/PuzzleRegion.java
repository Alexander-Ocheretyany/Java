package com.ocheretyanyy.alexander.sudokusolver.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Alexander Ocheretyanyy
 * @since 29-01-2021
 * @version 1.0
 */
public class PuzzleRegion extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = 2900513186327516456L;
	private final int gap = 1;
	private final int width = 3;
	private final int height = 3;
	private PuzzleCell[][] regionCells;
	private JFrame selector;
	private boolean readOnly = false;

	public PuzzleRegion() {
		regionCells = new PuzzleCell[height][width];
		
		GridLayout layout = new GridLayout(height, width);
		layout.setHgap(gap);
		layout.setVgap(gap);
		setLayout(layout);
		
		addPuzzleCells();
		
		setVisible(true);
	}
	
	private void addPuzzleCells() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				PuzzleCell cell = new PuzzleCell();
				cell.addMouseListener(this);
				regionCells[i][j] = cell;
				add(cell);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!readOnly) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				selector = ValueSelector.getDefaultValueSelector(this);
			}
			else if(e.getButton() == MouseEvent.BUTTON3) {
				PuzzleCell currentCell = (PuzzleCell) e.getComponent();
				currentCell.clear();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!readOnly && e.getButton() == MouseEvent.BUTTON1) {
			if(selector.getContentPane() instanceof ValueSelector) {
				ValueSelector vSelector = (ValueSelector)selector.getContentPane();
				PuzzleCell currentCell = (PuzzleCell) e.getComponent();
				
				if(vSelector.getChosenValue() != null) {
					currentCell.setValue(vSelector.getChosenValue());
				}
				
				selector.dispose();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getComponent() instanceof PuzzleCell) {
			PuzzleCell cell = (PuzzleCell) e.getComponent();
			cell.setBackground(Color.LIGHT_GRAY);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getComponent() instanceof PuzzleCell) {
			PuzzleCell cell = (PuzzleCell) e.getComponent();
			cell.setBackground(Color.WHITE);
		}
	}
	
	protected List<Integer> getRow(int row) {
		List<Integer> values = new ArrayList<>();
		for(int i = 0; i < width; i++) {
			values.add(regionCells[row][i].getValue());
		}
		return values;
	}

	protected void writeRow(List<Integer> values, int row) {
		for(int i = 0; i < width; i++) {
			regionCells[row][i].setValue(values.get(i));
		}
	}

	protected void setValueAt(int value, int row, int column) {
		regionCells[row][column].setValue(value);
	}

	protected void makeReadOnly() {
		readOnly = true;
	}

	protected void clear() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				regionCells[i][j].clear();
			}
		}
		readOnly = false;
	}
}