package com.afkfish.sudoku;

import com.afkfish.sudoku.logic.Game;
import com.afkfish.sudoku.logic.Generator;
import com.afkfish.sudoku.logic.Grid;
import org.junit.*;

public class GameTest {
	Generator generator;
	Game game;
	Grid active;
	Grid solved;
	long currentStartTime;

	@Before
	public void setUpGrids() {
		generator = new Generator();
		solved = generator.generate();
		active = new Grid(solved);
		Generator.eraseCells(active, 12);
		game = new Game(active, solved);
	}

	@Before
	public void setUpTime() {
		currentStartTime = System.currentTimeMillis();
		Game.setCurrentStartTime();
	}

	@Test
	public void gameInit() {
		Assert.assertEquals(active, game.getActive());
		Assert.assertEquals(solved, game.getSolved());
	}

	@Test
	public void timeSinceGameStart() {
		Assert.assertEquals((int) ((System.currentTimeMillis() - currentStartTime)/1000), Game.timeSinceGameStart(), 1);
		Game.setElapsedTime(10);
		Assert.assertEquals((int) (10 + (System.currentTimeMillis() - currentStartTime)/1000), Game.timeSinceGameStart(), 1);
	}
}
