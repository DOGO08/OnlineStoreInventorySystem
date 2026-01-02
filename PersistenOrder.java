package OnlineStoreApp;

import java.io.Serializable;
import java.time.LocalDateTime;

class PersistentOrder implements Serializable {
	int id;
	double total;
	String date;  

	public PersistentOrder() {
	}  

	public PersistentOrder(int id, double total, LocalDateTime date) {
		this.id = id;
		this.total = total;
		this.date = date.toString();
	}

	public LocalDateTime getDate() {
		return LocalDateTime.parse(date);
	}
}
