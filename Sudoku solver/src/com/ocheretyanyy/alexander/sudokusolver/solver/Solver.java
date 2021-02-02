package com.ocheretyanyy.alexander.sudokusolver.solver;

/**
 * @author Alexander Ocheretyanyy
 * @since 28-01-2021
 * @version 1.0
 */
@FunctionalInterface
public interface Solver {
	void solve() throws InvalidPuzzleException;
}