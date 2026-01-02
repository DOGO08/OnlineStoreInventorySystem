package OnlineStoreApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

class AdminFrame extends JFrame {
	ProductTableModel productModel = new ProductTableModel();
	JTable table;
	JLabel notificationLabel;

	AdminFrame() {
		setTitle("Admin Dashboard - MyAmazon");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Color DARK_BLUE = new Color(19, 25, 33);
		Color BLUE = new Color(0, 120, 215);
		Color LIGHT_GRAY = new Color(240, 240, 240);
		Color WHITE = Color.WHITE;
		Color BLACK = Color.BLACK;
		Color WARNING_RED = new Color(220, 53, 69);
		Color WARNING_YELLOW = new Color(255, 193, 7);

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(DARK_BLUE);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		JLabel titleLabel = new JLabel("Admin Dashboard");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setForeground(WHITE);
		headerPanel.add(titleLabel, BorderLayout.WEST);

		notificationLabel = new JLabel();
		notificationLabel.setFont(new Font("Arial", Font.BOLD, 12));
		notificationLabel.setForeground(WARNING_YELLOW);
		notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerPanel.add(notificationLabel, BorderLayout.CENTER);

		JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		userPanel.setBackground(DARK_BLUE);

		JButton logoutButton = new JButton("Logout");
		logoutButton.setBackground(new Color(220, 53, 69));
		logoutButton.setForeground(BLACK);
		logoutButton.setFocusPainted(false);
		logoutButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
		userPanel.add(logoutButton);

		JButton restoreStockButton = new JButton("Restore Abandoned Cart Items");
		restoreStockButton.setBackground(new Color(255, 193, 7));
		restoreStockButton.setForeground(BLACK);
		restoreStockButton.setFocusPainted(false);
		restoreStockButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
		userPanel.add(restoreStockButton);

		headerPanel.add(userPanel, BorderLayout.EAST);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		buttonPanel.setBackground(LIGHT_GRAY);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JButton addButton = new JButton("Add Product");
		addButton.setForeground(Color.BLACK);
		JButton editButton = new JButton("Edit Product");
		editButton.setForeground(Color.BLACK);
		JButton deleteButton = new JButton("Delete Product");
		deleteButton.setForeground(Color.BLACK);
		JButton refreshButton = new JButton("Refresh");
		refreshButton.setForeground(Color.BLACK);
		JButton viewOrdersButton = new JButton("View Orders");
		viewOrdersButton.setForeground(Color.BLACK);
		JButton lowStockButton = new JButton("View Low Stock");
		lowStockButton.setForeground(Color.BLACK);
		lowStockButton.setBackground(WARNING_YELLOW);

		JButton addImageButton = new JButton("Add Product Image");
		addImageButton.setForeground(Color.BLACK);
		addImageButton.setBackground(new Color(40, 167, 69));
		addImageButton.setFocusPainted(false);
		addImageButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

		JButton discountButton = new JButton("Manage Discounts");
		discountButton.setForeground(Color.BLACK);
		discountButton.setBackground(new Color(138, 43, 226));
		discountButton.setFocusPainted(false);
		discountButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

		JButton setDiscountButton = new JButton("Set Product Discount");
		setDiscountButton.setForeground(Color.BLACK);
		setDiscountButton.setBackground(new Color(255, 193, 7));
		setDiscountButton.setFocusPainted(false);
		setDiscountButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

		for (JButton btn : new JButton[] { addButton, editButton, deleteButton, refreshButton, viewOrdersButton,
				lowStockButton, addImageButton, discountButton, setDiscountButton }) {
			btn.setBackground(BLUE);
			btn.setForeground(Color.BLACK);
			btn.setFocusPainted(false);
			btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
			buttonPanel.add(btn);
		}
		lowStockButton.setBackground(WARNING_YELLOW);
		addImageButton.setBackground(new Color(40, 167, 69));
		discountButton.setBackground(new Color(138, 43, 226));
		setDiscountButton.setBackground(new Color(255, 193, 7));

		table = new JTable(productModel) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				Product p = productModel.get(row);
				
				// İndirimli ürünleri farklı renkte göster
				if (StoreData.discountManager.hasProductDiscount(p.id)) {
					ProductDiscount discount = StoreData.discountManager.getProductDiscount(p.id);
					if (discount.active) {
						c.setBackground(new Color(220, 237, 200)); // Açık yeşil
					} else {
						c.setBackground(new Color(255, 243, 205)); // Açık sarı
					}
				} else if (p.stock <= 3) {
					c.setBackground(new Color(255, 243, 205));
					c.setForeground(Color.BLACK);
				} else if (p.stock <= 10) {
					c.setBackground(new Color(255, 248, 225));
				} else {
					c.setBackground(Color.WHITE);
				}
				return c;
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setRowHeight(100);
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		table.setForeground(Color.BLACK);
		table.setGridColor(new Color(230, 230, 230));
		table.getTableHeader().setBackground(DARK_BLUE);
		table.getTableHeader().setForeground(Color.BLACK);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

		table.getColumnModel().getColumn(0).setCellRenderer(new ClickableImageRenderer(this));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(scrollPane, BorderLayout.SOUTH);

		add(mainPanel);

		productModel.refresh();
		checkLowStock();

		restoreStockButton.addActionListener(e -> {
			restoreAbandonedCartItems();
		});

		refreshButton.addActionListener(e -> {
			productModel.refresh();
			checkLowStock();
			JOptionPane.showMessageDialog(this, "Inventory refreshed!");
		});

		addButton.addActionListener(e -> {
			JTextField nameField = new JTextField();
			nameField.setForeground(Color.BLACK);
			JTextField categoryField = new JTextField();
			categoryField.setForeground(Color.BLACK);
			JTextField priceField = new JTextField();
			priceField.setForeground(Color.BLACK);
			JTextField stockField = new JTextField();
			stockField.setForeground(Color.BLACK);

			JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
			inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			JLabel nameLabel = new JLabel("Product Name:");
			nameLabel.setForeground(Color.BLACK);
			JLabel categoryLabel = new JLabel("Category:");
			categoryLabel.setForeground(Color.BLACK);
			JLabel priceLabel = new JLabel("Price (TL):");
			priceLabel.setForeground(Color.BLACK);
			JLabel stockLabel = new JLabel("Stock:");
			stockLabel.setForeground(Color.BLACK);

			inputPanel.add(nameLabel);
			inputPanel.add(nameField);
			inputPanel.add(categoryLabel);
			inputPanel.add(categoryField);
			inputPanel.add(priceLabel);
			inputPanel.add(priceField);
			inputPanel.add(stockLabel);
			inputPanel.add(stockField);

			int result = JOptionPane.showConfirmDialog(this, inputPanel, "Add New Product",
					JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {
				try {
					String name = nameField.getText().trim();
					String category = categoryField.getText().trim();
					double price = Double.parseDouble(priceField.getText().trim());
					int stock = Integer.parseInt(stockField.getText().trim());

					if (name.isEmpty() || category.isEmpty()) {
						JOptionPane.showMessageDialog(this, "All fields are required!");
						return;
					}

					StoreData.inventory.add(name, category, price, stock);
					productModel.refresh();
					checkLowStock();
					JOptionPane.showMessageDialog(this, "Product added successfully!");

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Invalid number format!");
				}
			}
		});

		editButton.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow < 0) {
				JOptionPane.showMessageDialog(this, "Please select a product to edit!");
				return;
			}

			Product product = productModel.get(selectedRow);

			JTextField nameField = new JTextField(product.name);
			nameField.setForeground(Color.BLACK);
			JTextField categoryField = new JTextField(product.category);
			categoryField.setForeground(Color.BLACK);
			JTextField priceField = new JTextField(String.valueOf(product.price));
			priceField.setForeground(Color.BLACK);
			JTextField stockField = new JTextField(String.valueOf(product.stock));
			stockField.setForeground(Color.BLACK);

			JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
			inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			JLabel nameLabel = new JLabel("Product Name:");
			nameLabel.setForeground(Color.BLACK);
			JLabel categoryLabel = new JLabel("Category:");
			categoryLabel.setForeground(Color.BLACK);
			JLabel priceLabel = new JLabel("Price (TL):");
			priceLabel.setForeground(Color.BLACK);
			JLabel stockLabel = new JLabel("Stock:");
			stockLabel.setForeground(Color.BLACK);

			inputPanel.add(nameLabel);
			inputPanel.add(nameField);
			inputPanel.add(categoryLabel);
			inputPanel.add(categoryField);
			inputPanel.add(priceLabel);
			inputPanel.add(priceField);
			inputPanel.add(stockLabel);
			inputPanel.add(stockField);

			int result = JOptionPane.showConfirmDialog(this, inputPanel, "Edit Product", JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {
				try {
					String name = nameField.getText().trim();
					String category = categoryField.getText().trim();
					double price = Double.parseDouble(priceField.getText().trim());
					int stock = Integer.parseInt(stockField.getText().trim());

					if (name.isEmpty() || category.isEmpty()) {
						JOptionPane.showMessageDialog(this, "All fields are required!");
						return;
					}

					StoreData.inventory.update(product.id, name, category, price, stock);
					productModel.refresh();
					checkLowStock();
					JOptionPane.showMessageDialog(this, "Product updated successfully!");

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Invalid number format!");
				}
			}
		});

		deleteButton.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow < 0) {
				JOptionPane.showMessageDialog(this, "Please select a product to delete!");
				return;
			}

			Product product = productModel.get(selectedRow);

			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete '" + product.name + "'?",
					"Confirm Delete", JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				StoreData.inventory.delete(product.id);
				StoreData.discountManager.removeProductDiscount(product.id); // İndirimi de sil
				productModel.refresh();
				checkLowStock();
				JOptionPane.showMessageDialog(this, "Product deleted successfully!");
			}
		});

		addImageButton.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow < 0) {
				JOptionPane.showMessageDialog(this, "Please select a product first!");
				return;
			}

			Product product = productModel.get(selectedRow);

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Select Product Image");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
				public boolean accept(File f) {
					if (f.isDirectory())
						return true;
					String name = f.getName().toLowerCase();
					return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png")
							|| name.endsWith(".gif") || name.endsWith(".bmp");
				}

				public String getDescription() {
					return "Image Files (*.jpg, *.jpeg, *.png, *.gif, *.bmp)";
				}
			});

			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				try {
					String imageName = DataManager.saveImage(selectedFile, product.id);

					StoreData.inventory.update(product.id, product.name, product.category, product.price, product.stock,
							imageName);

					productModel.refresh();
					JOptionPane.showMessageDialog(this, "Image added successfully for " + product.name + "!", "Success",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (IOException ex) {
					JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		viewOrdersButton.addActionListener(e -> {
			List<Order> orders = StoreData.orderManager.list();
			if (orders.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No orders yet!");
				return;
			}

			StringBuilder sb = new StringBuilder();
			sb.append("ORDER HISTORY\n\n");
			for (Order order : orders) {
				sb.append("Order #").append(order.id).append(" - ").append(String.format("%.2f TL", order.total))
						.append(" - ").append(order.date.toString().replace("T", " ")).append("\n");
			}

			JTextArea textArea = new JTextArea(sb.toString());
			textArea.setEditable(false);
			textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
			textArea.setForeground(Color.BLACK);

			JScrollPane orderScroll = new JScrollPane(textArea);
			orderScroll.setPreferredSize(new Dimension(500, 300));

			JOptionPane.showMessageDialog(this, orderScroll, "Order History", JOptionPane.INFORMATION_MESSAGE);
		});

		lowStockButton.addActionListener(e -> {
			showLowStockAlert();
		});

		setDiscountButton.addActionListener(e -> {
			setProductDiscount();
		});

		discountButton.addActionListener(e -> {
			showDiscountManagementDialog();
		});

		logoutButton.addActionListener(e -> {
			dispose();
			new LoginFrame().setVisible(true);
		});
	}

	private void restoreAbandonedCartItems() {
		List<AbandonedCartItem> abandonedItems = StoreData.abandonedCartManager.getAbandonedItems();
		
		if (abandonedItems.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No abandoned cart items to restore!", "Info",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("<html><div style='font-family: Arial;'>");
		sb.append("<h3 style='color: #dc3545;'>🛒 Abandoned Cart Items</h3>");
		sb.append("<p>The following items were left in carts and can be restored to stock:</p>");
		sb.append("<table border='1' cellpadding='5' style='border-collapse: collapse; width: 100%;'>");
		sb.append("<tr style='background-color: #f8d7da;'>");
		sb.append("<th>Product</th><th>Quantity</th><th>Price</th><th>Abandoned Date</th>");
		sb.append("</tr>");

		for (AbandonedCartItem item : abandonedItems) {
			sb.append("<tr>");
			sb.append("<td>").append(item.productName).append("</td>");
			sb.append("<td align='center'><b>").append(item.quantity).append("</b></td>");
			sb.append("<td>").append(String.format("%.2f TL", item.price)).append("</td>");
			sb.append("<td>").append(item.abandonedDate.format(
					java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("</td>");
			sb.append("</tr>");
		}

		sb.append("</table>");
		sb.append("<p style='margin-top: 15px;'><b>Total items to restore: ").append(abandonedItems.size()).append("</b></p>");
		sb.append("</div></html>");

		int confirm = JOptionPane.showConfirmDialog(this, sb.toString(), 
				"Restore Abandoned Cart Items", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (confirm == JOptionPane.YES_OPTION) {
			StoreData.abandonedCartManager.restoreAbandonedItemsToStock();
			productModel.refresh();
			checkLowStock();
			JOptionPane.showMessageDialog(this, 
					"Successfully restored " + abandonedItems.size() + " items to stock!", 
					"Restoration Complete", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void checkLowStock() {
		List<Product> lowStockProducts = StoreData.inventory.list().stream().filter(p -> p.stock <= 3).toList();

		if (!lowStockProducts.isEmpty()) {
			notificationLabel.setText("⚠️ " + lowStockProducts.size() + " product(s) with low stock!");
			notificationLabel.setForeground(new Color(255, 193, 7));
		} else {
			notificationLabel.setText("✓ All products are in stock");
			notificationLabel.setForeground(new Color(40, 167, 69));
		}
	}

	private void showLowStockAlert() {
		List<Product> lowStockProducts = StoreData.inventory.list().stream().filter(p -> p.stock <= 3).toList();

		if (lowStockProducts.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No products with low stock! All products are adequately stocked.",
					"Stock Status", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		StringBuilder message = new StringBuilder();
		message.append("<html><div style='font-family: Arial;'>");
		message.append("<h3 style='color: #dc3545;'>⚠️ LOW STOCK ALERT</h3>");
		message.append("<p>The following products are running low on stock (≤ 3 units):</p>");
		message.append("<table border='1' cellpadding='5' style='border-collapse: collapse; width: 100%;'>");
		message.append("<tr style='background-color: #f8d7da;'>");
		message.append("<th>Product</th><th>Category</th><th>Current Stock</th><th>Status</th>");
		message.append("</tr>");

		for (Product p : lowStockProducts) {
			String statusColor = p.stock == 0 ? "#dc3545" : p.stock == 1 ? "#ffc107" : "#fd7e14";
			String statusText = p.stock == 0 ? "OUT OF STOCK" : p.stock == 1 ? "CRITICAL" : "LOW";

			message.append("<tr>");
			message.append("<td>").append(p.name).append("</td>");
			message.append("<td>").append(p.category).append("</td>");
			message.append("<td align='center'><b>").append(p.stock).append("</b></td>");
			message.append("<td style='color: ").append(statusColor).append("';><b>").append(statusText)
					.append("</b></td>");
			message.append("</tr>");
		}

		message.append("</table>");
		message.append("<p style='margin-top: 15px;'>Please restock these products as soon as possible.</p>");
		message.append("</div></html>");

		JOptionPane.showMessageDialog(this, message.toString(), "Low Stock Products", JOptionPane.WARNING_MESSAGE);
	}

	private void setProductDiscount() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow < 0) {
			JOptionPane.showMessageDialog(this, "Please select a product first!", "No Selection",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Product product = productModel.get(selectedRow);
		ProductDiscount currentDiscount = StoreData.discountManager.getProductDiscount(product.id);

		JPanel discountPanel = new JPanel(new GridLayout(4, 2, 10, 10));
		discountPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel productLabel = new JLabel("Product:");
		productLabel.setForeground(Color.BLACK);
		JLabel productValue = new JLabel(product.name + " (" + product.id + ")");
		productValue.setForeground(Color.BLACK);

		JLabel discountLabel = new JLabel("Discount Percent (%):");
		discountLabel.setForeground(Color.BLACK);
		JTextField discountField = new JTextField(currentDiscount != null ? 
				String.valueOf(currentDiscount.discountPercent) : "10");
		discountField.setForeground(Color.BLACK);

		JLabel quantityLabel = new JLabel("Minimum Quantity:");
		quantityLabel.setForeground(Color.BLACK);
		JTextField quantityField = new JTextField(currentDiscount != null ? 
				String.valueOf(currentDiscount.minQuantity) : "2");
		quantityField.setForeground(Color.BLACK);

		JLabel activeLabel = new JLabel("Active:");
		activeLabel.setForeground(Color.BLACK);
		JCheckBox activeCheckBox = new JCheckBox("", currentDiscount == null || currentDiscount.active);
		activeCheckBox.setForeground(Color.BLACK);

		discountPanel.add(productLabel);
		discountPanel.add(productValue);
		discountPanel.add(discountLabel);
		discountPanel.add(discountField);
		discountPanel.add(quantityLabel);
		discountPanel.add(quantityField);
		discountPanel.add(activeLabel);
		discountPanel.add(activeCheckBox);

		int result = JOptionPane.showConfirmDialog(this, discountPanel, "Set Product Discount",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			try {
				double discountPercent = Double.parseDouble(discountField.getText().trim());
				int minQuantity = Integer.parseInt(quantityField.getText().trim());
				boolean active = activeCheckBox.isSelected();

				if (discountPercent < 0 || discountPercent > 100) {
					JOptionPane.showMessageDialog(this, "Discount must be between 0-100%!", "Invalid Input",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (minQuantity < 1) {
					JOptionPane.showMessageDialog(this, "Minimum quantity must be at least 1!", "Invalid Input",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Eğer indirim yüzdesi 0 ise indirimi kaldır
				if (discountPercent == 0) {
					StoreData.discountManager.removeProductDiscount(product.id);
					JOptionPane.showMessageDialog(this, "Discount removed for " + product.name, "Success",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					if (currentDiscount != null) {
						StoreData.discountManager.updateDiscount(product.id, discountPercent, minQuantity, active);
					} else {
						StoreData.discountManager.setProductDiscount(product.id, discountPercent, minQuantity);
						if (!active) {
							StoreData.discountManager.updateDiscount(product.id, discountPercent, minQuantity, false);
						}
					}
					JOptionPane.showMessageDialog(this,
							String.format("Discount set: %.0f%% for %d+ units of %s", discountPercent, minQuantity,
									product.name),
							"Success", JOptionPane.INFORMATION_MESSAGE);
				}

				productModel.refresh();
				StoreData.saveDiscounts();

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Please enter valid numbers!", "Invalid Input",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	void showDiscountManagementDialog() {
		JDialog dialog = new JDialog(this, "Discount Management", true);
		dialog.setLayout(new BorderLayout());
		dialog.setSize(700, 600);
		dialog.setLocationRelativeTo(this);
		
		// Başlık paneli
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		headerPanel.setBackground(new Color(138, 43, 226));
		
		JLabel titleLabel = new JLabel("💎 Product Discount Management");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		titleLabel.setForeground(Color.WHITE);
		headerPanel.add(titleLabel, BorderLayout.WEST);
		
		// Ana içerik paneli
		JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		// Üst panel - Toplu indirim ayarları
		JPanel bulkDiscountPanel = new JPanel(new GridLayout(2, 3, 10, 10));
		bulkDiscountPanel.setBorder(BorderFactory.createTitledBorder("Bulk Discount Settings"));
		
		JLabel bulkThresholdLabel = new JLabel("Bulk Discount Threshold (TL):");
		JTextField bulkThresholdField = new JTextField(
				String.valueOf(StoreData.discountManager.getBulkDiscountThreshold()));
		
		JLabel bulkPercentLabel = new JLabel("Bulk Discount Percent (%):");
		JTextField bulkPercentField = new JTextField(
				String.valueOf(StoreData.discountManager.getBulkDiscountPercent()));
		
		JButton saveBulkButton = new JButton("Save Bulk Settings");
		saveBulkButton.setBackground(new Color(40, 167, 69));
		saveBulkButton.setForeground(Color.BLACK);
		saveBulkButton.addActionListener(e -> {
			try {
				double threshold = Double.parseDouble(bulkThresholdField.getText());
				double percent = Double.parseDouble(bulkPercentField.getText());
				
				if (threshold < 0 || percent < 0 || percent > 100) {
					JOptionPane.showMessageDialog(dialog, "Invalid values! Threshold must be >= 0, Percent 0-100",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				StoreData.discountManager.setBulkDiscount(threshold, percent);
				JOptionPane.showMessageDialog(dialog, "Bulk discount settings saved!", "Success",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(dialog, "Please enter valid numbers!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		
		bulkDiscountPanel.add(bulkThresholdLabel);
		bulkDiscountPanel.add(bulkThresholdField);
		bulkDiscountPanel.add(new JLabel());
		bulkDiscountPanel.add(bulkPercentLabel);
		bulkDiscountPanel.add(bulkPercentField);
		bulkDiscountPanel.add(saveBulkButton);
		
		// Orta panel - Ürün indirimleri tablosu
		Map<String, ProductDiscount> allDiscounts = StoreData.discountManager.getAllDiscounts();
		List<Product> allProducts = StoreData.inventory.list();
		
		String[] columnNames = {"Product ID", "Product Name", "Discount %", "Min Qty", "Active", "Actions"};
		Object[][] data = new Object[allDiscounts.size()][6];
		
		int i = 0;
		for (Map.Entry<String, ProductDiscount> entry : allDiscounts.entrySet()) {
			String productId = entry.getKey();
			ProductDiscount discount = entry.getValue();
			Product product = StoreData.inventory.map.get(productId);
			String productName = product != null ? product.name : "Unknown Product";
			
			data[i][0] = productId;
			data[i][1] = productName;
			data[i][2] = discount.discountPercent;
			data[i][3] = discount.minQuantity;
			data[i][4] = discount.active ? "✓" : "✗";
			data[i][5] = "Edit/Remove";
			i++;
		}
		
		JTable discountsTable = new JTable(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 5; // Sadece Actions sütunu editable
			}
		};
		discountsTable.setRowHeight(30);
		
		discountsTable.getColumnModel().getColumn(5).setCellRenderer(new DiscountButtonRenderer());
		discountsTable.getColumnModel().getColumn(5).setCellEditor(new DiscountButtonEditor(new JCheckBox(), dialog, discountsTable));
		
		JScrollPane tableScroll = new JScrollPane(discountsTable);
		tableScroll.setBorder(BorderFactory.createTitledBorder("Product Discounts"));
		
		// Alt panel - İstatistikler
		JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
		statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
		
		int totalProducts = allProducts.size();
		int discountedProducts = allDiscounts.size();
		int activeDiscounts = (int) allDiscounts.values().stream().filter(d -> d.active).count();
		
		statsPanel.add(createStatCard("Total Products", String.valueOf(totalProducts), Color.BLUE));
		statsPanel.add(createStatCard("Discounted Products", String.valueOf(discountedProducts), Color.GREEN));
		statsPanel.add(createStatCard("Active Discounts", String.valueOf(activeDiscounts), new Color(255, 193, 7)));
		statsPanel.add(createStatCard("Discount Coverage", 
				String.format("%.1f%%", (discountedProducts * 100.0 / totalProducts)), Color.MAGENTA));
		
		// Buton paneli
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(e -> dialog.dispose());
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(e -> {
			dialog.dispose();
			showDiscountManagementDialog();
		});
		
		buttonPanel.add(refreshButton);
		buttonPanel.add(closeButton);
		
		// Bileşenleri ekle
		contentPanel.add(bulkDiscountPanel, BorderLayout.NORTH);
		contentPanel.add(tableScroll, BorderLayout.CENTER);
		contentPanel.add(statsPanel, BorderLayout.SOUTH);
		
		dialog.add(headerPanel, BorderLayout.NORTH);
		dialog.add(contentPanel, BorderLayout.CENTER);
		dialog.add(buttonPanel, BorderLayout.SOUTH);
		
		dialog.setVisible(true);
	}

	private JPanel createStatCard(String title, String value, Color color) {
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(color, 1),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)
		));
		
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel valueLabel = new JLabel(value);
		valueLabel.setFont(new Font("Arial", Font.BOLD, 16));
		valueLabel.setForeground(color);
		valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		card.add(titleLabel);
		card.add(Box.createVerticalStrut(5));
		card.add(valueLabel);
		
		return card;
	}
}