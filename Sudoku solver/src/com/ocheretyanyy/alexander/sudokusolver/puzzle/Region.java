package com.ocheretyanyy.alexander.sudokusolver.puzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ocheretyanyy.alexander.sudokusolver.SudokuSolverManager;

/**
 * @author Alexander Ocheretyanyy
 * @since 28-01-2021
 * @version 1.0
 */
public class Region {
	private final int width;
	private final int height;
	private final Cell[][] regionCells;
	
	protected Region(int width, int height) {
		this.width = width;
		this.height = height;
		regionCells = new Cell[height][width];
		initRegionCells();
	}
	
	protected void initRegionCells() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				regionCells[i][j] = new Cell(null, true);
			}
		}
	}

	protected Cell getCellAt(int row, int column) {
		return regionCells[row][column];
	}
	
	protected List<Cell> getRow(int index) {
		List<Cell> rowCells = new ArrayList<>();
		
		for(int i = 0; i < width; i++) {
			rowCells.add(regionCells[index][i]);
		}
		
		return rowCells;
	}

	protected List<Cell> getColumn(int index) {
		List<Cell> columnCells = new ArrayList<>();
	
		for(int i = 0; i < height; i++) {
			columnCells.add(regionCells[i][index]);
		}
		
		return columnCells;
	}

	protected Set<Integer> getRegionValues() {
		Set<Integer> regionValues = new HashSet<>();
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				regionValues.add(regionCells[i][j].getValue());
			}
		}
		return regionValues;
	}
	
	protected Integer getValueAt(int row, int column) {
		Cell cell = getCellAt(row, column);
		return cell.getValue();
	}
	
	protected void setEditableValueAt(int value, int row, int column) {
		Cell cell = getCellAt(row, column);
		cell.setValue(value);
		cell.setEditable(true);
	}
	
	protected void setNonEditableValueAt(int value, int row, int column) {
		Cell cell = getCellAt(row, column);
		cell.setValue(value);
		cell.setEditable(false);
	}

	protected boolean isEditableCellAt(int row, int column) {
		Cell cell = getCellAt(row, column);
		return cell.isEditable();
	}

	protected boolean isValid() {
		Set<Integer> seenValues = new HashSet<>();
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				Cell currentCell = regionCells[i][j];
				Integer value = currentCell.getValue();
				
				if(seenValues.contains(value) || !currentCell.isValid()) {
					return false;
				}
				else if(value != SudokuSolverManager.EMPTY_CELL) {
					seenValues.add(value);
				}
			}
		}
		
		return true;
	}
}