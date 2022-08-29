package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A listener class for {@link NumberButton} to detect when a button is active and get the user input. If the input is a
 * numerical value between 1 and 9 than change the {@link Grid.Cell} to it.
 */
public class ChangeListener implements KeyListener {
	@Override
	public void keyTyped(KeyEvent e) {
		int n = Character.getNumericValue(e.getKeyChar());
		NumberButton eventParent = (NumberButton)e.getSource();

		if(n > 0 && n < 10 && eventParent.getPressState()) {
			eventParent.setNumber(n);
			eventParent.setText(String.valueOf(eventParent.getNumber()));
			eventParent.getActiveGrid().getCell(eventParent.getRow(), eventParent.getCol()).setValue(n);

		} else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			eventParent.setNumber(0);
			eventParent.setText("");
			eventParent.getActiveGrid().getCell(eventParent.getRow(), eventParent.getCol()).setValue(0);
		}
		eventParent.changePressState();

		if (Grid.compare(eventParent.getActiveGrid(), eventParent.getSolvedGrid())) {
			JDialog dialog = new JDialog(AppFrame.getFrames()[0], "You won!", true);
			dialog.setLocationRelativeTo(AppFrame.getFrames()[0]);
			dialog.setMinimumSize(new Dimension(300, 100));
			dialog.getContentPane().setBackground(new Color(29, 37, 40));
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

			JLabel label = new JLabel("It took " + ((int) (System.currentTimeMillis() - Game.time) / 1000) + " seconds to solve.");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setForeground(Color.WHITE);

			dialog.add(label);
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
