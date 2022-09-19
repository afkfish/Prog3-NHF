package sudoku;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class RecordsDialog extends JDialog {
	private static ArrayList<Record> records = new ArrayList<>();

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
			JLabel label = new JLabel((i+1) + ". " + records.get(i).time + " sec    difficulty: " + records.get(i).diff);
			label.setFont(new Font("font1", Font.PLAIN, 15));
			label.setForeground(Color.WHITE);
			label.setPreferredSize(new Dimension(300, 30));
			label.setAlignmentX(Component.CENTER_ALIGNMENT);

			panel.add(label);
		}

		if (records.size() > 0) {
			JButton saveRecords = new JButton("Export");
			saveRecords.addActionListener(actionEvent -> saveRecords());
			saveRecords.setPreferredSize(new Dimension(100, 20));
			saveRecords.setUI(new BasicButtonUI() {
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
			saveRecords.setBackground(menu);
			saveRecords.setForeground(Color.WHITE);
			saveRecords.setAlignmentX(Component.CENTER_ALIGNMENT);

			panel.add(saveRecords);
		} else {
			JLabel label = new JLabel("No records yet!");
			label.setFont(new Font("font1", Font.PLAIN, 15));
			label.setForeground(Color.WHITE);
			label.setPreferredSize(new Dimension(300, 30));
			label.setAlignmentX(Component.CENTER_ALIGNMENT);

			panel.add(label);
		}

		this.add(panel, BorderLayout.CENTER);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	public static void addRecord(int time, int hardness) {
		records.add(new Record(time, hardness));
	}

	public static void saveRecords() {
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("records.dat"));
			outputStream.writeInt(records.size());
			for (Record r : records) {
				outputStream.writeObject(r);
			}
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadRecords(String file) throws IOException, ClassNotFoundException {
		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file == null ? "records.dat" : file));
		int n = inputStream.readInt();
		ArrayList<Record> recordArrayList = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			recordArrayList.add((Record) inputStream.readObject());
		}
		inputStream.close();

		records = recordArrayList;
	}

	public record Record(int time, int diff) implements Serializable {}
}
