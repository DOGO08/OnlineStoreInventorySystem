package OnlineStoreApp;

import java.util.ArrayList;
import java.util.List;

class StoreData {
	static Inventory inventory = new Inventory();
	static OrderManager orderManager = new OrderManager();
	static DiscountManager discountManager = new DiscountManager();
	static AbandonedCartManager abandonedCartManager = new AbandonedCartManager(); // Yeni: Terk edilmiş sepet yöneticisi

	static {
		loadPersistentData();

		if (inventory.map.isEmpty()) {
			inventory.add("Coffee Machine", "Electronics", 1299, 5, "");
			inventory.add("Headphones", "Electronics", 349, 10, "");
			inventory.add("Notebook", "Stationery", 25, 40, "");
			inventory.add("Backpack", "Accessories", 249, 8, "");
		}
	}

	private static void loadPersistentData() {
		List<Product> products = DataManager.loadInventory();
		for (Product product : products) {
			inventory.map.put(product.id, product);
			try {
				int currentId = Integer.parseInt(product.id.substring(1));
				if (currentId >= inventory.c.get()) {
					inventory.c.set(currentId + 1);
				}
			} catch (NumberFormatException e) {
			}
		}

		List<PersistentOrder> orders = DataManager.loadOrders();
		orderManager.orders.clear();
		for (PersistentOrder pOrder : orders) {
			Order order = new Order(pOrder.id, pOrder.total);
			order.date = pOrder.getDate();
			orderManager.orders.add(order);
			if (pOrder.id >= orderManager.c.get()) {
				orderManager.c.set(pOrder.id + 1);
			}
		}

		discountManager.loadDiscounts();
		abandonedCartManager.loadAbandonedCart(); // Yeni: Terk edilmiş sepeti yükle
	}

	static void saveInventory() {
		DataManager.saveInventory(new ArrayList<>(inventory.map.values()));
	}

	static void saveOrders() {
		List<PersistentOrder> persistentOrders = new ArrayList<>();
		for (Order order : orderManager.orders) {
			persistentOrders.add(new PersistentOrder(order.id, order.total, order.date));
		}
		DataManager.saveOrders(persistentOrders);
	}

	static void saveDiscounts() {
		discountManager.saveDiscounts();
	}

	// Yeni: Terk edilmiş sepeti kaydet
	static void saveAbandonedCart() {
		abandonedCartManager.saveAbandonedCart();
	}
}

