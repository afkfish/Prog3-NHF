package sudoku;

import com.bulenkov.darcula.DarculaLaf;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new DarculaLaf());
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
				Game.time = ois.readLong();
				AppFrame.hardness = ois.readInt();

				game = new Game(active, solved);
				AppFrame frame = new AppFrame(game);
				frame.setVisible(true);
			} else {
				GameDialog startDialog = new GameDialog(null);
				startDialog.setVisible(true);
			}
		} catch (IOException | ClassNotFoundException e) {
			GameDialog startDialog = new GameDialog(null);
			startDialog.setVisible(true);
		}
		//NewGame dialog = new NewGame();
	}
}
