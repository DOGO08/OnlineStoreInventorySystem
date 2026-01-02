package OnlineStoreApp;

import java.io.Serializable;
import java.time.LocalDateTime;

/* ===================== ABANDONED CART ITEM CLASS ===================== */
public class AbandonedCartItem implements Serializable {
	String productId;
	String productName;
	int quantity;
	double price;
	LocalDateTime abandonedDate;

	public AbandonedCartItem(String productId, String productName, int quantity, double price) {
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.abandonedDate = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return String.format("%s x%d (%.2f TL) - %s", productName, quantity, price, 
				abandonedDate.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
	}
}

