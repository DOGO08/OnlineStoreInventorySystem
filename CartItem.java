package OnlineStoreApp;

/* ===================== CART ===================== */
class CartItem {
	Product p;
	int q;
	double d;
	String discountInfo;

	CartItem(Product p, int q) {
		this.p = p;
		this.q = q;
		
		// Ürün bazlı indirimi kontrol et
		ProductDiscount productDiscount = StoreData.discountManager.getProductDiscount(p.id);
		if (productDiscount != null && productDiscount.active && q >= productDiscount.minQuantity) {
			d = productDiscount.discountPercent / 100.0;
			discountInfo = String.format("Ürün indirimi: %.0f%%", productDiscount.discountPercent);
		} else {
			// Eski kategori bazlı indirimler (backward compatibility)
			if (p.category.equals("Electronics") && q >= 2) {
				d = 0.10;
				discountInfo = "Kategori indirimi: 10%";
			} else if (p.category.equals("Stationery") && q >= 5) {
				d = 0.15;
				discountInfo = "Kategori indirimi: 15%";
			} else if (p.category.equals("Accessories") && q >= 3) {
				d = 0.05;
				discountInfo = "Kategori indirimi: 5%";
			} else {
				d = 0.0;
				discountInfo = "İndirim yok";
			}
		}
	}

	double subtotal() {
		return p.price * q;
	}

	double discount() {
		return subtotal() * d;
	}

	double total() {
		return subtotal() - discount();
	}
}
