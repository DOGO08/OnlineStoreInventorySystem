package OnlineStoreApp;

import java.io.Serializable;

class Product implements Serializable {
	String id, name, category;
	double price;
	int stock;
	String imageName;

	Product(String i, String n, String c, double p, int s) {
		id = i;
		name = n;
		category = c;
		price = p;
		stock = s;
		imageName = "";
	}

	Product(String i, String n, String c, double p, int s, String img) {
		id = i;
		name = n;
		category = c;
		price = p;
		stock = s;
		imageName = img;
	}
}
