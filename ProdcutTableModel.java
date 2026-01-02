package OnlineStoreApp;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

class ProductTableModel extends AbstractTableModel {
	List<Product> list = new ArrayList<>();
	String[] c = { "Image", "ID", "Product", "Category", "Price", "Stock", "Discount Info" };

	void refresh() {
		list = StoreData.inventory.list();
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
		Product p = list.get(r);
		return switch (col) {
		case 0 -> DataManager.getProductImage(p.imageName);
		case 1 -> p.id;
		case 2 -> p.name;
		case 3 -> p.category;
		case 4 -> String.format("%.2f TL", p.price);
		case 5 -> p.stock;
		case 6 -> getDiscountInfo(p); // İndirim bilgisi
		default -> "";
		};
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0) {
			return ImageIcon.class;
		}
		return String.class;
	}

	Product get(int r) {
		return list.get(r);
	}

	// İndirim bilgilerini döndüren yardımcı metod
	private String getDiscountInfo(Product p) {
		ProductDiscount discount = StoreData.discountManager.getProductDiscount(p.id);
		if (discount != null && discount.active) {
			return String.format("İndirim: %.0f%% (%d+ adet)", discount.discountPercent, discount.minQuantity);
		}
		return "Standart kategori indirimi";
	}
}

