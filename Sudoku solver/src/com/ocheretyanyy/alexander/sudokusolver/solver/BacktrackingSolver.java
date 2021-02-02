package com.ocheretyanyy.alexander.sudokusolver.solver;

import java.util.Set;

import com.ocheretyanyy.alexander.sudokusolver.common.XYCoordinates;
import com.ocheretyanyy.alexander.sudokusolver.puzzle.Puzzle;

/**
 * @author Alexander Ocheretyanyy
 * @since 28-01-2021
 * @version 1.0
 */
public class BacktrackingSolver implements Solver {
	private Puzzle puzzle;
	
	public BacktrackingSolver(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	@Override
	public void solve() throws InvalidPuzzleException {
		
		if(!puzzle.isValid()) {
			throw new InvalidPuzzleException();
		}
		
		int row = 0;
		int column = 0;
		
		while(row < 9) {		
			if(puzzle.isEditableCellAt(row, column)) {
				if(tryNextValueAt(row, column)) {
					column++;
				}
				else {
					XYCoordinates newCoordinates = backTrackFrom(row, column);
					
					if(newCoordinates == null) {
						throw new InvalidPuzzleException();
					}
					
					row = newCoordinates.getX();
					column = newCoordinates.getY();
				}
			} 
			else {
				column++;
			}
			
			if(column >= 9) {
				column = 0;
				row++;
			}
		}
	}
	
	private boolean tryNextValueAt(int row, int column) {
		boolean result = false;
		
		int value = puzzle.getValueAt(row, column);
		++value;
		
		while(value <= 9) {					
			if(canSubstituteValueAt(value, row, column)) {
				puzzle.setEditableValueAt(value, row, column);
				result = true;
				break;
			}
			++value;
		}
		
		return result;
	}

	private XYCoordinates backTrackFrom(int row, int column) {
		puzzle.clearValueAt(row, column);
		return puzzle.getPreviousEditable(row, column);
	}
	
	private boolean canSubstituteValueAt(int value, int rowIndex, int columnIndex) {
		Set<Integer> rowValues = puzzle.getValuesFromRow(rowIndex);
		Set<Integer> columnValues = puzzle.getValuesFromColumn(columnIndex);
		Set<Integer> regionValues = puzzle.getValuesFromRegionWithCellAt(rowIndex, columnIndex);
		
		if(rowValues.contains(value) || columnValues.contains(value) || regionValues.contains(value)) {
			return false;
		}
		
		return true;
	}
}