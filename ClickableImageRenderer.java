package OnlineStoreApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;

class ClickableImageRenderer extends JLabel implements TableCellRenderer {
	private JFrame parentFrame;
	private JPopupMenu contextMenu;

	public ClickableImageRenderer(JFrame parentFrame) {
		this.parentFrame = parentFrame;
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.CENTER);

		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		contextMenu = new JPopupMenu();
		JMenuItem viewLargeMenuItem = new JMenuItem("View Large Image");
		viewLargeMenuItem.addActionListener(e -> showLargeImage());
		contextMenu.add(viewLargeMenuItem);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					showLargeImage();
				} else if (SwingUtilities.isRightMouseButton(e)) {
					contextMenu.show(ClickableImageRenderer.this, e.getX(), e.getY());
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(BorderFactory.createEmptyBorder());
			}
		});
	}

	private void showLargeImage() {
		if (getIcon() != null && !getText().equals("No Image")) {
			ImageIcon largeIcon = DataManager.getProductImageLarge(getImageName());

			JDialog imageDialog = new JDialog(parentFrame, "Product Image", true);
			imageDialog.setLayout(new BorderLayout());

			JLabel imageLabel = new JLabel(largeIcon);
			imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

			JScrollPane scrollPane = new JScrollPane(imageLabel);
			scrollPane.setPreferredSize(new Dimension(500, 500));

			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JButton closeButton = new JButton("Close");
			closeButton.addActionListener(e -> imageDialog.dispose());
			buttonPanel.add(closeButton);

			imageDialog.add(scrollPane, BorderLayout.CENTER);
			imageDialog.add(buttonPanel, BorderLayout.SOUTH);

			imageDialog.pack();
			imageDialog.setLocationRelativeTo(parentFrame);
			imageDialog.setVisible(true);
		}
	}

	private String getImageName() {
		Object clientProperty = getClientProperty("imageName");
		return clientProperty != null ? clientProperty.toString() : "";
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (isSelected) {
			setBackground(table.getSelectionBackground());
			setForeground(table.getSelectionForeground());
		} else {
			setBackground(table.getBackground());
			setForeground(table.getForeground());
		}

		if (value instanceof ImageIcon) {
			setIcon((ImageIcon) value);
			setText("");

			if (table.getModel() instanceof ProductTableModel) {
				ProductTableModel model = (ProductTableModel) table.getModel();
				Product product = model.get(row);
				putClientProperty("imageName", product.imageName);
			}
		} else {
			setIcon(null);
			setText("No Image");
		}

		return this;
	}
}
