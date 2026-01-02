package OnlineStoreApp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/* ===================== DISCOUNT TABLE BUTTON RENDERER ===================== */
class DiscountButtonRenderer extends JPanel implements TableCellRenderer {
	private JButton editButton;
	private JButton removeButton;

	public DiscountButtonRenderer() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		setOpaque(true);

		editButton = new JButton("Edit");
		editButton.setBackground(new Color(255, 193, 7));
		editButton.setForeground(Color.BLACK);
		editButton.setFocusPainted(false);
		editButton.setBorderPainted(false);
		editButton.setOpaque(true);
		editButton.setPreferredSize(new Dimension(60, 25));

		removeButton = new JButton("Remove");
		removeButton.setBackground(new Color(220, 53, 69));
		removeButton.setForeground(Color.BLACK);
		removeButton.setFocusPainted(false);
		removeButton.setBorderPainted(false);
		removeButton.setOpaque(true);
		removeButton.setPreferredSize(new Dimension(70, 25));

		add(editButton);
		add(removeButton);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
		return this;
	}
}
