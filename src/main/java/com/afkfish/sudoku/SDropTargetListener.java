package com.afkfish.sudoku;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SDropTargetListener implements DropTargetListener {
	private final JTextField textField;

	public SDropTargetListener(JTextField textField) {
		this.textField = textField;
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {

	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {

	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {

	}

	@Override
	public void dragExit(DropTargetEvent dte) {

	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		dtde.acceptDrop(DnDConstants.ACTION_COPY);
		Transferable transferable = dtde.getTransferable();
		DataFlavor[] dataFlavors = transferable.getTransferDataFlavors();
		for (DataFlavor flavor : dataFlavors) {
			try {
				if (flavor.isFlavorJavaFileListType()) {
					List<File> files = new ArrayList<>();
					for (Object o : (Collection<?>) transferable.getTransferData(flavor)) {
						try {
							files.add((File) o);
						} catch (ClassCastException e) {
							throw new RuntimeException(e);
						}
					}

					for (File file :  files) {
						this.textField.setText(file.getPath());
						this.textField.dispatchEvent(new KeyEvent(textField, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), KeyEvent.SHIFT_DOWN_MASK, KeyEvent.VK_ESCAPE, ' '));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
