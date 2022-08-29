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
		this.getContentPane().setBackground(menu);
		this.setMinimumSize(new Dimension(400, 300));
		this.setPreferredSize(new Dimension(400, 300));
		this.setMaximumSize(new Dimension(400, 300));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);

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
			this.dispose();
			Game game = new Game();
			if (appFrame != null) {
				appFrame.setGame(game);
				appFrame.setVisible(true);
			} else {
				AppFrame frame = new AppFrame(game);
				frame.setVisible(true);
			}
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

		this.add(hardnessLabel, BorderLayout.NORTH);
		this.add(slider, BorderLayout.CENTER);
		this.add(submit, BorderLayout.SOUTH);

		this.validate();
	}
}
