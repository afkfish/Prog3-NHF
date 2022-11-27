package com.afkfish.sudoku.logic;

import com.afkfish.sudoku.gui.AppFrame;
import com.afkfish.sudoku.gui.components.ButtonInitializer;

import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This class generates a game with {@link Generator} into a {@link Grid} and also makes a {@link ButtonInitializer}
 * to make the game visible. Also stores the {@link #elapsedTime} when the game started.
 */
public class Game {
	private final ButtonInitializer buttonInitializer;
	private final Grid active;
	private final Grid solved;
	private static long currentStartTime;
	private static long elapsedTime = 0;

	/**
	 * Constructs a new game instance with {@link Grid} and a {@link ButtonInitializer} by the given hardness.
	 */
	public Game(Grid active, Grid solved) {
		int hardness = AppFrame.hardness;

		if (active == null || solved == null) {
			Generator generator = new Generator();
			this.solved = generator.generate();

			this.active = this.solved.copy();
			Generator.eraseCells(this.active, hardness * 4 + 6);
		} else {
			this.active = active;
			this.solved = solved;
		}

		this.buttonInitializer = new ButtonInitializer(this.active);
		this.buttonInitializer.setBorder(new LineBorder(Color.GRAY, 2, true));
		this.buttonInitializer.setMinimumSize(new Dimension(576, 576));
		this.buttonInitializer.setPreferredSize(new Dimension(576,576));
	}

	/**
	 * Container getter method.
	 * @return the {@link Game}'s configured {@link #buttonInitializer}
	 */
	public ButtonInitializer getButtonInitializer() {
		return this.buttonInitializer;
	}

	public Grid getActive() {
		return active;
	}

	public Grid getSolved() {
		return solved;
	}


	public static void setCurrentStartTime() {
		currentStartTime = System.currentTimeMillis();
	}

	public static void setElapsedTime(long time) {
		elapsedTime = time;
	}


	public static int timeSinceGameStart() {
		return (int)(elapsedTime + (System.currentTimeMillis() - currentStartTime) / 1000);
	}

	/**
	 * Saves the current game.
	 */
	public void saveGame() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("game.dat"));
			oos.writeObject(this.active);
			oos.writeObject(this.solved);
			oos.writeLong(timeSinceGameStart());
			oos.writeInt(AppFrame.hardness);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
