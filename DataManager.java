package OnlineStoreApp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

class DataManager {
	private static final String INVENTORY_FILE = "inventory.dat";
	private static final String ORDERS_FILE = "orders.dat";
	private static final String USERS_FILE = "users.dat";
	private static final String IMAGE_DIR = "product_images";
	private static final String DISCOUNT_FILE = "discounts.dat";
	private static final String ABANDONED_CART_FILE = "abandoned_cart.dat"; // Yeni: Terk edilmiş sepet

	static {
		checkAndCreateFiles();
	}

	private static void checkAndCreateFiles() {
		try {
			Files.createDirectories(Paths.get("."));
			Files.createDirectories(Paths.get(IMAGE_DIR));

			if (!Files.exists(Paths.get(INVENTORY_FILE))) {
				saveObject(new ArrayList<Product>(), INVENTORY_FILE);
			}

			if (!Files.exists(Paths.get(ORDERS_FILE))) {
				saveObject(new ArrayList<PersistentOrder>(), ORDERS_FILE);
			}

			if (!Files.exists(Paths.get(USERS_FILE))) {
				Map<String, User> defaultUsers = new HashMap<>();
				defaultUsers.put("admin", new User("admin", "1234", Role.ADMIN));
				defaultUsers.put("user", new User("user", "1234", Role.USER));
				saveObject(defaultUsers, USERS_FILE);
			}

			if (!Files.exists(Paths.get(DISCOUNT_FILE))) {
				saveObject(new HashMap<String, ProductDiscount>(), DISCOUNT_FILE);
			}

			if (!Files.exists(Paths.get(ABANDONED_CART_FILE))) {
				saveObject(new ArrayList<AbandonedCartItem>(), ABANDONED_CART_FILE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveUsers(Map<String, User> users) {
		saveObject(users, USERS_FILE);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, User> loadUsers() {
		Object obj = loadObject(USERS_FILE);
		if (obj instanceof Map) {
			return (Map<String, User>) obj;
		}
		return new HashMap<>();
	}

	public static boolean addUser(String username, String password, Role role) {
		Map<String, User> users = loadUsers();

		if (users.containsKey(username)) {
			return false;
		}

		users.put(username, new User(username, password, role));
		saveUsers(users);
		return true;
	}

	public static String saveImage(File imageFile, String productId) throws IOException {
		String extension = getFileExtension(imageFile.getName());
		String newFileName = productId + extension;
		Path destination = Paths.get(IMAGE_DIR, newFileName);
		Files.copy(imageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
		return newFileName;
	}

	public static ImageIcon getProductImageLarge(String imageName) {
		if (imageName == null || imageName.isEmpty()) {
			return getDefaultImageLarge();
		}

		Path imagePath = Paths.get(IMAGE_DIR, imageName);
		if (Files.exists(imagePath)) {
			try {
				ImageIcon icon = new ImageIcon(imagePath.toString());
				return scaleImage(icon, 400, 400);
			} catch (Exception e) {
				return getDefaultImageLarge();
			}
		}
		return getDefaultImageLarge();
	}

	public static ImageIcon getProductImage(String imageName) {
		if (imageName == null || imageName.isEmpty()) {
			return getDefaultImage();
		}

		Path imagePath = Paths.get(IMAGE_DIR, imageName);
		if (Files.exists(imagePath)) {
			try {
				ImageIcon icon = new ImageIcon(imagePath.toString());
				return scaleImage(icon, 100, 100);
			} catch (Exception e) {
				return getDefaultImage();
			}
		}
		return getDefaultImage();
	}

	private static ImageIcon getDefaultImage() {
		try {
			BufferedImage defaultImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = defaultImage.createGraphics();
			g2d.setColor(new Color(240, 240, 240));
			g2d.fillRect(0, 0, 100, 100);
			g2d.setColor(Color.GRAY);
			g2d.drawRect(0, 0, 99, 99);
			g2d.setFont(new Font("Arial", Font.PLAIN, 12));
			g2d.drawString("No Image", 25, 50);
			g2d.dispose();
			return new ImageIcon(defaultImage);
		} catch (Exception e) {
			return new ImageIcon();
		}
	}

	private static ImageIcon getDefaultImageLarge() {
		try {
			BufferedImage defaultImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = defaultImage.createGraphics();
			g2d.setColor(new Color(240, 240, 240));
			g2d.fillRect(0, 0, 400, 400);
			g2d.setColor(Color.GRAY);
			g2d.drawRect(0, 0, 399, 399);
			g2d.setFont(new Font("Arial", Font.BOLD, 24));
			g2d.drawString("No Image", 150, 200);
			g2d.dispose();
			return new ImageIcon(defaultImage);
		} catch (Exception e) {
			return new ImageIcon();
		}
	}

	private static ImageIcon scaleImage(ImageIcon icon, int width, int height) {
		Image img = icon.getImage();
		Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImg);
	}

	private static String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		return (dotIndex == -1) ? ".jpg" : fileName.substring(dotIndex);
	}

	private static void saveObject(Object obj, String filename) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
			oos.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static Object loadObject(String filename) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void saveInventory(List<Product> products) {  
		saveObject(products, INVENTORY_FILE);
	}

	@SuppressWarnings("unchecked")
	public static List<Product> loadInventory() {
		Object obj = loadObject(INVENTORY_FILE);
		if (obj instanceof List) {
			return (List<Product>) obj;
		}
		return new ArrayList<>();
	}

	public static void saveOrders(List<PersistentOrder> orders) {
		saveObject(orders, ORDERS_FILE);
	}

	@SuppressWarnings("unchecked")
	public static List<PersistentOrder> loadOrders() {
		Object obj = loadObject(ORDERS_FILE);
		if (obj instanceof List) {
			return (List<PersistentOrder>) obj;
		}
		return new ArrayList<>();
	}

	public static void saveDiscounts(Map<String, ProductDiscount> discounts) {
		saveObject(discounts, DISCOUNT_FILE);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, ProductDiscount> loadDiscounts() {
		Object obj = loadObject(DISCOUNT_FILE);
		if (obj instanceof Map) {
			return (Map<String, ProductDiscount>) obj;
		}
		return new HashMap<>();
	}

	// Yeni: Terk edilmiş sepet öğelerini kaydet
	public static void saveAbandonedCart(List<AbandonedCartItem> items) {
		saveObject(items, ABANDONED_CART_FILE);
	}

	@SuppressWarnings("unchecked")
	public static List<AbandonedCartItem> loadAbandonedCart() {
		Object obj = loadObject(ABANDONED_CART_FILE);
		if (obj instanceof List) {
			return (List<AbandonedCartItem>) obj;
		}
		return new ArrayList<>();
	}
}
