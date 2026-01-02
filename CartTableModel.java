package OnlineStoreApp;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

class CartTableModel extends AbstractTableModel {
	List<CartItem> list = new ArrayList<>();
	String[] c = { "Product", "Category", "Quantity", "Unit Price", "Discount %", "Discount Amount", "Total", "Actions" };

	void refresh(Cart cart) {
		list = new ArrayList<>(cart.list());
		fireTableDataChanged();
	}

	public int getRowCount() {
		return list.size();
	}

	public int getColumnCount() {
		return c.length;
	}

	public String getColumnName(int i) {
		return c[i];
	}

	public Object getValueAt(int r, int col) {
		CartItem it = list.get(r);
		return switch (col) {
		case 0 -> it.p.name;
		case 1 -> it.p.category;
		case 2 -> it.q;
		case 3 -> String.format("%.2f TL", it.p.price);
		case 4 -> it.d > 0 ? String.format("%.0f%%", it.d * 100) : "-";
		case 5 -> it.d > 0 ? String.format("-%.2f TL", it.discount()) : "-";
		case 6 -> String.format("%.2f TL", it.total());
		case 7 -> "";
		default -> "";
		};
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 2) {
			return Integer.class;
		} else if (columnIndex == 7) {
			return JButton.class;
		}
		return String.class;
	}

	CartItem get(int r) {
		return list.get(r);
	}
}

