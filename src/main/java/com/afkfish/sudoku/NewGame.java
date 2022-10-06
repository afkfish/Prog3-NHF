package com.afkfish.sudoku;

import com.bulenkov.darcula.ui.DarculaTabbedPaneUI;

import javax.swing.*;
import java.awt.*;

public class NewGame extends JDialog {
	private JPanel contentPane;
	private JTabbedPane tabbedPane;

	public NewGame() {
		contentPane = new JPanel(new GridLayout(1,1));
		setContentPane(contentPane);
		setModal(true);
		setSize(400, 300);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setUI(new DarculaTabbedPaneUI());

		JPanel createPanel = new JPanel(new GridLayout(1,1));
		tabbedPane.addTab("Create new", createPanel);

		JPanel importPanel = new JPanel(new GridLayout(1,1));
		tabbedPane.addTab("Import", importPanel);

		contentPane.add(tabbedPane);

		setLocationRelativeTo(null);
		setVisible(true);
	}
}
