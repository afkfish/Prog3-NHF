package com.afkfish.sudoku;

import com.afkfish.sudoku.gui.components.ButtonInitializer;
import com.afkfish.sudoku.logic.Generator;
import com.afkfish.sudoku.logic.Grid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

@FixMethodOrder
public class NumberButtonTest {
	Grid active;
	Grid solved;
	ButtonInitializer buttonInitializer;

	@Before
	public void setUp() {
		this.solved = Grid.emptyGrid();

		while(!this.solved.isFullGrid()) {
			Generator generator = new Generator();
			this.solved = generator.generate(0);
		}

		this.active = new Grid(this.solved);
		Generator.eraseCells(this.active, 12);
	}

	@Test
	public void numberButtonInit() {
		buttonInitializer = new ButtonInitializer(this.active);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int n = buttonInitializer.getButtons().get(i*9+j).getNumber();
				Assert.assertEquals(n, active.getCell(i, j).getValue());
			}
		}
	}
}
