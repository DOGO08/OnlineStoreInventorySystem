package OnlineStoreApp;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	Map<String, CartItem> items = new LinkedHashMap<>();

	void add(Product p, int q) {
	    if (q <= 0) return;

	    if (p.stock < q) {
	        throw new IllegalArgumentException("Insufficient stock");
	    }

	    if (items.containsKey(p.id)) {
	        CartItem item = items.get(p.id);
	        items.put(p.id, new CartItem(p, item.q + q));
	    } else {
	        items.put(p.id, new CartItem(p, q));
	    }

	    // 🔴 ASIL OLAY BURASI
	    p.stock -= q;
	    StoreData.saveInventory();
	}



	void remove(Product p) {
		CartItem it = items.remove(p.id);
		if (it != null) {
			p.stock += it.q;
			StoreData.saveInventory();
		}
	}

	void updateQuantity(Product p, int newQuantity) {
		if (items.containsKey(p.id)) {
			CartItem item = items.get(p.id);
			int oldQuantity = item.q;
			int quantityDifference = newQuantity - oldQuantity;

			if (p.stock >= quantityDifference) {
				// Yeni miktarla yeni CartItem oluştur (indirimi yeniden hesaplamak için)
				items.put(p.id, new CartItem(p, newQuantity));
				p.stock -= quantityDifference;
				StoreData.saveInventory();
			} else {
				throw new IllegalArgumentException("Insufficient stock");
			}
		}
	}

	void incrementQuantity(Product p) {
		if (items.containsKey(p.id)) {
			CartItem item = items.get(p.id);
			if (p.stock > 0) {
				// Yeni miktarla yeni CartItem oluştur
				items.put(p.id, new CartItem(p, item.q + 1));
				p.stock--;
				StoreData.saveInventory();
			}
		}
	}

	void decrementQuantity(Product p) {
		if (items.containsKey(p.id)) {
			CartItem item = items.get(p.id);
			if (item.q > 1) {
				// Yeni miktarla yeni CartItem oluştur
				items.put(p.id, new CartItem(p, item.q - 1));
				p.stock++;
				StoreData.saveInventory();
			} else {
				remove(p);
			}
		}
	}

	double subtotal() {
		return items.values().stream().mapToDouble(CartItem::subtotal).sum();
	}

	double discount() {
		return items.values().stream().mapToDouble(CartItem::discount).sum();
	}

	double total() {
		double t = subtotal() - discount();
		// Toplam eşik değeri üzeri için ek toplu indirim
		double bulkThreshold = StoreData.discountManager.getBulkDiscountThreshold();
		double bulkPercent = StoreData.discountManager.getBulkDiscountPercent();
		
		if (t >= bulkThreshold && bulkPercent > 0) {
			t *= (1 - bulkPercent / 100.0);
		}
		return t;
	}

	// Toplu indirim kontrolü için yardımcı metod
	boolean hasBulkDiscount() {
		double subtotalWithItemDiscounts = subtotal() - discount();
		double bulkThreshold = StoreData.discountManager.getBulkDiscountThreshold();
		return subtotalWithItemDiscounts >= bulkThreshold;
	}

	boolean empty() {
		return items.isEmpty();
	}

	Collection<CartItem> list() {
		return items.values();
	}

	void clear() {
		// Sepeti temizlemeden önce terk edilmiş sepet kaydı oluştur
		if (!items.isEmpty()) {
			for (CartItem item : items.values()) {
				StoreData.abandonedCartManager.addAbandonedItem(
					item.p.id, 
					item.p.name, 
					item.q, 
					item.p.price
				);
				// Ürünleri stoklara geri ekle
				item.p.stock += item.q;
			}
			StoreData.saveInventory();
		}
		items.clear();
	}
}

