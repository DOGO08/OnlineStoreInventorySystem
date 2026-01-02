package OnlineStoreApp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class OrderManager {
	List<Order> orders = new ArrayList<>();
	AtomicInteger c = new AtomicInteger(1);

	void create(double total) {
		orders.add(new Order(c.getAndIncrement(), total));
		StoreData.saveOrders();
	}

	List<Order> list() {
		return new ArrayList<>(orders);
	}
}
