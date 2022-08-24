package sudoku;

import javax.swing.*;
import java.awt.*;

public class ButtonContainer extends JPanel{
	private final NumberButton[][] buttons = new NumberButton[9][9];

    ButtonContainer(Grid active, Grid solved) {
        super(new GridLayout(9,9));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                NumberButton b = new NumberButton(i, j, active, solved);
                buttons[i][j] = b;
                if (j == 0) {
                    if (i == 0) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(0,0,1,1, Color.WHITE));
                    } else if (i == 2 || i == 5) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,0,5,1, Color.WHITE));
                    } else if (i < 8) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,0,1,1, Color.WHITE));
                    } else {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,0,0,1, Color.WHITE));
                    }
                } else if (j == 2 || j == 5) {
                    if (i == 0) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(0,1,1,5, Color.WHITE));
                    } else if (i == 2 || i == 5) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,5,5, Color.WHITE));
                    } else if (i < 8) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,5, Color.WHITE));
                    } else {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,0,5, Color.WHITE));
                    }
                } else if (j < 8) {
                    if (i == 0) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(0,1,1,1, Color.WHITE));
                    } else if (i == 2 || i == 5) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,5,1, Color.WHITE));
                    } else if (i < 8) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.WHITE));
                    } else {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,0,1, Color.WHITE));
                    }
                } else {
                    if (i == 0) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(0,1,1,0, Color.WHITE));
                    } else if (i == 2 || i == 5) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,5,0, Color.WHITE));
                    } else if (i < 8) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,0, Color.WHITE));
                    } else {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,0,0, Color.WHITE));
                    }
                }
                this.add(buttons[i][j]);
            }
        }
    }
}
