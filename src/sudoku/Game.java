package sudoku;

import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * This class generates a game with {@link Generator} into a {@link Grid} and also makes a {@link ButtonInitializer}
 * to make the game visible. Also stores the {@link #time} when the game started.
 */
public class Game {
	private final ButtonInitializer buttonInitializer;
	public static long time;

	public static void main(String[] args) {
		GameDialog startDialog = new GameDialog(null);
		startDialog.setVisible(true);

		RecordsDialog.records.add(new RecordsDialog.Record(13,1));
		RecordsDialog.records.add(new RecordsDialog.Record(2,4));

	}

	/**
	 * Constructs a new game instance with {@link Grid} and a {@link ButtonInitializer} by the given hardness.
	 */
	public Game() {
		int hardness = AppFrame.hardness;
		Grid grid = Grid.emptyGrid();

		while(!grid.isFullGrid()) {
			Generator generator = new Generator();
			grid = generator.generate(0);
		}

		Grid activeGrid = new Grid(grid);
		Generator.eraseCells(activeGrid, hardness * 4 + 6);

		this.buttonInitializer = new ButtonInitializer(activeGrid, grid);
		this.buttonInitializer.setBorder(new LineBorder(Color.GRAY, 2, true));

		Game.time = System.currentTimeMillis();
	}

	/**
	 * Container getter method.
	 * @return the {@link Game}'s configured {@link #buttonInitializer}
	 */
	public ButtonInitializer getButtonInitializer() {
		return this.buttonInitializer;
	}
}
