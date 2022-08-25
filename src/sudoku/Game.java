package sudoku;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

/**
 * This class generates a game with {@link Generator} into a {@link Grid} and also makes a {@link ButtonInitializer}
 * to make the game visible. Also stores the {@link #time} when the game started.
 */
public class Game {
	private final ButtonInitializer buttonInitializer;
	public static long time;

	public static void main(String[] args) {
		Color menu = new Color(29, 37, 40);

		AppFrame.hardness = 2;

		JDialog newGameDialog = new JDialog();
		newGameDialog.setTitle("Start new game");
		newGameDialog.getContentPane().setBackground(menu);
		newGameDialog.setMinimumSize(new Dimension(400, 300));
		newGameDialog.setPreferredSize(new Dimension(400, 300));
		newGameDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		newGameDialog.setLocationRelativeTo(null);

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
			AppFrame appFrame = new AppFrame(game);
			appFrame.setVisible(true);
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
	}

	/**
	 * Constructs a new game instance with {@link Grid} and a {@link ButtonInitializer} by the given hardness.
	 */
	public Game() {
		int hardness = AppFrame.hardness;
		Grid grid = Grid.emptyGrid();

		while(!grid.isFullGrid()) {
			Generator generator = new Generator();
			grid = generator.generate(0);
		}

		Grid activeGrid = new Grid(grid);
		Generator.eraseCells(activeGrid, hardness * 6 + 6);

		this.buttonInitializer = new ButtonInitializer(activeGrid, grid);
		this.buttonInitializer.setBorder(new LineBorder(Color.GRAY, 2, true));

		Game.time = System.currentTimeMillis();
	}

	/**
	 * Container getter method.
	 * @return the {@link Game}'s configured {@link #buttonInitializer}
	 */
	public ButtonInitializer getButtonInitializer() {
		return this.buttonInitializer;
	}
}
