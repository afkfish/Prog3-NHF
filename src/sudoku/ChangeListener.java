package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChangeListener implements KeyListener {
	@Override
	public void keyTyped(KeyEvent e) {
		int n = Character.getNumericValue(e.getKeyChar());
		if(n > 0 && n < 10 && ((NumberButton)e.getSource()).getPressState()) {
			((NumberButton)e.getSource()).setNumber(n);
			((NumberButton)e.getSource()).setText(String.valueOf(((NumberButton)e.getSource()).getNumber()));
			((NumberButton)e.getSource()).getActiveGrid().getCell(((NumberButton)e.getSource()).getRow(), ((NumberButton)e.getSource()).getCol()).setValue(n);
		} else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			((NumberButton)e.getSource()).setNumber(0);
			((NumberButton)e.getSource()).setText("");
			((NumberButton)e.getSource()).getActiveGrid().getCell(((NumberButton)e.getSource()).getRow(), ((NumberButton)e.getSource()).getCol()).setValue(0);
		}
		((NumberButton)e.getSource()).changePressState();

		//System.out.println("row " + ((NumberButton)e.getSource()).getRow());
		//System.out.println("col " + ((NumberButton)e.getSource()).getCol());

		//System.out.println(((NumberButton)e.getSource()).getActiveGrid());

		if (Grid.compare(((NumberButton)e.getSource()).getActiveGrid(), ((NumberButton)e.getSource()).getSolvedGrid())) {
			//System.out.println("Solved!!!");
			JDialog dialog = new JDialog(AppFrame.getFrames()[0], "You won!", true);
			dialog.setLocationRelativeTo(null);
			dialog.setMinimumSize(new Dimension(300, 100));
			JLabel label = new JLabel("It took " + ((int) (System.currentTimeMillis() - Game.time) / 1000) + " seconds to solve.");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setForeground(Color.WHITE);
			dialog.getContentPane().setBackground(new Color(24, 25, 27));
			dialog.add(label);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
