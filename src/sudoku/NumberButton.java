package sudoku;

import javax.swing.*;
import java.awt.*;

public class NumberButton extends JButton {
    private boolean pressState;
    private int number;
    private Color color;

    public NumberButton(String title) {
        super(title);

        this.pressState = false;
        this.number = 0;
        this.color = new Color(86, 86, 86);

        this.setPreferredSize(new Dimension(100, 100));
        this.setBackground(new Color(24, 25, 27));
        this.setAlignmentY(0.5F);
        this.setOpaque(true);

        this.addActionListener(actionEvent -> this.changePressState());
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
    }

    public void changePressState(){
        this.pressState = !this.pressState;
        if(this.pressState) {
            this.color = new Color(139, 239, 99, 166);
        } else {
            this.color = new Color(122, 154, 116);
        }
        this.number += 1;
        this.setText(String.valueOf(this.number));
        this.setUI(new RoundButtonUI(this.color));
    }
}
