package com.ocheretyanyy.alexander.sudokusolver.puzzle;

import com.ocheretyanyy.alexander.sudokusolver.SudokuSolverManager;

/**
 * Class for Sudoku cells. The instance variable {@link Cell#editable} is false for the
 * cells that are filled in the initial puzzle, for other cells this flag should be set
 * to true.
 * 
 * @author Alexander Ocheretyanyy
 * @since 28-01-2021
 * @version 1.0
 */
public class Cell {
	private Integer value;
	private boolean editable;
	
	protected Cell(Integer value, boolean editable) {
		this.value = value;
		this.editable = editable;
	}
	
	protected Integer getValue() {
		return value;
	}
	
	protected void setValue(Integer value) {
		this.value = value;
	}

	protected void setEditable(boolean value) {
		editable = value;
	}
	
	protected boolean isEditable() {
		return editable;
	}
	
	protected boolean isValid() {
		boolean valueIsInCorrectRange = value >= SudokuSolverManager.MIN_VALUE && value <= SudokuSolverManager.MAX_VALUE;
		
		if(valueIsInCorrectRange || value == SudokuSolverManager.EMPTY_CELL) {
			return true;
		}
		
		return false;
	}
}