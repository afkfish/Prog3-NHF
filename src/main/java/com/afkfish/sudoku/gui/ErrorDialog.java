package com.afkfish.sudoku.gui;

import javax.swing.*;
import java.awt.*;

public class ErrorDialog extends JDialog {
	public ErrorDialog(JFrame owner, String title, boolean modal, String message) {
		super(owner, title, modal);

		JPanel panel = new JPanel(new GridLayout(2, 1));
		this.add(panel);

		JPanel text = new JPanel();
		JPanel button = new JPanel();
		panel.add(text);
		panel.add(button);

		JLabel label1 = new JLabel(message);
		label1.setPreferredSize(new Dimension(300, 30));
		label1.setHorizontalTextPosition(SwingConstants.CENTER);
		label1.setHorizontalAlignment(SwingConstants.CENTER);

		JButton okButton = new JButton("Dismiss");
		okButton.addActionListener(actionEvent1 -> this.dispose());

		button.add(okButton);
		text.add(label1);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
