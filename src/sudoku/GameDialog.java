package sudoku;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

/**
 * This class is a {@link JDialog} for the beginning dialog or when starting a new game with the option to change the
 * hardness.
 */
public class GameDialog extends JDialog {

	public GameDialog(AppFrame appFrame) {
		Color menu = new Color(29, 37, 40);

		AppFrame.hardness = 2;

		this.setTitle("Start new game");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(menu);

		JLabel hardnessLabel = new JLabel("Game hardness level:  " + AppFrame.hardness);
		hardnessLabel.setHorizontalAlignment(0);
		hardnessLabel.setPreferredSize(new Dimension(300, 30));
		hardnessLabel.setFont(new Font("font1", Font.PLAIN, 20));
		hardnessLabel.setForeground(Color.WHITE);
		hardnessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JSlider slider = new JSlider(0, 10, AppFrame.hardness);
		slider.setPreferredSize(new Dimension(300, 20));
		slider.setBackground(menu);
		slider.setUI(new BasicSliderUI());
		slider.setSnapToTicks(true);
		slider.setBorder(null);
		slider.addChangeListener(changeEvent -> {
			AppFrame.setHardness(slider.getValue());
			hardnessLabel.setText("Game hardness level:  " + AppFrame.hardness);
		});
		slider.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton submit = new JButton("Start");
		submit.addActionListener(submitActionEvent -> {
			this.dispose();
			Game game = new Game(null, null);
			Game.setCurrentStartTime();
			Game.setElapsedTime(0);
			if (appFrame != null) {
				appFrame.setGame(game);
				appFrame.setVisible(true);
			} else {
				AppFrame frame = new AppFrame(game);
				frame.setVisible(true);
			}
		});
		submit.setPreferredSize(new Dimension(100, 20));
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
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(hardnessLabel);
		panel.add(slider);
		panel.add(submit);

		this.add(panel, BorderLayout.CENTER);
		this.pack();
		this.setLocationRelativeTo(null);
	}
}
