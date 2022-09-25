package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class initializes the {@link NumberButton}s in a 9x9 pattern to a {@link JPanel}.
 */
public class ButtonInitializer extends JPanel  implements ActionListener {
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
				int top, left, bottom, right;
				if (j == 0) {
					right = 1;
					left = 0;
				} else if (j == 2 || j == 5) {
					right = 3;
					left = 1;
				} else if (j == 3 || j == 6) {
					right = 1;
					left = 3;
				} else if (j < 8) {
					right = 1;
					left = 1;
				} else {
					right = 0;
					left = 1;
				}
				if (i == 0) {
					top = 0;
					bottom = 1;
				} else if (i == 2 || i == 5) {
					top = 1;
					bottom = 3;
				} else if (i == 3 || i == 6) {
					top = 3;
					bottom = 1;
				} else if (i < 8) {
					top = 1;
					bottom = 1;
				} else {
					top = 1;
					bottom = 0;
				}
				numberButton.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, separator));
				numberButton.addActionListener(this);
				this.add(numberButton);
				this.buttons.add(numberButton);
			}
		}
	}

	/**
	 * @return an ArrayList with the added {@link NumberButton}s
	 */
	public ArrayList<NumberButton> getButtons() {
		return buttons;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof NumberButton) {
			((NumberButton) e.getSource()).changePressState();
			for (NumberButton n : this.buttons) {
				if (!NumberButton.equals(n, (NumberButton) e.getSource()) && n.getPressState()) {
					n.changePressState();
				}
			}
		}
	}
}
