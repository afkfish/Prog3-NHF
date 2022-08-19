package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NumberButton extends JButton implements KeyListener {
    private boolean pressState;
    private int number;
    private Color color;

    public NumberButton(String title) {
        super(title);

        this.pressState = false;
        this.number = 0;
        this.color = new Color(86, 86, 86);

        this.addKeyListener(this);

        this.setPreferredSize(new Dimension(25, 25));
        this.setBackground(new Color(24, 25, 27));
        this.setAlignmentY(0.5F);
        this.setOpaque(true);
        this.setForeground(new Color(255, 255, 255));

        this.setUI(new RoundButtonUI(this.color));

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
        this.setUI(new RoundButtonUI(this.color));
    }

    public void changePressState(){
        this.pressState = !this.pressState;
        if(this.pressState) {
            this.color = new Color(139, 239, 99, 166);
        } else {
            this.color = new Color(86, 86, 86);
        }
        this.setUI(new RoundButtonUI(this.color));
    }

    @Override
    public void keyTyped(KeyEvent event) {
        int n = Character.getNumericValue(event.getKeyChar());
        if(n > 0 && n < 10 && this.pressState) {
            this.number = n;
            this.setText(String.valueOf(this.number));
        }
        this.changePressState();
    }

    @Override
    public void keyPressed(KeyEvent event) {

    }

    @Override
    public void keyReleased(KeyEvent event) {

    }
}
