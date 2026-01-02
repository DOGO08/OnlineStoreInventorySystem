package OnlineStoreApp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

class CartButtonEditor extends DefaultCellEditor {
	private JPanel panel;
	private JButton increaseButton;
	private JButton decreaseButton;
	private StoreGUI parentFrame;
	private int currentRow;

	public CartButtonEditor(JCheckBox checkBox, StoreGUI parentFrame) {
		super(checkBox);
		this.parentFrame = parentFrame;

		setClickCountToStart(1);

		panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		panel.setOpaque(true);

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

		increaseButton.addActionListener(e -> {
			parentFrame.incrementCartItem(currentRow);
			fireEditingStopped();
		});

		decreaseButton.addActionListener(e -> {
			parentFrame.decrementCartItem(currentRow);
			fireEditingStopped();
		});

		panel.add(decreaseButton);
		panel.add(increaseButton);
	}

	@Override
	public Component getTableCellEditorComponent(
			JTable table, Object value, boolean isSelected,
			int row, int column) {

		currentRow = row;
		panel.setBackground(isSelected
				? table.getSelectionBackground()
				: table.getBackground());

		return panel;
	}

	@Override
	public Object getCellEditorValue() {
		return "";
	}
}
