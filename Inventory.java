package OnlineStoreApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* ===================== INVENTORY ===================== */
class Inventory {
	Map<String, Product> map = new ConcurrentHashMap<>();
	AtomicInteger c = new AtomicInteger(1000);

	void add(String n, String cat, double p, int s) {
		add(n, cat, p, s, "");
	}

	void add(String n, String cat, double p, int s, String img) {
		Product pr = new Product("P" + c.getAndIncrement(), n, cat, p, s, img);
		map.put(pr.id, pr);
		StoreData.saveInventory();
	}

	void update(String id, String name, String category, double price, int stock) {
		update(id, name, category, price, stock, null);
	}

	void update(String id, String name, String category, double price, int stock, String imageName) {
		Product p = map.get(id);
		if (p != null) {
			p.name = name;
			p.category = category;
			p.price = price;
			p.stock = stock;
			if (imageName != null) {
				p.imageName = imageName;
			}
			StoreData.saveInventory();
		}
	}

	void delete(String id) {
		map.remove(id);
		StoreData.saveInventory();
	}

	List<Product> list() {
		return new ArrayList<>(map.values());
	}
}

