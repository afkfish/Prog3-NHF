package sudoku;

import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;

/**
 * This class represents a single {@link JButton} with a number on it which is determined by the {@link Grid.Cell}s
 * value that is in the same {@link #row} and {@link #col}umn as the button.
 */
public class NumberButton extends JButton {
	private final int row;
	private final int col;
	private final Grid active;
	private final Grid solved;
	private boolean pressState;
	private int number;
	private Color color;

	/**
	 * Constructor for a {@link NumberButton}.
	 * @param row the row position of the button
	 * @param col the column position of the button
	 * @param active the {@link Game}'s active {@link Grid} which the player can modify
	 * @param solved the solution {@link Grid} that will be compared
	 */
	public NumberButton(int row, int col, Grid active, Grid solved) {
		super();

		this.row = row;
		this.col = col;
		this.active = active;
		this.solved = solved;
		this.number = active.getCell(row, col).getValue();

		this.pressState = false;
		this.color = new Color(46, 58, 63);

		if (this.number == 0) {
			this.setText(null);
			this.setUI(new RoundButtonUI(this.color));
		} else {
			this.setText(String.valueOf(this.number));
			this.setEnabled(false);
			this.setUI(new MetalButtonUI() {
				@Override
				protected Color getDisabledTextColor() {
					return Color.WHITE;
				}
			});
		}

		this.addKeyListener(new ChangeListener());

		this.setPreferredSize(new Dimension(25, 25));
		this.setBackground(new Color(29, 37, 40));
		this.setAlignmentY(0.5F);
		this.setOpaque(true);
		this.setFont(new Font("arial", Font.PLAIN, 20));
		this.setForeground(new Color(255, 255, 255));

		this.addActionListener(actionEvent -> this.changePressState());
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Grid getActiveGrid() {
		return active;
	}

	public Grid getSolvedGrid() {
		return solved;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean getPressState() {
		return this.pressState;
	}

	public void changePressState() {
		this.pressState = !this.pressState;
		if(this.pressState) {
			this.color = new Color(139, 239, 99, 166);
		} else {
			this.color = new Color(39, 49, 54);
		}
		this.setUI(new RoundButtonUI(this.color));
	}
}
