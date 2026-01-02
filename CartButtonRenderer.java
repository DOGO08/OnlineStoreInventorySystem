package OnlineStoreApp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/* ===================== CART BUTTON RENDERER ===================== */
class CartButtonRenderer extends JPanel implements TableCellRenderer {
	private JButton increaseButton;
	private JButton decreaseButton;

	public CartButtonRenderer() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		setOpaque(true);

		increaseButton = new JButton("+1");
		increaseButton.setBackground(new Color(40, 167, 69));
		increaseButton.setForeground(Color.BLACK);
		increaseButton.setFocusPainted(false);
		increaseButton.setBorderPainted(false);
		increaseButton.setOpaque(true);
		increaseButton.setPreferredSize(new Dimension(50, 30));

		decreaseButton = new JButton("-1");
		decreaseButton.setBackground(new Color(220, 53, 69));
		decreaseButton.setForeground(Color.BLACK);
		decreaseButton.setFocusPainted(false);
		decreaseButton.setBorderPainted(false);
		decreaseButton.setOpaque(true);
		decreaseButton.setPreferredSize(new Dimension(50, 30));

		add(decreaseButton);
		add(increaseButton);
	}

	@Override
	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		setBackground(isSelected
				? table.getSelectionBackground()
				: table.getBackground());

		return this;
	}
}
