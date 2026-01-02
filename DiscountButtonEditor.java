package OnlineStoreApp;

import java.awt.Color; 
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/* ===================== DISCOUNT BUTTON EDITOR ===================== */
class DiscountButtonEditor extends DefaultCellEditor {
	private JPanel panel;
	private JButton editButton;
	private JButton removeButton;
	private JDialog parentDialog;
	private JTable table;

	public DiscountButtonEditor(JCheckBox checkBox, JDialog parentDialog, JTable table) {
		super(checkBox);
		this.parentDialog = parentDialog;
		this.table = table;

		setClickCountToStart(1);

		panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		panel.setOpaque(true);

		editButton = new JButton("Edit");
		editButton.setBackground(new Color(255, 193, 7));
		editButton.setForeground(Color.BLACK);
		editButton.setFocusPainted(false);
		editButton.setPreferredSize(new Dimension(60, 25));

		removeButton = new JButton("Remove");
		removeButton.setBackground(new Color(220, 53, 69));
		removeButton.setForeground(Color.BLACK);
		removeButton.setFocusPainted(false);
		removeButton.setPreferredSize(new Dimension(70, 25));

		editButton.addActionListener(e -> {
			int row = table.getSelectedRow();
			if (row >= 0) {
				String productId = (String) table.getValueAt(row, 0);
				editProductDiscount(productId);
				fireEditingStopped();
			}
		});

		removeButton.addActionListener(e -> {
			int row = table.getSelectedRow();
			if (row >= 0) {
				String productId = (String) table.getValueAt(row, 0);
				Product product = StoreData.inventory.map.get(productId);
				String productName = product != null ? product.name : "Unknown";
				
				int confirm = JOptionPane.showConfirmDialog(parentDialog,
						"Remove discount for " + productName + "?", "Confirm Removal",
						JOptionPane.YES_NO_OPTION);
				
				if (confirm == JOptionPane.YES_OPTION) {
					StoreData.discountManager.removeProductDiscount(productId);
					StoreData.saveDiscounts();
					JOptionPane.showMessageDialog(parentDialog, "Discount removed!", "Success",
							JOptionPane.INFORMATION_MESSAGE);
					parentDialog.dispose();
					((AdminFrame) SwingUtilities.getWindowAncestor(table)).showDiscountManagementDialog();
				}
				fireEditingStopped();
			}
		});

		panel.add(editButton);
		panel.add(removeButton);
	}

	private void editProductDiscount(String productId) {
		Product product = StoreData.inventory.map.get(productId);
		if (product == null) return;
		
		ProductDiscount discount = StoreData.discountManager.getProductDiscount(productId);
		if (discount == null) return;
		
		JPanel discountPanel = new JPanel(new GridLayout(4, 2, 10, 10));
		discountPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel productLabel = new JLabel("Product:");
		JLabel productValue = new JLabel(product.name + " (" + product.id + ")");

		JLabel discountLabel = new JLabel("Discount Percent (%):");
		JTextField discountField = new JTextField(String.valueOf(discount.discountPercent));

		JLabel quantityLabel = new JLabel("Minimum Quantity:");
		JTextField quantityField = new JTextField(String.valueOf(discount.minQuantity));

		JLabel activeLabel = new JLabel("Active:");
		JCheckBox activeCheckBox = new JCheckBox("", discount.active);

		discountPanel.add(productLabel);
		discountPanel.add(productValue);
		discountPanel.add(discountLabel);
		discountPanel.add(discountField);
		discountPanel.add(quantityLabel);
		discountPanel.add(quantityField);
		discountPanel.add(activeLabel);
		discountPanel.add(activeCheckBox);

		int result = JOptionPane.showConfirmDialog(parentDialog, discountPanel, "Edit Product Discount",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			try {
				double discountPercent = Double.parseDouble(discountField.getText().trim());
				int minQuantity = Integer.parseInt(quantityField.getText().trim());
				boolean active = activeCheckBox.isSelected();

				if (discountPercent < 0 || discountPercent > 100) {
					JOptionPane.showMessageDialog(parentDialog, "Discount must be between 0-100%!", "Invalid Input",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (minQuantity < 1) {
					JOptionPane.showMessageDialog(parentDialog, "Minimum quantity must be at least 1!", "Invalid Input",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				StoreData.discountManager.updateDiscount(productId, discountPercent, minQuantity, active);
				StoreData.saveDiscounts();
				
				JOptionPane.showMessageDialog(parentDialog,
						String.format("Discount updated for %s", product.name),
						"Success", JOptionPane.INFORMATION_MESSAGE);
				
				parentDialog.dispose();
				((AdminFrame) SwingUtilities.getWindowAncestor(table)).showDiscountManagementDialog();

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(parentDialog, "Please enter valid numbers!", "Invalid Input",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
		return panel;
	}

	@Override
	public Object getCellEditorValue() {
		return "";
	}
}