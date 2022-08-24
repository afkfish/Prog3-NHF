package sudoku;

public class Game {
	private final ButtonContainer container;

	private static int hardness = 0;

	public static long time;

	public static void main(String[] args) {
		Game.time = System.currentTimeMillis();
		Game.setHardness(0);
		Game game = new Game();
		AppFrame appFrame = new AppFrame(game);
		appFrame.setVisible(true);
	}

	public Game() {
		Grid grid = Grid.emptyGrid();
		while(!grid.isFullGrid()) {
			Generator generator = new Generator();
			grid = generator.generate(0);
		}
		Grid activegrid = new Grid(grid);
		Generator.eraseCells(activegrid, hardness * 6 + 1);

		this.container = new ButtonContainer(activegrid, grid);
		//System.out.println(grid);
	}

	public static void setHardness(int hardness) {
		Game.hardness = hardness;
	}

	public ButtonContainer getContainer() {
		return this.container;
	}
}
