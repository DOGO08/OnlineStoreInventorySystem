package OnlineStoreApp;

import java.util.HashMap;
import java.util.Map;

class DiscountManager {
	private Map<String, ProductDiscount> productDiscounts = new HashMap<>();
	private double bulkDiscountThreshold = 1000.0;
	private double bulkDiscountPercent = 5.0;

	public DiscountManager() {
	}

	public void loadDiscounts() {
		productDiscounts = DataManager.loadDiscounts();
	}

	public void saveDiscounts() {
		DataManager.saveDiscounts(productDiscounts);
	}

	public void setProductDiscount(String productId, double discountPercent, int minQuantity) {
		productDiscounts.put(productId, new ProductDiscount(productId, discountPercent, minQuantity));
		saveDiscounts();
	}

	public void removeProductDiscount(String productId) {
		productDiscounts.remove(productId);
		saveDiscounts();
	}

	public ProductDiscount getProductDiscount(String productId) {
		return productDiscounts.get(productId);
	}

	public boolean hasProductDiscount(String productId) {
		return productDiscounts.containsKey(productId);
	}

	public void setBulkDiscount(double threshold, double percent) {
		this.bulkDiscountThreshold = threshold;
		this.bulkDiscountPercent = percent;
		saveDiscounts();
	}

	public double getBulkDiscountThreshold() {
		return bulkDiscountThreshold;
	}

	public double getBulkDiscountPercent() {
		return bulkDiscountPercent;
	}

	public Map<String, ProductDiscount> getAllDiscounts() {
		return new HashMap<>(productDiscounts);
	}

	public void updateDiscount(String productId, double discountPercent, int minQuantity, boolean active) {
		ProductDiscount discount = productDiscounts.get(productId);
		if (discount != null) {
			discount.discountPercent = discountPercent;
			discount.minQuantity = minQuantity;
			discount.active = active;
			saveDiscounts();
		}
	}
}

