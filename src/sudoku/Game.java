package sudoku;

import java.awt.*;

public class Game {
	private final ButtonContainer container;
	private final int hardness;
	public static long time;

	public static void main(String[] args) {
		AppFrame.setHardness(2);
		Game game = new Game();
		AppFrame appFrame = new AppFrame(game);
		appFrame.setVisible(true);
	}

	public Game() {
		this.hardness = AppFrame.hardness;
		Grid grid = Grid.emptyGrid();
		while(!grid.isFullGrid()) {
			Generator generator = new Generator();
			grid = generator.generate(0);
		}
		Grid activegrid = new Grid(grid);
		Generator.eraseCells(activegrid, AppFrame.hardness * 6 + 6);

		this.container = new ButtonContainer(activegrid, grid);
		this.container.setBorder(new RoundedBorder(10, Color.GRAY));
		Game.time = System.currentTimeMillis();
		//System.out.println(grid);
	}

	public ButtonContainer getContainer() {
		return this.container;
	}
}
