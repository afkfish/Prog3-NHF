package sudoku;

import java.util.Random;

/**
 * A Generator to generate random Sudoku {@link Grid} instances.
 */
public class Generator {
	private final Solver solver;

	/**
	 * Constructs a new Generator instance.
	 */
	public Generator() {
		this.solver = new Solver();
	}

	/**
	 * Generates a random {@link Grid} instance with the given number of empty {@link Grid.Cell}s.
	 * <br><br>
	 * Note: The complexity for a human player increases with a higher amount of empty {@link Grid.Cell}s.
	 * @param numberOfEmptyCells the number of empty {@link Grid.Cell}s
	 * @return a randomly filled Sudoku {@link Grid} with the given number of empty {@link Grid.Cell}s
	 */
	public Grid generate(int numberOfEmptyCells) {
		Grid grid = Grid.emptyGrid();
		solver.solve(grid);

		eraseCells(grid, numberOfEmptyCells);

		return grid;
	}

	/**
	 * Erases a given number or {@link Grid.Cell}s from the specified {@link Grid}.
	 * @param grid the {@link Grid} from which to erase the {@link Grid.Cell}s
	 * @param numberOfEmptyCells the number of {@link Grid.Cell}s to erase
	 */
	public static void eraseCells(Grid grid, int numberOfEmptyCells) {
		Random random = new Random();
		for (int i = 0; i < numberOfEmptyCells; i++) {
			int randomRow = random.nextInt(9);
			int randomColumn = random.nextInt(9);

			Grid.Cell cell = grid.getCell(randomRow, randomColumn);
			if (!cell.isEmpty()) {
				cell.setValue(0);
				cell.isOriginal = false;
			} else {
				i--;
			}
		}
	}
}
