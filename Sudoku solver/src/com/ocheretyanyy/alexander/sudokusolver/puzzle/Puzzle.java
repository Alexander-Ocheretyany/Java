package com.ocheretyanyy.alexander.sudokusolver.puzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ocheretyanyy.alexander.sudokusolver.SudokuSolverManager;
import com.ocheretyanyy.alexander.sudokusolver.common.XYCoordinates;

/**
 * @author Alexander Ocheretyanyy
 * @since 28-01-2021
 * @version 1.0
 */
public class Puzzle {
	
	private final Region[][] puzzleRegions;
	
	public Puzzle() {
		puzzleRegions = new Region[SudokuSolverManager.NUM_OF_VERTICAL_REGIONS][SudokuSolverManager.NUM_OF_HORIZONTAL_REGIONS];
		initPuzzleRegions();
	}
	
	private void initPuzzleRegions() {
		for(int i = 0; i < SudokuSolverManager.NUM_OF_VERTICAL_REGIONS; i++) {
			for(int j = 0; j < SudokuSolverManager.NUM_OF_HORIZONTAL_REGIONS; j++) {
				puzzleRegions[i][j] = new Region(SudokuSolverManager.REGION_WIDTH, SudokuSolverManager.REGION_HEIGHT);
			}
		}
	}

	private Region getRegionAt(int regionRow, int regionColumn) {
		return puzzleRegions[regionRow][regionColumn];
	}
	
	private Region getRegionWithCellAt(int rowIndex, int columnIndex) {
		int regionRow = rowIndex / SudokuSolverManager.NUM_OF_VERTICAL_REGIONS;
		int regionColumn = columnIndex / SudokuSolverManager.NUM_OF_HORIZONTAL_REGIONS;
		
		return getRegionAt(regionRow, regionColumn);
	}
	
	private List<Cell> getRowOfCells(int index) {
		List<Cell> rowCells = new ArrayList<>();
		
		int rowOfRegions = index / SudokuSolverManager.REGION_HEIGHT;
		int rowIndex = index % SudokuSolverManager.REGION_HEIGHT;
		
		for(int i = 0; i < SudokuSolverManager.NUM_OF_HORIZONTAL_REGIONS; i++) {
			Region currentRegion = puzzleRegions[rowOfRegions][i];
			rowCells.addAll(currentRegion.getRow(rowIndex));
		}
		
		return rowCells;
	}

	private List<Cell> getColumnOfCells(int index) {
		List<Cell> columnCells = new ArrayList<>();
		
		int columnOfRegions = index / SudokuSolverManager.REGION_WIDTH;
		int columnIndex = index % SudokuSolverManager.REGION_WIDTH;
		
		for(int i = 0; i < SudokuSolverManager.NUM_OF_VERTICAL_REGIONS; i++) {
			Region currentRegion = puzzleRegions[i][columnOfRegions]; 
			columnCells.addAll(currentRegion.getColumn(columnIndex));
		}
		
		return columnCells;
	}

	private XYCoordinates getRegionCoordinates(int row, int column) {
		int regionRow = row / SudokuSolverManager.REGION_HEIGHT;
		int regionColumn = column / SudokuSolverManager.REGION_WIDTH;
		
		return new XYCoordinates(regionRow, regionColumn);
	}

	private XYCoordinates getRelativeCellCoordinates(int row, int column) {
		int rowIndex = row % SudokuSolverManager.NUM_OF_VERTICAL_REGIONS;
		int columnIndex = column % SudokuSolverManager.NUM_OF_HORIZONTAL_REGIONS;
		
		return new XYCoordinates(rowIndex, columnIndex);
	}
	
	public boolean isEditableCellAt(int row, int column) {
		XYCoordinates regionCoordinates = getRegionCoordinates(row, column);
		Region region = getRegionAt(regionCoordinates.getX(), regionCoordinates.getY());
		
		XYCoordinates relativeCellCoordinates = getRelativeCellCoordinates(row, column);
		return region.isEditableCellAt(relativeCellCoordinates.getX(), relativeCellCoordinates.getY());
	}
	
	public Integer getValueAt(int row, int column) {
		XYCoordinates regionCoordinates = getRegionCoordinates(row, column);
		Region region = getRegionAt(regionCoordinates.getX(), regionCoordinates.getY());
		
		XYCoordinates relativeCellCoordinates = getRelativeCellCoordinates(row, column);
		return region.getValueAt(relativeCellCoordinates.getX(), relativeCellCoordinates.getY());
	}
	
	public List<Integer> getRowAsValues(int row) {
		List<Cell> rowOfCells = getRowOfCells(row);
		List<Integer> rowValues = new ArrayList<>();
		
		rowOfCells.stream().forEach(i -> rowValues.add(i.getValue()));
		
		return rowValues;
	}
	
	public void setEditableValueAt(Integer value, int row, int column) {
		XYCoordinates regionCoordinates = getRegionCoordinates(row, column);
		XYCoordinates relativeCellCoordinates = getRelativeCellCoordinates(row, column);
		
		Region neededRegion = getRegionAt(regionCoordinates.getX(), regionCoordinates.getY());
		neededRegion.setEditableValueAt(value, relativeCellCoordinates.getX(), relativeCellCoordinates.getY());
	}
	
	public void clearValueAt(int row, int column) {
		setEditableValueAt(SudokuSolverManager.EMPTY_CELL, row, column);
	}
	
	public void setNonEditableValueAt(Integer value, int row, int column) {
		XYCoordinates regionCoordinates = getRegionCoordinates(row, column);
		XYCoordinates relativeCellCoordinates = getRelativeCellCoordinates(row, column);
		
		Region neededRegion = getRegionAt(regionCoordinates.getX(), regionCoordinates.getY());
		neededRegion.setNonEditableValueAt(value, relativeCellCoordinates.getX(), relativeCellCoordinates.getY());
	}
	
	public Set<Integer> getValuesFromRow(int row) {
		Set<Integer> rowValues = new HashSet<>();
		List<Cell> rowCells = getRowOfCells(row);
		
		rowCells.stream().forEach(i -> rowValues.add(i.getValue()));
		
		return rowValues;
	}

	public Set<Integer> getValuesFromColumn(int column) {
		Set<Integer> columnValues = new HashSet<>();
		List<Cell> columnCells = getColumnOfCells(column);
		
		columnCells.stream().forEach(i -> columnValues.add(i.getValue()));
		
		return columnValues;
	}

	public Set<Integer> getValuesFromRegionWithCellAt(int row, int column) {
		Region region = getRegionWithCellAt(row, column);
		return region.getRegionValues();
	}

	public XYCoordinates getPreviousEditable(int row, int column) {
		XYCoordinates coordinates = null;
		
		column--;
		
		while(row >= 0) {
			if(column < 0) {
				column = 8;
				row--;
			}
			else {
				if(isEditableCellAt(row, column)) {
					coordinates = new XYCoordinates(row, column);
					break;
				}
				else {
					column--;
				}
			}
		}
		
		return coordinates;
	}
	
	public void clear() {
		initPuzzleRegions();
	}

	public boolean isValid() {
		if(areAllRegionsValid() && areAllRowsValid() && areAllColumnsValid()) {
			return true;
		}
		return false;
	}
	
	private boolean areAllRegionsValid() {
		for(int i = 0; i < SudokuSolverManager.NUM_OF_VERTICAL_REGIONS; i++) {
			for(int j = 0; j < SudokuSolverManager.NUM_OF_HORIZONTAL_REGIONS; j++) {
				if(!puzzleRegions[i][j].isValid()) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean areAllRowsValid() {
		for(int i = 0; i < SudokuSolverManager.REGION_HEIGHT * SudokuSolverManager.NUM_OF_VERTICAL_REGIONS; i++) {
			List<Cell> rowOfCells = getRowOfCells(i);
			
			long numOfValues = rowOfCells.stream().filter(c -> c.getValue() != SudokuSolverManager.EMPTY_CELL).count();
			
			Set<Integer> distinctValues = getValuesFromRow(i);
			
			if(distinctValues.contains(SudokuSolverManager.EMPTY_CELL)) {
				distinctValues.remove(SudokuSolverManager.EMPTY_CELL);
			}
			
			if(numOfValues != distinctValues.size()) {
				return false;
			}
		}
		return true;
	}
	
	private boolean areAllColumnsValid() {
		for(int i = 0; i < SudokuSolverManager.REGION_WIDTH * SudokuSolverManager.NUM_OF_HORIZONTAL_REGIONS; i++) {
			List<Cell> columnOfCells = getColumnOfCells(i);
			
			long numOfValues = columnOfCells.stream().filter(c -> c.getValue() != SudokuSolverManager.EMPTY_CELL).count();
			
			Set<Integer> distinctValues = getValuesFromColumn(i);
			if(distinctValues.contains(SudokuSolverManager.EMPTY_CELL)) {
				distinctValues.remove(SudokuSolverManager.EMPTY_CELL);
			}
			
			if(numOfValues != distinctValues.size()) {
				return false;
			}
		}
		
		return true;
	}
}