package com.afkfish.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * A listener class for {@link NumberButton} to detect when a button is active and get the user input. If the input is a
 * numerical value between 1 and 9 than change the {@link Grid.Cell} to it.
 */
public class ChangeListener implements KeyListener {
	private final Grid active;
	private final Grid solved;
	private final ArrayList<NumberButton> buttons;

	public ChangeListener(Grid active, Grid solved, ButtonInitializer buttonInitializer) {
		this.active = active;
		this.solved = solved;
		this.buttons = buttonInitializer.getButtons();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int n = Character.getNumericValue(e.getKeyChar());

		if(n > 0 && n < 10) {
			for (NumberButton button: buttons) {
				if (button.getPressState()) {
					button.setNumber(n);
					button.setText(String.valueOf(n));
					button.changePressState();
					active.getCell(button.getRow(), button.getCol()).setValue(n);
				}
			}
		} else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			for (NumberButton button: buttons) {
				if (button.getPressState()) {
					button.setNumber(0);
					button.setText("");
					button.changePressState();
					active.getCell(button.getRow(), button.getCol()).setValue(0);
				}
			}
		}

		if (Grid.compare(active, solved)) {
			int time = Game.timeSinceGameStart();
			AppFrame.timer.stop();

			RecordsDialog.addRecord(time, AppFrame.hardness);

			JDialog dialog = new JDialog(AppFrame.getFrames()[0], "You won!", true);
			dialog.setMinimumSize(new Dimension(300, 100));
			dialog.getContentPane().setBackground(new Color(29, 37, 40));
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

			JLabel label = new JLabel("It took " + time + " seconds to solve.");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setForeground(Color.WHITE);

			dialog.add(label);
			dialog.pack();
			dialog.setLocationRelativeTo(AppFrame.getFrames()[0]);
			dialog.setVisible(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
