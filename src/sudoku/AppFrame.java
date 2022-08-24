package sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AppFrame extends JFrame {
	private Dimension windowSize;
	private JPanel mainPanel;

	public static int hardness = 0;

	public AppFrame(Game game) {
		super("Sudoku v0.8");

		Color bgColor = new Color(24, 25, 27);

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

		JMenuItem newGame = new JMenuItem("New game");
		newGame.addActionListener(actionEvent -> {
			Game game = new Game();
			setGame(game);
		});
		JMenuItem saveGame = new JMenuItem("Save game");
		JMenuItem loadGame = new JMenuItem("Load game");
		JMenuItem exportRecords = new JMenuItem("Export records");
		JMenuItem preferences = new JMenuItem("Preferences");
		preferences.addActionListener(actionEvent -> {
			JDialog settings = new JDialog(this, "Preferences", true);
			settings.getContentPane().setBackground(new Color(24, 25, 27));
			settings.setMinimumSize(new Dimension(300, 100));
			settings.setLocationRelativeTo(this);
			JLabel hardnessLabel = new JLabel("Game hardness level:  " + AppFrame.hardness);
			hardnessLabel.setHorizontalAlignment(0);
			hardnessLabel.setFont(new Font("font1", Font.PLAIN, 20));
			hardnessLabel.setForeground(Color.WHITE);
			JSlider slider = new JSlider(0, 10);
			slider.setMaximumSize(new Dimension(200, 100));
			slider.setValue(AppFrame.hardness);
			slider.setSnapToTicks(true);
			slider.addChangeListener(changeEvent -> {
				AppFrame.setHardness(slider.getValue());
				hardnessLabel.setText("Game hardness level:  " + AppFrame.hardness);
			});
			settings.add(hardnessLabel, BorderLayout.NORTH);
			settings.add(slider);
			settings.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			settings.validate();
			settings.setVisible(true);
		});
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(actionEvent -> System.exit(0));

		file.add(newGame);
		file.add(saveGame);
		file.add(loadGame);
		file.add(exportRecords);
		file.addSeparator();
		file.add(preferences);
		file.addSeparator();
		file.add(exit);

		menuBar.add(file);

		this.setJMenuBar(menuBar);
	}

	public static void setHardness(int hardness) {
		AppFrame.hardness = Math.abs(hardness % 11);
	}

	public void setGame(Game game) {
		Container contain = getContentPane();
		contain.remove(this.mainPanel);
		this.mainPanel = game.getContainer();
		contain.add(mainPanel, BorderLayout.CENTER);
		validate();
		setVisible(true);
	}
}
