package sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class initializes the {@link NumberButton}s in a 9x9 pattern to a {@link JPanel}.
 */
public class ButtonInitializer extends JPanel {
	/**
	 * Array to get buttons easier.
	 */
	private final ArrayList<NumberButton> buttons = new ArrayList<>();

	/**
	 * Constructor for initialization of the 9x9 {@link NumberButton} grid.
	 * @param active the active {@link Grid} where the player changes the {@link Grid.Cell}s
	 */
	public ButtonInitializer(Grid active) {
		super(new GridLayout(9,9));
		Color separator = new Color(48, 58, 62);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				NumberButton numberButton = new NumberButton(i, j, active.getCell(i, j).getValue(), active.getCell(i, j).isOriginal);
				if (j == 0) {
					if (i == 0) {
						numberButton.setBorder(BorderFactory.createMatteBorder(0,0,1,1, separator));
					} else if (i == 2 || i == 5) {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,0,3,1, separator));
					} else if (i == 3 || i == 6) {
						numberButton.setBorder(BorderFactory.createMatteBorder(3,0,1,1, separator));
					} else if (i < 8) {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,0,1,1, separator));
					} else {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,0,0,1, separator));
					}
				} else if (j == 2 || j == 5) {
					if (i == 0) {
						numberButton.setBorder(BorderFactory.createMatteBorder(0,1,1,3, separator));
					} else if (i == 2 || i == 5) {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,1,3,3, separator));
					} else if (i == 3 || i == 6) {
						numberButton.setBorder(BorderFactory.createMatteBorder(3,1,1,3, separator));
					} else if (i < 8) {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,1,1,3, separator));
					} else {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,1,0,3, separator));
					}
				} else if (j == 3 || j == 6) {
					if (i == 0) {
						numberButton.setBorder(BorderFactory.createMatteBorder(0,3,1,1, separator));
					} else if (i == 2 || i == 5) {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,3,3,1, separator));
					} else if (i == 3 || i == 6) {
						numberButton.setBorder(BorderFactory.createMatteBorder(3,3,1,1, separator));
					} else if (i < 8) {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,3,1,1, separator));
					} else {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,3,0,1, separator));
					}
				} else if (j < 8) {
					if (i == 0) {
						numberButton.setBorder(BorderFactory.createMatteBorder(0,1,1,1, separator));
					} else if (i == 2 || i == 5) {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,1,3,1, separator));
					} else if (i == 3 || i == 6) {
						numberButton.setBorder(BorderFactory.createMatteBorder(3,1,1,1, separator));
					} else if (i < 8) {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,1,1,1, separator));
					} else {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,1,0,1, separator));
					}
				} else {
					if (i == 0) {
						numberButton.setBorder(BorderFactory.createMatteBorder(0,1,1,0, separator));
					} else if (i == 2 || i == 5) {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,1,3,0, separator));
					} else if (i == 3 || i == 6) {
						numberButton.setBorder(BorderFactory.createMatteBorder(3,1,1,0, separator));
					} else if (i < 8) {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,1,1,0, separator));
					} else {
						numberButton.setBorder(BorderFactory.createMatteBorder(1,1,0,0, separator));
					}
				}
				this.add(numberButton);
				this.buttons.add(numberButton);
			}
		}
	}

	public ArrayList<NumberButton> getButtons() {
		return buttons;
	}
}
