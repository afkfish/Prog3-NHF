package com.afkfish.sudoku;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

/**
 * This class is an override of a {@link BasicButtonUI} to make a circle button with a custom {@link #color}.
 */
public class RoundButtonUI extends BasicButtonUI {
	private final Color color;

	public RoundButtonUI(Color c) {
		super();
		this.color = c;
	}

	@Override
	public void update(Graphics g, JComponent c) {
		super.update(g, c);
		if (c.isOpaque()) {
			g.setColor(this.color);
			g.fillOval(0, 0, c.getWidth(), c.getHeight());
		}
		paint(g, c);
	}
}
