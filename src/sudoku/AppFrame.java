package sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * This is the {@link JFrame} initializer class. It holds the data for the game's {@link #hardness}.
 */
public class AppFrame extends JFrame {
	/**
	 * The window's dimensions.
	 */
	private Dimension windowSize;
	/**
	 * The {@link JPanel} containing the button grid.
	 */
	private JPanel mainPanel;
	/**
	 * The stored hardness of the current {@link Game}. The difficulty level can be: 0 -> 10
	 */
	public static int hardness = 0;

	/**
	 * Constructor for the {@link JFrame} with the {@link Game} inside.
	 * @param game the generated game
	 */
	public AppFrame(Game game) {
		super("Sudoku v0.9");

		Color bgColor = new Color(29, 37, 40);

		this.initFrame();
		this.initMenuBar();

		this.mainPanel = game.getButtonInitializer();

		JPanel border0 = new JPanel();
		border0.setPreferredSize(new Dimension((this.windowSize.width-600)/2, this.windowSize.height));
		JPanel border1 = new JPanel();
		border1.setPreferredSize(new Dimension((this.windowSize.width-600)/2, this.windowSize.height));
		JPanel border2 = new JPanel();
		border2.setPreferredSize(new Dimension(this.windowSize.width, (this.windowSize.height-600)/2));
		JPanel border3 = new JPanel();
		border3.setPreferredSize(new Dimension(this.windowSize.width, (this.windowSize.height-600)/2));
		border0.setBackground(bgColor);
		border1.setBackground(bgColor);
		border2.setBackground(bgColor);
		border3.setBackground(bgColor);

		mainPanel.setBackground(bgColor);
		mainPanel.setPreferredSize(new Dimension(600,600));

		this.add(border0, BorderLayout.WEST);
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(border1, BorderLayout.EAST);
		this.add(border2, BorderLayout.SOUTH);
		this.add(border3, BorderLayout.NORTH);

        this.pack();
    }

	/**
	 * The main frame initializer.
	 */
	private void initFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.windowSize = new Dimension(1280, 720);
		this.setMinimumSize(windowSize);
		this.setPreferredSize(windowSize);
		this.setLocationRelativeTo(null);
	}

	/**
	 * Menubar initializer. <br><br/> NOTE: When using the app on macOS the menubar is moved to the top
	 * menubar.
	 */
	private void initMenuBar() {
		if(Objects.equals(System.getProperty("os.name"), "Mac OS X")) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}

		Color menu = new Color(29, 37, 40);
		Color menuItem = new Color(39, 49, 54);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(menu);
		menuBar.setForeground(Color.WHITE);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(48, 58, 62), 1));

		JMenu file = new JMenu("File");
		file.setBackground(menu);
		file.setForeground(Color.WHITE);

		JMenuItem newGame = new JMenuItem("New game");
		newGame.addActionListener(actionEvent -> {
			GameDialog newGameDialog = new GameDialog(this);
			newGameDialog.setVisible(true);

		});
		newGame.setBackground(menuItem);
		newGame.setForeground(Color.WHITE);

		JMenuItem saveGame = new JMenuItem("Save game");
		saveGame.setBackground(menuItem);
		saveGame.setForeground(Color.WHITE);

		JMenuItem loadGame = new JMenuItem("Load game");
		loadGame.setBackground(menuItem);
		loadGame.setForeground(Color.WHITE);

		JMenuItem records = new JMenuItem("Records");
		records.addActionListener(actionEvent -> {
			RecordsDialog recordsDialog = new RecordsDialog(this);
			recordsDialog.setVisible(true);
		});
		records.setBackground(menuItem);
		records.setForeground(Color.WHITE);

		JMenuItem exit = new JMenuItem("Exit");
		exit.setBackground(menuItem);
		exit.setForeground(Color.WHITE);
		exit.addActionListener(actionEvent -> System.exit(0));

		file.add(newGame);
		file.add(saveGame);
		file.add(loadGame);
		file.add(records);
		file.addSeparator();
		file.add(exit);

		menuBar.add(file);

		this.setJMenuBar(menuBar);
	}

	/**
	 * Set the hardness of the upcoming {@link Game}.
	 * @param hardness difficulty level from 0 -> 10
	 */
	public static void setHardness(int hardness) {
		AppFrame.hardness = Math.abs(hardness % 11);
	}

	/**
	 * Set a new {@link Game} to display when the player started a new.
	 * @param game the generated new {@link Game}
	 */
	public void setGame(Game game) {
		Container contain = getContentPane();
		contain.remove(this.mainPanel);
		this.mainPanel = game.getButtonInitializer();
		contain.add(mainPanel, BorderLayout.CENTER);
		validate();
		setVisible(true);
	}
}
