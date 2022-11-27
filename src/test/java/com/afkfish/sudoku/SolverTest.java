package com.afkfish.sudoku;

import com.afkfish.sudoku.logic.Grid;
import com.afkfish.sudoku.logic.Solver;
import org.junit.Before;
import org.junit.Test;

public class SolverTest {
	Solver solver;
	Grid grid;

	@Before
	public void setUp() {
		solver = new Solver();
		grid = Grid.emptyGrid();
	}

	@Test
	public void solveTestNoThrow() {
		solver.solve(grid);
	}

}
