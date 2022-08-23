package sudoku;

import java.util.ArrayList;
import java.util.Random;

public class Puzzle {

	// 9x9 puzzle
	private int puzzle[][] = new int[9][9];

	// generate a completely solved sudoku board
	public int[][] generate() {

		// set all values of puzzle to 0
		// 0 denotes an empty square
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				puzzle[i][j] = 0;
			}
		}

		// start solving the board at the first square
		solve(0, 0);

		return puzzle;
	}

	private void solve(int row, int col) {

		Random gen = new Random();
		boolean numFound = false;

		// list of numbers to try to place in square
		ArrayList<Integer> nums = new ArrayList<>();

		// fill list with numbers 1-9
		for (int i = 1; i < 10; i++) {
			nums.add(i);
		}

		// while we still have numbers to try and have not found a valid number
		while (!nums.isEmpty() || !numFound) {

			// pick random number from list of available numbers
			int num = nums.get(gen.nextInt(nums.size()));

			// check if generated number is valid
			if (checkRow(row, num) && checkCol(col, num) && checkSection(row, col, num)) {

				// add number to square
				puzzle[row][col] = num;
				numFound = true;
				break;

            } else {
                // remove number from list of available numbers
                nums.remove(Integer.valueOf(num));
                // if we are out of numbers, stop trying to find a number
                if (nums.isEmpty())
                    break;
            }

		}

		// if out of numbers go back 1 square
        if (nums.isEmpty()) {
            back(row, col);
        }

		// if a number was added and there are still more empty squares
		// go forward 1 square
		else if (numFound && emptyCheck()) {
			next(row, col);
		}
	}

	// move to the next square
	private void next(int row, int col) {
		if (col == 8)
			solve(row + 1, 0);
		else
			solve(row, col + 1);
	}

	// move to the previous square
	private void back(int row, int col) {
		if (col == 0)
			solve(row - 1, 8);
		else
			solve(row, col - 1);
	}

	// check each element of the row for num, if num is found return false
	private boolean checkRow(int row, int num) {

		for (int i = 0; i < 9; i++) {
			if (puzzle[row][i] == num) {
				return false;
			}
		}

		return true;
	}

	// check each element of the column for num, if num is found return false
	private boolean checkCol(int col, int num) {

		for (int i = 0; i < 9; i++) {
			if (puzzle[i][col] == num) {
				return false;
			}
		}

		return true;
	}

	// check each element of the section for num, if num is found return false
	private boolean checkSection(int xPos, int yPos, int num) {

		int[][] section;
		section = getSection(xPos, yPos);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (section[i][j] == num)
					return false;
			}
		}
		return true;

	}

	// return the 3x3 section the given coordinates are in
	private int[][] getSection(int xPos, int yPos) {

		int[][] section = new int[3][3];
		int xIndex = 3 * (xPos / 3);
		int yIndex = 3 * (yPos / 3);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				section[i][j] = puzzle[xIndex + i][yIndex + j];
			}
		}

		return section;

	}

	// searches puzzle for empty squares
	// 0 denotes empty
	private boolean emptyCheck() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (puzzle[i][j] == 0) {
					return true;
				}
			}
		}
		return false;
	}
}