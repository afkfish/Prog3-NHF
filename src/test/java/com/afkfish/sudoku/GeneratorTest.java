package com.afkfish.sudoku;

import com.afkfish.sudoku.logic.Generator;
import com.afkfish.sudoku.logic.Grid;
import org.junit.*;

public class GeneratorTest {
	Grid nGrid;
	Generator generator;
	int n;

	@Before
	public void setUp() {
		n = 15;
		generator = new Generator();
		nGrid = generator.generate(n);
	}

	@Test
	public void nEmptyCells() {
		int empty = 0;
		for (int i = 0; i < nGrid.getSize(); i++)
			for (int j = 0; j < nGrid.getSize(); j++)
				if (nGrid.getCell(i, j).isEmpty())
					++empty;

		Assert.assertEquals(n, empty);
	}
}
