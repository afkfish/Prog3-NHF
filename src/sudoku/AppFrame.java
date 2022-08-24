package sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AppFrame extends JFrame {
	private Dimension windowSize;
	private final JPanel mainPanel;

	public AppFrame(Game game) {
		super("Sudoku v0.8");

		Color bgcolor = new Color(24, 25, 27);

		this.initFrame();
		this.initMenuBar();

		this.mainPanel = game.getContainer();

		JPanel border0 = new JPanel();
		border0.setPreferredSize(new Dimension((this.windowSize.width-600)/2, this.windowSize.height));
		JPanel border1 = new JPanel();
		border1.setPreferredSize(new Dimension((this.windowSize.width-600)/2, this.windowSize.height));
		JPanel border2 = new JPanel();
		border2.setPreferredSize(new Dimension(this.windowSize.width, (this.windowSize.height-600)/2));
		JPanel border3 = new JPanel();
		border3.setPreferredSize(new Dimension(this.windowSize.width, (this.windowSize.height-600)/2));
		border0.setBackground(bgcolor);
		border1.setBackground(bgcolor);
		border2.setBackground(bgcolor);
		border3.setBackground(bgcolor);

		mainPanel.setBackground(bgcolor);
		mainPanel.setPreferredSize(new Dimension(600,600));

		this.add(border0, BorderLayout.WEST);
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(border1, BorderLayout.EAST);
		this.add(border2, BorderLayout.SOUTH);
		this.add(border3, BorderLayout.NORTH);

        this.pack();
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
