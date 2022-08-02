package sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AppFrame extends JFrame {
    private Dimension windowSize;

    public AppFrame() {
        super("Sudoku");

        this.initFrame();
        this.initMenuBar();

        this.pack();
    }
    public static void main(String[] args) {
        AppFrame appFrame = new AppFrame();
        appFrame.setVisible(true);
    }

    private void initFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.windowSize = new Dimension(1280, 720);
        this.setMinimumSize(windowSize);
        this.setPreferredSize(windowSize);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.DARK_GRAY);
    }

    private void initMenuBar() {
        if(Objects.equals(System.getProperty("os.name"), "Mac OS X")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");

        JMenuItem saveGame = new JMenuItem("Save game");
        JMenuItem loadGame = new JMenuItem("Load game");
        JMenuItem exportRecords = new JMenuItem("Export records");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(actionEvent -> System.exit(0));

        file.add(saveGame);
        file.add(loadGame);
        file.add(exportRecords);
        file.addSeparator();
        file.add(exit);

        menuBar.add(file);

        this.setJMenuBar(menuBar);
    }
}
