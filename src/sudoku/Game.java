package sudoku;

import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This class generates a game with {@link Generator} into a {@link Grid} and also makes a {@link ButtonInitializer}
 * to make the game visible. Also stores the {@link #time} when the game started.
 */
public class Game {
	private final ButtonInitializer buttonInitializer;
	private Grid active;
	private Grid solved;
	public static long time;

	/**
	 * Constructs a new game instance with {@link Grid} and a {@link ButtonInitializer} by the given hardness.
	 */
	public Game(Grid active, Grid solved) {
		int hardness = AppFrame.hardness;

		if (active == null || solved == null) {
			this.solved = Grid.emptyGrid();

			while(!this.solved.isFullGrid()) {
				Generator generator = new Generator();
				this.solved = generator.generate(0);
			}

			this.active = new Grid(this.solved);
			Generator.eraseCells(this.active, hardness * 4 + 6);
		} else {
			this.active = active;
			this.solved = solved;
		}

		this.buttonInitializer = new ButtonInitializer(this.active);
		this.buttonInitializer.setBorder(new LineBorder(Color.GRAY, 2, true));
		this.buttonInitializer.setBackground(new Color(29, 37, 40));
		this.buttonInitializer.setPreferredSize(new Dimension(600,600));

		Game.time = System.currentTimeMillis();
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

	/**
	 * Saves the current game.
	 */
	public void saveGame() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("game.dat"));
			oos.writeObject(this.active);
			oos.writeObject(this.solved);
			oos.writeLong(Game.time);
			oos.writeInt(AppFrame.hardness);
			oos.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
