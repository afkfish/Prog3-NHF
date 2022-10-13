package com.afkfish.sudoku;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class NewGame extends JFrame {
	public NewGame(AppFrame appFrame) {
		super("Sudoku");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel contentPane = new JPanel(new GridLayout(1, 1));
		this.setContentPane(contentPane);
		this.setSize(500, 400);

		AppFrame.hardness = 2;

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setFocusable(false);

		tabbedPane.addTab("Create new", initCreatePanel(appFrame));
		tabbedPane.addTab("Import", initImportPanel(appFrame));

		contentPane.add(tabbedPane);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel initCreatePanel(AppFrame appFrame) {
		JPanel createPanel = new JPanel(new BorderLayout());
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

		JLabel hardnessLabel = new JLabel("Game hardness level:  " + AppFrame.hardness);
		hardnessLabel.setHorizontalAlignment(0);
		hardnessLabel.setPreferredSize(new Dimension(300, 30));
		hardnessLabel.setFont(new Font("font1", Font.PLAIN, 20));
		hardnessLabel.setForeground(Color.WHITE);
		hardnessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JSlider slider = new JSlider(0, 10, AppFrame.hardness);
		slider.setPreferredSize(new Dimension(300, 20));
		slider.setFocusable(false);
		slider.setUI(new BasicSliderUI());
		slider.setSnapToTicks(true);
		slider.setBorder(null);
		slider.addChangeListener(changeEvent -> {
			AppFrame.setHardness(slider.getValue());
			hardnessLabel.setText("Game hardness level:  " + AppFrame.hardness);
		});
		slider.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel bottomPanel = new JPanel();
		JButton ok = new JButton("OK");
		ok.addActionListener(submitActionEvent -> {
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

		topPanel.add(hardnessLabel);
		topPanel.add(slider);
		bottomPanel.add(ok);
		createPanel.add(topPanel, BorderLayout.PAGE_START);
		createPanel.add(bottomPanel, BorderLayout.PAGE_END);

		return createPanel;
	}

	private JPanel initImportPanel(AppFrame appFrame) {
		JPanel importPanel = new JPanel(new BorderLayout());

		JPanel textPanel = new JPanel(new GridLayout(2, 1));
		JLabel label = new JLabel("Path to file:");
		JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(400, 30));

		JPanel buttonPanel = new JPanel();
		JButton open = new JButton("Open");
		open.addActionListener(actionEvent -> {
			FileDialog fileDialog = new FileDialog(this, "Open game", FileDialog.LOAD);
			fileDialog.setDirectory(System.getProperty("user.dir"));
			fileDialog.setFile("*.DAT;*.dat");
			fileDialog.setLocationRelativeTo(null);
			fileDialog.setVisible(true);

			textField.setText(fileDialog.getDirectory() + fileDialog.getFile());
			textField.dispatchEvent(new KeyEvent(textField, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), KeyEvent.SHIFT_DOWN_MASK, KeyEvent.VK_ESCAPE, ' '));
		});
		JButton ok = new JButton("OK");
		ok.addActionListener(actionEvent -> {
			this.dispose();
			if (!textField.getText().equals("")) {
				Game game;
				try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(textField.getText()))) {
					Grid active = (Grid) ois.readObject();
					Grid solved = (Grid) ois.readObject();

					if (!Grid.compare(active, solved)) {
						long time = ois.readLong();
						Game.setCurrentStartTime();
						Game.setElapsedTime(time);
						AppFrame.hardness = ois.readInt();

						game = new Game(active, solved);
						if (appFrame != null) {
							appFrame.setGame(game);
							appFrame.setVisible(true);
						} else {
							AppFrame frame = new AppFrame(game);
							frame.setVisible(true);
						}
					} else {
						new ErrorDialog(this, "Error", true, "Error! This game is already solved!");
					}
				} catch (IOException | ClassNotFoundException e) {
					new ErrorDialog(this, "Error", true, "Error in loading game! \nMaybe bad save.");
				}
			}
		});
		ok.setEnabled(false);

		textField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}


			@Override
			public void keyReleased(KeyEvent e) {
				ok.setEnabled(!textField.getText().equals(""));
			}
		});

		textPanel.add(label);
		textPanel.add(textFieldPanel);

		SDropTargetListener dropTargetListener = new SDropTargetListener(textField);
		new DropTarget(textPanel, dropTargetListener);
		new DropTarget(buttonPanel, dropTargetListener);
		new DropTarget(textFieldPanel, dropTargetListener);
		new DropTarget(importPanel, dropTargetListener);

		textFieldPanel.add(textField);

		buttonPanel.add(open);
		buttonPanel.add(ok);

		importPanel.add(textPanel, BorderLayout.PAGE_START);
		importPanel.add(buttonPanel, BorderLayout.PAGE_END);

		return importPanel;
	}

}
