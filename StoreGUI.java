package OnlineStoreApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class StoreGUI extends JFrame {
	Cart cart = new Cart();
	ProductTableModel productModel = new ProductTableModel();
	CartTableModel cartModel = new CartTableModel();
	JTable cTable;

	JLabel sub = new JLabel(), disc = new JLabel(), total = new JLabel();
	JButton logoutButton;
	JLabel cartInfo;

	StoreGUI(User u) {
		setTitle("MyAmazon - Welcome " + u.username);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Pencere kapatıldığında sepeti kaydet
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				saveCartBeforeExit();
			}
		});

		Color AMAZON_BLACK = new Color(19, 25, 33);
		Color AMAZON_ORANGE = new Color(255, 153, 0);
		Color AMAZON_YELLOW = new Color(247, 202, 0);
		Color AMAZON_LIGHT_BLUE = new Color(234, 237, 237);
		Color AMAZON_DARK_ORANGE = new Color(240, 136, 4);
		Color AMAZON_WHITE = new Color(255, 255, 255);
		Font FONT_MAIN = new Font("Arial", Font.PLAIN, 14);
		Font FONT_BOLD = new Font("Arial", Font.BOLD, 14);
		Font FONT_TITLE = new Font("Arial", Font.BOLD, 18);
		Font FONT_SMALL = new Font("Arial", Font.PLAIN, 12);

		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(AMAZON_BLACK);
		header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		logoPanel.setBackground(AMAZON_BLACK);
		JLabel logo = new JLabel("MyAmazon");
		logo.setForeground(Color.WHITE);
		logo.setFont(new Font("Arial", Font.BOLD, 28));
		logoPanel.add(logo);

		JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		userPanel.setBackground(AMAZON_BLACK);

		JLabel userLabel = new JLabel("Hello, " + u.username);
		userLabel.setForeground(Color.WHITE);
		userLabel.setFont(FONT_BOLD);
		userPanel.add(userLabel);
		userPanel.add(Box.createHorizontalStrut(20));

		logoutButton = new JButton("Logout");
		logoutButton.setFont(FONT_BOLD);
		logoutButton.setBackground(new Color(220, 53, 69));
		logoutButton.setForeground(Color.BLACK);
		logoutButton.setFocusPainted(false);
		logoutButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
		userPanel.add(logoutButton);

		header.add(logoPanel, BorderLayout.WEST);
		header.add(userPanel, BorderLayout.EAST);
		add(header, BorderLayout.NORTH);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(AMAZON_LIGHT_BLUE);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel productsHeader = new JPanel(new BorderLayout());
		productsHeader.setBackground(AMAZON_WHITE);
		productsHeader.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, AMAZON_ORANGE),
						BorderFactory.createEmptyBorder(10, 15, 10, 15)));

		JLabel productsTitle = new JLabel("Available Products");
		productsTitle.setFont(FONT_TITLE);
		productsTitle.setForeground(Color.BLACK);
		productsHeader.add(productsTitle, BorderLayout.WEST);

		JLabel stockInfo = new JLabel("Click 'Add to Cart' to purchase");
		stockInfo.setForeground(Color.BLACK);
		stockInfo.setFont(FONT_MAIN);
		productsHeader.add(stockInfo, BorderLayout.EAST);

		JTable pTable = new JTable(productModel);
		pTable.setRowHeight(100);
		pTable.setFont(FONT_MAIN);
		pTable.setForeground(Color.BLACK);
		pTable.setGridColor(new Color(230, 230, 230));
		pTable.getTableHeader().setBackground(AMAZON_ORANGE);
		pTable.getTableHeader().setForeground(Color.BLACK);
		pTable.getTableHeader().setFont(FONT_BOLD);
		pTable.getTableHeader().setPreferredSize(new Dimension(0, 40));

		pTable.getColumnModel().getColumn(0).setCellRenderer(new ClickableImageRenderer(this));

		JScrollPane productsScroll = new JScrollPane(pTable);
		productsScroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		productsScroll.getViewport().setBackground(AMAZON_WHITE);

		JPanel productsPanel = new JPanel(new BorderLayout());
		productsPanel.setBackground(AMAZON_WHITE);
		productsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		productsPanel.add(productsHeader, BorderLayout.NORTH);
		productsPanel.add(productsScroll, BorderLayout.CENTER);

		JPanel cartHeader = new JPanel(new BorderLayout());
		cartHeader.setBackground(AMAZON_WHITE);
		cartHeader.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, AMAZON_YELLOW),
						BorderFactory.createEmptyBorder(10, 15, 10, 15)));

		JLabel cartTitle = new JLabel("Your Shopping Cart");
		cartTitle.setFont(FONT_TITLE);
		cartTitle.setForeground(Color.BLACK);
		cartHeader.add(cartTitle, BorderLayout.WEST);

		cartInfo = new JLabel("Items: " + cart.list().size() + " | Total: " + String.format("%.2f TL", cart.total()));
		cartInfo.setForeground(Color.BLACK);
		cartInfo.setFont(FONT_MAIN);
		cartHeader.add(cartInfo, BorderLayout.EAST);

		cTable = new JTable(cartModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 7;
			}
		};

		// DÜZELTME: CartButtonRenderer ve CartButtonEditor'ı düzgün şekilde bağla
		cTable.getColumnModel().getColumn(7).setCellRenderer(new CartButtonRenderer());
		cTable.getColumnModel().getColumn(7).setCellEditor(new CartButtonEditor(new JCheckBox(), this));

		cTable.setRowHeight(70); // Daha küçük satır yüksekliği
		cTable.setFont(FONT_MAIN);
		cTable.setForeground(Color.BLACK);
		cTable.setGridColor(new Color(230, 230, 230));
		cTable.getTableHeader().setBackground(AMAZON_YELLOW);
		cTable.getTableHeader().setForeground(Color.BLACK);
		cTable.getTableHeader().setFont(FONT_BOLD);
		cTable.getTableHeader().setPreferredSize(new Dimension(0, 40));

		JScrollPane cartScroll = new JScrollPane(cTable);
		cartScroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		cartScroll.getViewport().setBackground(AMAZON_WHITE);
		cartScroll.setPreferredSize(new Dimension(500, 0));

		JPanel summaryPanel = new JPanel();
		summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
		summaryPanel.setBackground(AMAZON_WHITE);
		summaryPanel.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, AMAZON_YELLOW),
						BorderFactory.createEmptyBorder(15, 15, 15, 15)));

		JLabel summaryTitle = new JLabel("Order Summary");
		summaryTitle.setFont(FONT_BOLD);
		summaryTitle.setForeground(Color.BLACK);
		summaryTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

		sub.setFont(FONT_MAIN);
		sub.setForeground(Color.BLACK);
		sub.setAlignmentX(Component.LEFT_ALIGNMENT);

		disc.setFont(FONT_MAIN);
		disc.setForeground(Color.BLACK);
		disc.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
		totalPanel.setBackground(AMAZON_WHITE);
		totalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		total.setFont(new Font("Arial", Font.BOLD, 16));
		total.setForeground(Color.BLACK);
		totalPanel.add(total);

		// İndirim bilgileri paneli
		JPanel discountInfoPanel = new JPanel();
		discountInfoPanel.setLayout(new BoxLayout(discountInfoPanel, BoxLayout.Y_AXIS));
		discountInfoPanel.setBackground(new Color(255, 248, 225));
		discountInfoPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(255, 193, 7), 1),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		discountInfoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel discountTitle = new JLabel("📢 Current Discounts Available:");
		discountTitle.setFont(new Font("Arial", Font.BOLD, 12));
		discountTitle.setForeground(new Color(133, 100, 4));
		discountTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Dinamik indirim bilgileri
		JLabel bulkDiscountInfo = new JLabel("• Order total " + 
				String.format("%.0f", StoreData.discountManager.getBulkDiscountThreshold()) + 
				"+ TL: Additional " + 
				String.format("%.0f", StoreData.discountManager.getBulkDiscountPercent()) + "% off");
		bulkDiscountInfo.setFont(FONT_SMALL);
		bulkDiscountInfo.setForeground(Color.BLACK);
		bulkDiscountInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

		discountInfoPanel.add(discountTitle);
		discountInfoPanel.add(Box.createVerticalStrut(5));
		discountInfoPanel.add(bulkDiscountInfo);

		summaryPanel.add(summaryTitle);
		summaryPanel.add(Box.createVerticalStrut(15));
		summaryPanel.add(sub);
		summaryPanel.add(Box.createVerticalStrut(5));
		summaryPanel.add(disc);
		summaryPanel.add(Box.createVerticalStrut(15));
		summaryPanel.add(totalPanel);
		summaryPanel.add(Box.createVerticalStrut(20));

		summaryPanel.add(discountInfoPanel);
		summaryPanel.add(Box.createVerticalStrut(15));

		JButton removeButton = new JButton("Remove Selected");
		removeButton.setFont(FONT_BOLD);
		removeButton.setBackground(new Color(220, 53, 69));
		removeButton.setForeground(Color.BLACK);
		removeButton.setFocusPainted(false);
		removeButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		removeButton.setAlignmentX(Component.LEFT_ALIGNMENT);

		JButton checkoutButton = new JButton("Proceed to Checkout");
		checkoutButton.setFont(FONT_BOLD);
		checkoutButton.setBackground(AMAZON_DARK_ORANGE);
		checkoutButton.setForeground(Color.BLACK);
		checkoutButton.setFocusPainted(false);
		checkoutButton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
		checkoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);

		JButton clearCartButton = new JButton("Clear Cart");
		clearCartButton.setFont(FONT_BOLD);
		clearCartButton.setBackground(new Color(108, 117, 125));
		clearCartButton.setForeground(Color.BLACK);
		clearCartButton.setFocusPainted(false);
		clearCartButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		clearCartButton.setAlignmentX(Component.LEFT_ALIGNMENT);

		summaryPanel.add(removeButton);
		summaryPanel.add(Box.createVerticalStrut(10));
		summaryPanel.add(clearCartButton);
		summaryPanel.add(Box.createVerticalStrut(10));
		summaryPanel.add(checkoutButton);

		JPanel cartPanel = new JPanel(new BorderLayout());
		cartPanel.setBackground(AMAZON_WHITE);
		cartPanel.add(cartHeader, BorderLayout.NORTH);
		cartPanel.add(cartScroll, BorderLayout.CENTER);
		cartPanel.add(summaryPanel, BorderLayout.SOUTH);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, productsPanel, cartPanel);
		splitPane.setResizeWeight(0.65);
		splitPane.setDividerSize(4);
		splitPane.setBackground(AMAZON_LIGHT_BLUE);
		splitPane.setBorder(BorderFactory.createEmptyBorder());

		mainPanel.add(splitPane, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(AMAZON_LIGHT_BLUE);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

		JButton addButton = new JButton("Add to Cart");
		addButton.setFont(FONT_BOLD);
		addButton.setBackground(AMAZON_ORANGE);
		addButton.setForeground(Color.BLACK);
		addButton.setFocusPainted(false);
		addButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(AMAZON_DARK_ORANGE, 1),
				BorderFactory.createEmptyBorder(10, 25, 10, 25)));

		JButton quickAddButton = new JButton("Quick Add (1 item)");
		quickAddButton.setFont(FONT_BOLD);
		quickAddButton.setBackground(new Color(40, 167, 69));
		quickAddButton.setForeground(Color.BLACK);
		quickAddButton.setFocusPainted(false);
		quickAddButton
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(40, 167, 69), 1),
						BorderFactory.createEmptyBorder(10, 25, 10, 25)));

		bottomPanel.add(addButton);
		bottomPanel.add(Box.createHorizontalStrut(10));
		bottomPanel.add(quickAddButton);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		add(mainPanel, BorderLayout.CENTER);

		productModel.refresh();

		addButton.addActionListener(e -> {
			int r = pTable.getSelectedRow();
			if (r < 0) {
				JOptionPane.showMessageDialog(this, "Please select a product first!", "No Selection",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			Product p = productModel.get(r);

			JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
			inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			JLabel quantityLabel = new JLabel("Quantity:");
			quantityLabel.setForeground(Color.BLACK);
			JTextField quantityField = new JTextField("1");
			quantityField.setForeground(Color.BLACK);

			JLabel stockLabel = new JLabel("Available stock: " + p.stock);
			stockLabel.setForeground(Color.BLACK);
			JLabel productLabel = new JLabel("Product: " + p.name);
			productLabel.setForeground(Color.BLACK);

			// Dinamik indirim bilgisi
			String discountInfo = getDiscountDescription(p);
			JLabel discountLabel = new JLabel("Discount: " + discountInfo);
			discountLabel.setForeground(new Color(40, 167, 69));
			JLabel priceLabel = new JLabel("Price: " + String.format("%.2f TL", p.price));
			priceLabel.setForeground(Color.BLACK);

			inputPanel.add(productLabel);
			inputPanel.add(priceLabel);
			inputPanel.add(stockLabel);
			inputPanel.add(discountLabel);
			inputPanel.add(quantityLabel);
			inputPanel.add(quantityField);

			int result = JOptionPane.showConfirmDialog(this, inputPanel, "Add to Cart", JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {
				try {
					int q = Integer.parseInt(quantityField.getText().trim());
					if (q <= 0) {
						JOptionPane.showMessageDialog(this, "Quantity must be positive!", "Invalid Input",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (p.stock < q) {
						JOptionPane.showMessageDialog(this, "Insufficient stock! Only " + p.stock + " available.",
								"Stock Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					p.stock -= q;
					cart.add(p, q);
					StoreData.saveInventory();
					refresh();

					updateCartInfo();

					// İndirim uygulanıp uygulanmadığını göster
					CartItem item = cart.items.get(p.id);
					if (item.d > 0) {
						JOptionPane.showMessageDialog(this,
								String.format("✓ Discount applied! You saved %.2f TL", item.discount()),
								"Discount Applied", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Please enter a valid number!", "Invalid Input",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		quickAddButton.addActionListener(e -> {
			int r = pTable.getSelectedRow();
			if (r < 0) {
				JOptionPane.showMessageDialog(this, "Please select a product first!", "No Selection",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			Product p = productModel.get(r);

			if (p.stock > 0) {
				p.stock--;
				cart.add(p, 1);
				StoreData.saveInventory();
				refresh();
				updateCartInfo();

				JOptionPane.showMessageDialog(this, "1 x " + p.name + " added to cart!", "Quick Add",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Insufficient stock for " + p.name + "!", "Stock Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		removeButton.addActionListener(e -> {
			int r = cTable.getSelectedRow();
			if (r < 0) {
				JOptionPane.showMessageDialog(this, "Please select an item to remove!", "No Selection",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			int confirm = JOptionPane.showConfirmDialog(this, "Remove " + cartModel.get(r).p.name + " from cart?",
					"Confirm Removal", JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				cart.remove(cartModel.get(r).p);
				refresh();
				updateCartInfo();
			}
		});

		clearCartButton.addActionListener(e -> {
			if (cart.empty()) {
				JOptionPane.showMessageDialog(this, "Your cart is already empty!", "Empty Cart", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			int confirm = JOptionPane.showConfirmDialog(this, 
					"Clear the entire cart? Items will be returned to stock.",
					"Clear Cart", JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				cart.clear();
				refresh();
				updateCartInfo();
				JOptionPane.showMessageDialog(this, "Cart cleared! Items returned to stock.", 
						"Cart Cleared", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		checkoutButton.addActionListener(e -> {
			if (cart.empty()) {
				JOptionPane.showMessageDialog(this, "Your cart is empty!", "Empty Cart", JOptionPane.WARNING_MESSAGE);
				return;
			}

			JPanel confirmPanel = new JPanel(new BorderLayout());
			confirmPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			double subtotal = cart.subtotal();
			double totalDiscount = cart.discount();
			double finalTotal = cart.total();
			boolean bulkDiscount = cart.hasBulkDiscount();
			double bulkPercent = StoreData.discountManager.getBulkDiscountPercent();

			JLabel confirmLabel = new JLabel("<html><div style='text-align: center;'>"
					+ "<h3>Order Confirmation</h3>"
					+ "Complete order for <b>" + String.format("%.2f TL", finalTotal) + "</b>?<br><br>"
					+ "Items in cart: " + cart.list().size() + "<br>"
					+ "Subtotal: " + String.format("%.2f TL", subtotal) + "<br>"
					+ "Discounts: -" + String.format("%.2f TL", totalDiscount) + "<br>"
					+ (bulkDiscount ? "<font color='green'>✓ Additional " + bulkPercent + "% bulk discount applied!</font><br>" : "")
					+ "</div></html>");
			confirmLabel.setForeground(Color.BLACK);
			confirmLabel.setHorizontalAlignment(SwingConstants.CENTER);

			confirmPanel.add(confirmLabel, BorderLayout.CENTER);

			int confirm = JOptionPane.showConfirmDialog(this, confirmPanel, "Confirm Order", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {
				StoreData.orderManager.create(cart.total());
				cart.clear();
				refresh();
				updateCartInfo();

				JOptionPane.showMessageDialog(this,
						"Order completed successfully!\nThank you for shopping with MyAmazon.", "Order Complete",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		logoutButton.addActionListener(e -> {
			saveCartBeforeExit();
			dispose();
			new LoginFrame().setVisible(true);
		});

		pTable.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && pTable.getSelectedRow() >= 0) {
				Product p = productModel.get(pTable.getSelectedRow());
				addButton.setToolTipText("Add " + p.name + " to cart");
				quickAddButton.setToolTipText("Quickly add 1 " + p.name + " to cart");
			}
		});

		refresh();
		updateCartInfo();
	}

	void refresh() {
		productModel.refresh();
		cartModel.refresh(cart);

		double subtotal = cart.subtotal();
		double discount = cart.discount();
		double finalTotal = cart.total();
		boolean bulkDiscount = cart.hasBulkDiscount();
		double bulkPercent = StoreData.discountManager.getBulkDiscountPercent();

		sub.setText("Subtotal: " + String.format("%.2f TL", subtotal));
		disc.setText("Discount: -" + String.format("%.2f TL", discount)
				+ (bulkDiscount ? String.format(" (incl. %.0f%% bulk discount)", bulkPercent) : ""));
		total.setText("Total: " + String.format("%.2f TL", finalTotal));

		if (discount > 0 || bulkDiscount) {
			disc.setForeground(new Color(220, 53, 69));
		} else {
			disc.setForeground(Color.BLACK);
		}

		total.setForeground(bulkDiscount ? new Color(40, 167, 69) : Color.BLACK);
	}

	void updateCartInfo() {
		int totalItems = 0;
		for (CartItem item : cart.list()) {
			totalItems += item.q;
		}
		cartInfo.setText("Items: " + totalItems + " | Total: " + String.format("%.2f TL", cart.total()));
	}

	void incrementCartItem(int row) {
		CartItem item = cartModel.get(row);
		if (item.p.stock > 0) {
			cart.incrementQuantity(item.p);
			refresh();
			updateCartInfo();
		} else {
			JOptionPane.showMessageDialog(this, "Insufficient stock for " + item.p.name + "!", "Stock Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	void decrementCartItem(int row) {
		CartItem item = cartModel.get(row);
		cart.decrementQuantity(item.p);
		refresh();
		updateCartInfo();
	}

	// Dinamik indirim açıklamasını döndüren yardımcı metod
	private String getDiscountDescription(Product p) {
		ProductDiscount discount = StoreData.discountManager.getProductDiscount(p.id);
		if (discount != null && discount.active) {
			return String.format("Ürün indirimi: %.0f%% (%d+ adet)", discount.discountPercent, discount.minQuantity);
		}
		
		// Eski kategori bazlı indirimler
		return switch (p.category) {
		case "Electronics" -> "10% off for 2+ items";
		case "Stationery" -> "15% off for 5+ items";
		case "Accessories" -> "5% off for 3+ items";
		default -> "No discount";
		};
	}

	// Çıkış yapmadan önce sepeti kaydet
	private void saveCartBeforeExit() {
		if (!cart.empty()) {
			// Sepetteki ürünleri terk edilmiş sepet kaydına ekle
			for (CartItem item : cart.list()) {
				StoreData.abandonedCartManager.addAbandonedItem(
					item.p.id, 
					item.p.name, 
					item.q, 
					item.p.price
				);
				// Ürünleri stoklara geri ekle
				item.p.stock += item.q;
			}
			StoreData.saveInventory();
			System.out.println("Cart saved as abandoned. Items returned to stock.");
		}
	}
}
