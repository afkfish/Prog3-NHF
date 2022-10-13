package com.afkfish.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

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

	public static Timer timer;

	private final int[] count = new int[1];

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

		this.initMenuBar();

		JPanel border0 = new JPanel();
		border0.setPreferredSize(new Dimension((windowSize.width-600)/2, windowSize.height));
		JPanel border1 = new JPanel();
		border1.setPreferredSize(new Dimension((windowSize.width-600)/2, windowSize.height));
		JPanel border2 = new JPanel();
		border2.setPreferredSize(new Dimension(windowSize.width, (windowSize.height-600)/2));
		JPanel border3 = new JPanel();
		border3.setPreferredSize(new Dimension(windowSize.width, (windowSize.height-600)/2));

		JLabel time = new JLabel("...");
		time.setForeground(Color.WHITE);
		time.setHorizontalAlignment(SwingConstants.CENTER);
		time.setMinimumSize(new Dimension(300, 40));
		time.setPreferredSize(new Dimension(300, 40));
		time.setMaximumSize(new Dimension(500, 40));
		time.setFont(new Font("big", Font.PLAIN, 30));

		count[0] = Game.timeSinceGameStart();
		timer = new Timer(1000, e -> {
			++count[0];
			if (count[0] < 100000) {
				String min = String.format("%02d",(count[0] / 60));
				String sec = String.format("%02d", (count[0] % 60));
				time.setText(min + ":" + sec);
			} else {
				((Timer) e.getSource()).stop();
			}
		});
		border2.add(time, BorderLayout.CENTER);
		timer.setInitialDelay(0);

		this.add(border0, BorderLayout.WEST);
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(border1, BorderLayout.EAST);
		this.add(border2, BorderLayout.SOUTH);
		this.add(border3, BorderLayout.NORTH);

		this.pack();
		this.setLocationRelativeTo(null);
		timer.start();
	}

	/**
	 * Menubar initializer.
	 */
	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.WHITE);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(48, 58, 62), 1));

		JMenu gameMenu = new JMenu("Game");
		gameMenu.setForeground(Color.WHITE);

		JMenuItem newGame = new JMenuItem("New game");
		newGame.addActionListener(actionEvent -> {
			timer.stop();
			NewGame newGameFrame = new NewGame(this);
			newGameFrame.setVisible(true);
		});
		newGame.setForeground(Color.WHITE);

		JMenuItem saveGame = new JMenuItem("Save game");
		saveGame.setForeground(Color.WHITE);
		saveGame.addActionListener(actionEvent -> game.saveGame());

		JMenuItem loadGame = new JMenuItem("Load game");
		loadGame.setForeground(Color.WHITE);
		loadGame.addActionListener(actionEvent -> {
			FileDialog fileDialog = new FileDialog(this, "Choose game save", FileDialog.LOAD);
			fileDialog.setDirectory(System.getProperty("user.dir"));
			fileDialog.setFile("*.DAT;*.dat");
			fileDialog.setLocationRelativeTo(null);
			fileDialog.setVisible(true);
			Game game;
			timer.stop();
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileDialog.getDirectory() + fileDialog.getFile()))) {
				Grid active = (Grid) ois.readObject();
				Grid solved = (Grid) ois.readObject();

				if (!Grid.compare(active, solved)) {
					long time = ois.readLong();
					Game.setElapsedTime(time);
					Game.setCurrentStartTime();
					AppFrame.hardness = ois.readInt();

					game = new Game(active, solved);
					this.setGame(game);
					this.setVisible(true);
				} else {
					new ErrorDialog(this, "Error", true, "Error! This game is already solved!");
				}
			} catch (IOException | ClassNotFoundException e) {
				new ErrorDialog(this, "Error", true, "Error in loading game! \nMaybe bad save.");
			}
		});

		JMenuItem exit = new JMenuItem("Exit");
		exit.setForeground(Color.WHITE);
		exit.addActionListener(actionEvent -> {
			AppFrame.game.saveGame();
			RecordsDialog.saveRecords();
			System.exit(0);
		});

		gameMenu.add(newGame);
		gameMenu.add(saveGame);
		gameMenu.add(loadGame);
		gameMenu.addSeparator();
		gameMenu.add(exit);

		JMenu records = new JMenu("Records");
		records.setForeground(Color.WHITE);

		JMenuItem viewRecords = new JMenuItem("View");
		viewRecords.addActionListener(actionEvent -> {
			RecordsDialog recordsDialog = new RecordsDialog(this);
			recordsDialog.setVisible(true);
		});
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

		importRecords.setForeground(Color.WHITE);

		records.add(viewRecords);
		records.add(importRecords);

		JMenu help = new JMenu("Help");
		help.setForeground(Color.WHITE);

		JMenuItem validate = new JMenuItem("Validate");
		validate.addActionListener(actionEvent -> ((ButtonInitializer) mainPanel).validateGrid(game.getSolved()));
		validate.setForeground(Color.WHITE);

		help.add(validate);

		menuBar.add(gameMenu);
		menuBar.add(records);
		menuBar.add(help);

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
		count[0] = Game.timeSinceGameStart();

		contain.remove(this.mainPanel);
		this.mainPanel = game.getButtonInitializer();
		contain.add(this.mainPanel, BorderLayout.CENTER);

		this.setContentPane(contain);
		for (KeyListener k : this.getKeyListeners()) {
			this.removeKeyListener(k);
		}
		this.addKeyListener(new ChangeListener(game.getActive(), game.getSolved(), game.getButtonInitializer()));
		timer.start();
	}
}
