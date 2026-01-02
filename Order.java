package OnlineStoreApp;

import java.time.LocalDateTime;

/* ===================== ORDER ===================== */
class Order {
	int id;
	double total;
	LocalDateTime date;

	Order(int id, double total) {
		this.id = id;
		this.total = total;
		this.date = LocalDateTime.now();
	}
}

