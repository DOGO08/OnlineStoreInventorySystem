package OnlineStoreApp;

import java.util.ArrayList;
import java.util.List; 

class AbandonedCartManager {
	private List<AbandonedCartItem> abandonedItems = new ArrayList<>();

	public AbandonedCartManager() {
	}

	public void loadAbandonedCart() {
		abandonedItems = DataManager.loadAbandonedCart();
	}

	public void saveAbandonedCart() {
		DataManager.saveAbandonedCart(abandonedItems);
	}

	public void addAbandonedItem(String productId, String productName, int quantity, double price) {
		abandonedItems.add(new AbandonedCartItem(productId, productName, quantity, price));
		saveAbandonedCart();
	}

	public List<AbandonedCartItem> getAbandonedItems() {
		return new ArrayList<>(abandonedItems);
	}

	public void clearAbandonedCart() {
		abandonedItems.clear();
		saveAbandonedCart();
	}

	public void restoreAbandonedItemsToStock() {
		for (AbandonedCartItem item : abandonedItems) {
			Product product = StoreData.inventory.map.get(item.productId);
			if (product != null) {
				product.stock += item.quantity;
				System.out.println("Restored to stock: " + item.productName + " x" + item.quantity);
			}
		}
		StoreData.saveInventory();
		clearAbandonedCart();
	}
}
