package sudoku;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.util.Objects;

public class AppFrame extends JFrame {
	private Dimension windowSize;
	private JPanel mainPanel;
	public static int hardness = 0;

	public AppFrame(Game game) {
		super("Sudoku v0.8");

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
			JDialog newGameDialog = new JDialog(this, "New Game", true);
			newGameDialog.getContentPane().setBackground(menu);
			newGameDialog.setMinimumSize(new Dimension(400, 300));
			newGameDialog.setPreferredSize(new Dimension(400, 300));
			newGameDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			newGameDialog.setLocationRelativeTo(this);

			JLabel hardnessLabel = new JLabel("Game hardness level:  " + AppFrame.hardness);
			hardnessLabel.setHorizontalAlignment(0);
			hardnessLabel.setFont(new Font("font1", Font.PLAIN, 20));
			hardnessLabel.setForeground(Color.WHITE);

			JSlider slider = new JSlider(0, 10, AppFrame.hardness);
			slider.setPreferredSize(new Dimension(200, 50));
			slider.setMaximumSize(new Dimension(200, 50));
			slider.setBackground(menu);
			slider.setUI(new BasicSliderUI());
			slider.setSnapToTicks(true);
			slider.setBorder(null);
			slider.addChangeListener(changeEvent -> {
				AppFrame.setHardness(slider.getValue());
				hardnessLabel.setText("Game hardness level:  " + AppFrame.hardness);
			});

			JButton submit = new JButton("Start");
			submit.addActionListener(submitActionEvent -> {
				newGameDialog.dispose();
				Game game = new Game();
				setGame(game);
			});
			submit.setMaximumSize(new Dimension(100, 20));
			submit.setUI(new BasicButtonUI() {
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
			submit.setBackground(menu);
			submit.setForeground(Color.WHITE);

			newGameDialog.add(hardnessLabel, BorderLayout.NORTH);
			newGameDialog.add(slider, BorderLayout.CENTER);
			newGameDialog.add(submit, BorderLayout.SOUTH);

			newGameDialog.validate();
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

		JMenuItem exportRecords = new JMenuItem("Export records");
		exportRecords.setBackground(menuItem);
		exportRecords.setForeground(Color.WHITE);

		JMenuItem exit = new JMenuItem("Exit");
		exit.setBackground(menuItem);
		exit.setForeground(Color.WHITE);
		exit.addActionListener(actionEvent -> System.exit(0));

		file.add(newGame);
		file.add(saveGame);
		file.add(loadGame);
		file.add(exportRecords);
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
		this.mainPanel = game.getButtonInitializer();
		contain.add(mainPanel, BorderLayout.CENTER);
		validate();
		setVisible(true);
	}
}
