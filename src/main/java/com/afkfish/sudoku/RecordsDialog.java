package com.afkfish.sudoku;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class stores and shows the {@link Record}s. It can save and load from file.
 */
public class RecordsDialog extends JDialog {
	private static ArrayList<Record> records = new ArrayList<>();

	public RecordsDialog(AppFrame frame) {
		super(frame, "Records", true);

		records.sort(Comparator.comparingInt(o -> o.time));

		Color menu = new Color(29, 37, 40);

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(true);

		JPanel panel = new JPanel();
		panel.setBackground(menu);
		panel.setLayout(new GridLayout(records.size() + 2, 3));
		panel.add(new JLabel(""));
		panel.add(new JLabel("Time in sec"));
		panel.add(new JLabel("Difficulty"));

		for (int i = 0; i < records.size(); i++) {
			JLabel no = new JLabel((i + 1) + ".");
			JLabel label = new JLabel(String.valueOf(records.get(i).time));
			JLabel diff = new JLabel(String.valueOf(records.get(i).diff));

			panel.add(no);
			panel.add(label);
			panel.add(diff);
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

			panel.add(new JLabel(""));
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

	/**
	 * Writes the data to a records.dat file.
	 */
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

	/**
	 * Loads data
	 * @param file file for where to load data from
	 * @throws IOException read error
	 * @throws ClassNotFoundException wrong file format
	 */
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
