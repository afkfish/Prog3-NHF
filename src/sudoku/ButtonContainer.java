package sudoku;

import javax.swing.*;
import java.awt.*;

public class ButtonContainer {

    private NumberButton[][] buttons = new NumberButton[9][9];
    ButtonContainer(JPanel panel, int  num) {
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                NumberButton b = new NumberButton("0");
                buttons[i][j] = b;
                if (j == 0) {
                    if (i == 0) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(0,0,1,1, Color.WHITE));
                    } else if (i == 2 || i == 5) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,0,5,1, Color.WHITE));
                    } else if (i < num-1){
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,0,1,1, Color.WHITE));
                    } else {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,0,0,1, Color.WHITE));
                    }
                } else if (j < num-1) {
                    if (i == 0) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(0,1,1,1, Color.WHITE));
                    } else if (i == 2 || i == 5) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,5,1, Color.WHITE));
                    } else if (i < num-1) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.WHITE));
                    } else {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,0,1, Color.WHITE));
                    }
                } else {
                    if (i == 0) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(0,1,1,0, Color.WHITE));
                    } else if (i == 2 || i == 5) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,5,0, Color.WHITE));
                    } else if (i < num-1) {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,0, Color.WHITE));
                    } else {
                        buttons[i][j].setBorder(BorderFactory.createMatteBorder(1,1,0,0, Color.WHITE));
                    }
                }
                panel.add(buttons[i][j]);
            }
        }
    }
}
