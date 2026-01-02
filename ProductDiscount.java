package OnlineStoreApp;

import java.io.Serializable;

class ProductDiscount implements Serializable {
	String productId;
	double discountPercent;
	int minQuantity;
	boolean active;

	public ProductDiscount(String productId, double discountPercent, int minQuantity) {
		this.productId = productId;
		this.discountPercent = discountPercent;
		this.minQuantity = minQuantity;
		this.active = true;
	}

	public ProductDiscount(String productId, double discountPercent, int minQuantity, boolean active) {
		this.productId = productId;
		this.discountPercent = discountPercent;
		this.minQuantity = minQuantity;
		this.active = active;
	}
}

