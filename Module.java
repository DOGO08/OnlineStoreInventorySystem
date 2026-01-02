package OnlineStoreApp;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* ===================== MAIN ===================== */
public class Module {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("Label.foreground", Color.BLACK);
			UIManager.put("TextField.foreground", Color.BLACK);
			UIManager.put("TextArea.foreground", Color.BLACK);
			UIManager.put("Table.foreground", Color.BLACK);
			UIManager.put("TableHeader.foreground", Color.BLACK);
			UIManager.put("Button.foreground", Color.BLACK);
		} catch (Exception ignored) {
		}
		SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
	}
}


enum Role {
	ADMIN, USER
}