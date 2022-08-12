package sudoku;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class RoundButtonUI extends BasicButtonUI {
    private final Color color;

    RoundButtonUI(Color c) {
        super();
        this.color = c;
    }

    @Override
    public void update(Graphics g, JComponent c) {
        if (c.isOpaque()) {
            g.setColor(this.color);
            g.fillRoundRect(0, 0, c.getWidth(),c.getHeight(), 50, 50);
        }
        paint(g, c);
    }
}
