package sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AppFrame extends JFrame {
    private Dimension windowSize;
    private final JPanel mainPanel;

    public AppFrame() {
        super("Sudoku");

        this.initFrame();
        this.initMenuBar();

        JPanel tt = new JPanel();
        JLabel title = new JLabel("Sudoku v0.5");
        tt.add(title);
        tt.setBackground(new Color(24, 25, 27));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(new Color(255,255,255));
        this.add(tt, BorderLayout.NORTH);

        this.mainPanel = new ButtonContainer(9);

        JPanel border0 = new JPanel();
        border0.setPreferredSize(new Dimension((this.getWidth()-300)/3, this.getHeight()));
        JPanel border1 = new JPanel();
        border1.setPreferredSize(new Dimension((this.getWidth()-300)/3, this.getHeight()));
        border0.setBackground(new Color(24, 25, 27));
        border1.setBackground(new Color(24, 25, 27));

        mainPanel.setBackground(new Color(24, 25, 27));
        mainPanel.setPreferredSize(new Dimension(300,300));

        this.add(border0, BorderLayout.WEST);
        this.add(mainPanel);
        this.add(border1, BorderLayout.EAST);

        this.pack();
    }
    public static void main(String[] args) {
        AppFrame appFrame = new AppFrame();
        appFrame.setVisible(true);

        Puzzle puz = new Puzzle();
        int[][] board = puz.generate();

        for (int i = 0; i < 9; i++) {
            System.out.println(' ');
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j]);

            }
        }
    }

    private void initFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.windowSize = new Dimension(1280, 720);
        this.setMinimumSize(windowSize);
        this.setPreferredSize(windowSize);
        this.setLocationRelativeTo(null);
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
