package sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class RecordsDialog extends JDialog {
	public static ArrayList<Record> records = new ArrayList<>();

	public RecordsDialog(AppFrame frame) {
		super(frame, "Records", true);

		records.sort(Comparator.comparingInt(o -> o.time));

		Color menu = new Color(29, 37, 40);

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);

		JPanel panel = new JPanel();
		panel.setBackground(menu);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		for (int i = 0; i < records.size(); i++) {
			JLabel label = new JLabel((i+1) + ". " + records.get(i).time + "sec    difficulty: " + records.get(i).diff);
			label.setFont(new Font("font1", Font.PLAIN, 15));
			label.setForeground(Color.WHITE);
			label.setPreferredSize(new Dimension(300, 30));
			label.setAlignmentX(Component.LEFT_ALIGNMENT);

			panel.add(label);
		}

		this.add(panel, BorderLayout.CENTER);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	public record Record(int time, int diff) {}
}
