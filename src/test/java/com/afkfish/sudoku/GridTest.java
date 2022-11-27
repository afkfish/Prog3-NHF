package com.afkfish.sudoku;

import com.afkfish.sudoku.logic.Grid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class GridTest {
	Grid grid;
	Collection<Grid.Cell> collection;

	@Before
	public void setUpGrids() {
		grid = Grid.emptyGrid();

		collection = new ArrayList<>();
		for (int i = 1; i < grid.getSize(); i++) {
			collection.add(grid.getCell(0,i));
		}
	}

	@Test
	public void emptyGrid() {
		Assert.assertFalse(grid.isFullGrid());
		Assert.assertEquals(Optional.of(grid.getCell(0,0)), grid.getFirstEmptyCell());
	}

	@Test
	public void setCellValue() {
		grid.getCell(0,0).setValue(9);
		Assert.assertEquals(9, grid.getCell(0,0).getValue());
	}

	@Test
	public void cellNeighbor() {
		Assert.assertEquals(grid.getCell(0, 1), grid.getCell(0,0).getNextCell());
		Assert.assertEquals(collection, grid.getCell(0,0).getRowNeighbors());
	}
}
