package com.afkfish.sudoku;

import org.junit.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@FixMethodOrder
public class GridTest {
	Generator generator;
	Grid grid;
	int n;


	@Before
	public void setUp() {
		generator = new Generator();
		grid = Grid.emptyGrid();
		n = 15;
	}

	@Test
	public void emptyGrid() {
		Assert.assertFalse(grid.isFullGrid());
		Assert.assertEquals(Optional.of(grid.getCell(0,0)), grid.getFirstEmptyCell());
	}

	@Test
	public void fullGrid() {
		grid = generator.generate(0);
		Assert.assertTrue(grid.isFullGrid());
	}

	@Test
	public void nEmptyCells() {
		grid = generator.generate(n);

		int empty = 0;
		for (int i = 0; i < grid.getSize(); i++)
			for (int j = 0; j < grid.getSize(); j++)
				if (grid.getCell(i, j).isEmpty())
					++empty;

		Assert.assertEquals(n, empty);
	}

	@Test
	public void setCellValue() {
		grid.getCell(0,0).setValue(9);
		Assert.assertEquals(9, grid.getCell(0,0).getValue());
	}

	@Test
	public void cellNeighbor() {
		Assert.assertEquals(grid.getCell(0, 1), grid.getCell(0,0).getNextCell());

		Collection<Grid.Cell> collection = new ArrayList<>();
		for (int i = 1; i < grid.getSize(); i++) {
			collection.add(grid.getCell(0,i));
		}

		Assert.assertEquals(collection, grid.getCell(0,0).getRowNeighbors());
	}
}
