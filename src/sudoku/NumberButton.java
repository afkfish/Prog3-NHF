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
	private boolean pressState;
	private int number;
	private Color color;

	/**
	 * Constructor for a {@link NumberButton}.
	 * @param row the row position of the button
	 * @param col the column position of the button
	 */
	public NumberButton(int row, int col, int number, boolean isOriginal) {
		super();

		this.row = row;
		this.col = col;
		this.number = number;

		this.setFocusable(false);
		this.pressState = false;
		this.color = new Color(46, 58, 63);

		if (!isOriginal) {
			this.setText(this.number == 0 ? null : String.valueOf(this.number));
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

		//this.addKeyListener(new ChangeListener());

		this.setPreferredSize(new Dimension(25, 25));
		this.setBackground(new Color(29, 37, 40));
		this.setAlignmentY(0.5F);
		this.setOpaque(true);
		this.setFont(new Font("arial", Font.PLAIN, 20));
		this.setForeground(new Color(255, 255, 255));

		this.addActionListener(actionEvent -> this.changePressState());
	}

	/**
	 * @return the row where the button is located
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column where the button is located
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @return the number of the button
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set the button to
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return a boolean representing the button's {@link #pressState}
	 */
	public boolean getPressState() {
		return this.pressState;
	}

	/**
	 * Change the {@link #color} and the {@link #pressState} of the button.
	 */
	public void changePressState() {
		this.pressState = !this.pressState;
		if(this.pressState) {
			this.color = new Color(139, 239, 99, 166);
		} else {
			this.color = new Color(46, 58, 63);
		}
		this.setUI(new RoundButtonUI(this.color));
	}
}
