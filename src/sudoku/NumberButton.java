package sudoku;

import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;

public class NumberButton extends JButton {
	private final int row;
	private final int col;
	private final Grid active;
	private final Grid solved;
	private boolean pressState;
	private int number;
	private Color color;

	public NumberButton(int row, int col, Grid active, Grid solved) {
		super();

		this.row = row;
		this.col = col;
		this.active = active;
		this.solved = solved;
		this.number = active.getCell(row, col).getValue();

		this.pressState = false;
		this.color = new Color(86, 86, 86);

		if (this.number == 0)
			this.setText(null);
		else
			this.setText(String.valueOf(this.number));

		this.addKeyListener(new ChangeListener());

		this.setPreferredSize(new Dimension(25, 25));
		this.setBackground(new Color(24, 25, 27));
		this.setAlignmentY(0.5F);
		this.setOpaque(true);
		this.setFont(new Font("arial", Font.PLAIN, 20));
		this.setForeground(new Color(255, 255, 255));

		if (this.number != 0) {
			this.setEnabled(false);
			this.setUI(new MetalButtonUI() {
				@Override
				protected Color getDisabledTextColor() {
					return Color.WHITE;
				}
			});
		} else {
			this.setUI(new RoundButtonUI(this.color));
		}

		this.addActionListener(actionEvent -> {
			this.changePressState();
		});
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		this.setUI(new RoundButtonUI(this.color));
	}

	public boolean getPressState() {
		return this.pressState;
	}

	public void changePressState() {
		this.pressState = !this.pressState;
		if(this.pressState) {
			this.color = new Color(139, 239, 99, 166);
		} else {
			this.color = new Color(86, 86, 86);
		}
		this.setUI(new RoundButtonUI(this.color));
	}
}
