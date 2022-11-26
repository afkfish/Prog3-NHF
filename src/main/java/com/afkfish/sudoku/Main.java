package com.afkfish.sudoku;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

public class Main {
	public static final String version = "1.0";
	public static void main(String[] args) {
		/*
		 * Note: When using the app on macOS the menubar is moved to the top menubar and the default laf is preserved.
		 */
		if(Objects.equals(System.getProperty("os.name"), "Mac OS X")) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		try {
			UIManager.setLookAndFeel(new FlatDarculaLaf());
		} catch (UnsupportedLookAndFeelException e) {
			throw new RuntimeException(e);
		}
		try {
			RecordsDialog.loadRecords("records.dat");
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error in importing records!");
		}

		Game game;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game.dat"))) {
			Grid active = (Grid) ois.readObject();
			Grid solved = (Grid) ois.readObject();

			if (!Grid.compare(active, solved)) {
				long time = ois.readLong();
				Game.setCurrentStartTime();
				Game.setElapsedTime(time);
				AppFrame.hardness = ois.readInt();

				game = new Game(active, solved);
				AppFrame frame = new AppFrame(game);
				frame.setVisible(true);
			} else {
				NewGame startDialog = new NewGame(null);
				startDialog.setVisible(true);
			}
		} catch (IOException | ClassNotFoundException e) {
			NewGame startDialog = new NewGame(null);
			startDialog.setVisible(true);
		}
	}
}
