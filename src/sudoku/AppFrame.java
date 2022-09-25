package sudoku;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

/**
 * This is the {@link JFrame} initializer class. It holds the data for the game's {@link #hardness}.
 */
public class AppFrame extends JFrame {
	/**
	 * The {@link JPanel} containing the button grid.
	 */
	private JPanel mainPanel;
	/**
	 * The static {@link Game} which is currently displayed.
	 */
	private static Game game;
	/**
	 * The stored hardness of the current {@link Game}. The difficulty level can be: 0 -> 10
	 */
	public static int hardness = 0;

	/**
	 * Constructor for the {@link JFrame} with the {@link Game} inside.
	 */
	public AppFrame(Game game) {
		super("Sudoku v0.10");

		ImageIcon imageIcon = new ImageIcon("assets/icon/sudoku_128x128.png");
		this.setIconImage(imageIcon.getImage());

		AppFrame.game = game;
		this.mainPanel = game.getButtonInitializer();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				AppFrame.game.saveGame();
				RecordsDialog.saveRecords();
			}
		});

		this.addKeyListener(new ChangeListener(game.getActive(), game.getSolved(), game.getButtonInitializer()));
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension windowSize = new Dimension(1280, 720);
		this.setPreferredSize(windowSize);

		Color bgColor = new Color(29, 37, 40);

		this.initMenuBar();

		JPanel border0 = new JPanel();
		border0.setPreferredSize(new Dimension((windowSize.width-600)/2, windowSize.height));
		JPanel border1 = new JPanel();
		border1.setPreferredSize(new Dimension((windowSize.width-600)/2, windowSize.height));
		JPanel border2 = new JPanel();
		border2.setPreferredSize(new Dimension(windowSize.width, (windowSize.height-600)/2));
		JPanel border3 = new JPanel();
		border3.setPreferredSize(new Dimension(windowSize.width, (windowSize.height-600)/2));
		border0.setBackground(bgColor);
		border1.setBackground(bgColor);
		border2.setBackground(bgColor);
		border3.setBackground(bgColor);

		this.add(border0, BorderLayout.WEST);
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(border1, BorderLayout.EAST);
		this.add(border2, BorderLayout.SOUTH);
		this.add(border3, BorderLayout.NORTH);

		this.pack();
		this.setLocationRelativeTo(null);
	}

	/**
	 * Menubar initializer. <br><br/> Note: When using the app on macOS the menubar is moved to the top
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
		saveGame.addActionListener(actionEvent -> game.saveGame());

		JMenuItem loadGame = new JMenuItem("Load game");
		loadGame.setBackground(menuItem);
		loadGame.setForeground(Color.WHITE);
		loadGame.addActionListener(actionEvent -> {
			FileDialog fileDialog = new FileDialog(this, "Choose game save", FileDialog.LOAD);
			fileDialog.setDirectory(System.getProperty("user.dir"));
			fileDialog.setFile("*.DAT;*.dat");
			fileDialog.setLocationRelativeTo(null);
			fileDialog.setVisible(true);
			Game game;
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileDialog.getDirectory() + fileDialog.getFile()))) {
				Grid active = (Grid) ois.readObject();
				Grid solved = (Grid) ois.readObject();

				if (!Grid.compare(active, solved)) {
					Game.time = ois.readLong();
					AppFrame.hardness = ois.readInt();

					game = new Game(active, solved);
					this.setGame(game);
					this.setVisible(true);
				} else {
					JDialog dialog = new JDialog(this,"Error!",true);
					JLabel label = new JLabel("Error! This game is already solved!");
					label.setPreferredSize(new Dimension(300, 30));
					label.setHorizontalTextPosition(SwingConstants.CENTER);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					dialog.add(label);
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				}
			} catch (IOException | ClassNotFoundException e) {
				JDialog error = new JDialog(this, "Error!", true);
				JPanel panel = new JPanel(new GridLayout(2, 1));
				error.add(panel);
				JPanel text = new JPanel();
				JPanel button = new JPanel();
				panel.add(text);
				panel.add(button);
				JLabel label = new JLabel("Error in loading game! \nMaybe bad save.");
				label.setPreferredSize(new Dimension(300, 30));
				label.setHorizontalTextPosition(SwingConstants.CENTER);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				JButton okButton = new JButton("Dismiss");
				okButton.setUI(new BasicButtonUI() {
					@Override
					public void update(Graphics g, JComponent c) {
						super.update(g, c);
						if (c.isOpaque()) {
							g.setColor(new Color(46, 58, 63));
							g.fillRect(0, 0, c.getWidth(),c.getHeight());
						}
						paint(g, c);
					}
				});
				okButton.addActionListener(actionEvent1 -> error.dispose());
				button.add(okButton);
				text.add(label);
				error.pack();
				error.setLocationRelativeTo(null);
				error.setVisible(true);
			}
		});

		JMenuItem exit = new JMenuItem("Exit");
		exit.setBackground(menuItem);
		exit.setForeground(Color.WHITE);
		exit.addActionListener(actionEvent -> System.exit(0));

		file.add(newGame);
		file.add(saveGame);
		file.add(loadGame);
		file.addSeparator();
		file.add(exit);

		JMenu records = new JMenu("Records");
		records.setBackground(menuItem);
		records.setForeground(Color.WHITE);

		JMenuItem viewRecords = new JMenuItem("View");
		viewRecords.addActionListener(actionEvent -> {
			RecordsDialog recordsDialog = new RecordsDialog(this);
			recordsDialog.setVisible(true);
		});
		viewRecords.setBackground(menuItem);
		viewRecords.setForeground(Color.WHITE);

		JMenuItem importRecords = new JMenuItem("Import");
		importRecords.addActionListener(actionEvent -> {
			FileDialog fileDialog = new FileDialog(this, "Choose record save", FileDialog.LOAD);
			fileDialog.setDirectory(System.getProperty("user.dir"));
			fileDialog.setFile("*.DAT;*.dat");
			fileDialog.setLocationRelativeTo(null);
			fileDialog.setVisible(true);
			try {
				RecordsDialog.loadRecords(fileDialog.getDirectory() + fileDialog.getFile());
			} catch (IOException | ClassNotFoundException e) {
				System.err.println("Error in importing records!");
			}
		});

		importRecords.setBackground(menuItem);
		importRecords.setForeground(Color.WHITE);

		records.add(viewRecords);
		records.add(importRecords);

		menuBar.add(file);
		menuBar.add(records);

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
		AppFrame.game = game;

		contain.remove(this.mainPanel);
		this.mainPanel = game.getButtonInitializer();
		contain.add(this.mainPanel, BorderLayout.CENTER);

		this.setContentPane(contain);
		for (KeyListener k : this.getKeyListeners()) {
			this.removeKeyListener(k);
		}
		this.addKeyListener(new ChangeListener(game.getActive(), game.getSolved(), game.getButtonInitializer()));
	}
}
