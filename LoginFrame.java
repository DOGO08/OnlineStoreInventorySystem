package OnlineStoreApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/* ===================== LOGIN FRAME ===================== */
class LoginFrame extends JFrame {
	Map<String, User> users;

	LoginFrame() {
		setTitle("MyAmazon - Login");
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		Color AMAZON_BLACK = new Color(19, 25, 33);
		Color AMAZON_ORANGE = new Color(255, 153, 0);
		Color AMAZON_LIGHT_BLUE = new Color(234, 237, 237);
		Color AMAZON_DARK_ORANGE = new Color(240, 136, 4);
		Color WHITE = Color.WHITE;
		Color GRAY = new Color(118, 118, 118);
		Color DARK_GRAY = new Color(60, 60, 60);
		Color LINK_BLUE = new Color(0, 102, 204);

		Font TITLE_FONT = new Font("Arial", Font.BOLD, 32);
		Font LABEL_FONT = new Font("Arial", Font.PLAIN, 16);
		Font INPUT_FONT = new Font("Arial", Font.PLAIN, 16);
		Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);
		Font LINK_FONT = new Font("Arial", Font.PLAIN, 14);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(AMAZON_LIGHT_BLUE);
		add(mainPanel);

		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(AMAZON_BLACK);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

		JLabel logoLabel = new JLabel("MyAmazon");
		logoLabel.setFont(TITLE_FONT);
		logoLabel.setForeground(WHITE);
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel welcomeLabel = new JLabel("Welcome Back");
		welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		welcomeLabel.setForeground(new Color(200, 200, 200));
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		headerPanel.add(logoLabel);
		headerPanel.add(Box.createVerticalStrut(15));
		headerPanel.add(welcomeLabel);

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setBackground(WHITE);
		formPanel.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(221, 221, 221), 1),
						BorderFactory.createEmptyBorder(50, 50, 40, 50)));

		JPanel usernamePanel = new JPanel(new BorderLayout(0, 10));
		usernamePanel.setBackground(WHITE);
		usernamePanel.setMaximumSize(new Dimension(400, 80));

		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(LABEL_FONT);
		usernameLabel.setForeground(DARK_GRAY);

		JTextField usernameField = new JTextField();
		usernameField.setFont(INPUT_FONT);
		usernameField.setForeground(Color.BLACK);
		usernameField.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
						BorderFactory.createEmptyBorder(12, 12, 12, 12)));

		usernamePanel.add(usernameLabel, BorderLayout.NORTH);
		usernamePanel.add(usernameField, BorderLayout.CENTER);

		formPanel.add(usernamePanel);
		formPanel.add(Box.createVerticalStrut(25));

		JPanel passwordPanel = new JPanel(new BorderLayout(0, 10));
		passwordPanel.setBackground(WHITE);
		passwordPanel.setMaximumSize(new Dimension(400, 80));

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(LABEL_FONT);
		passwordLabel.setForeground(DARK_GRAY);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setFont(INPUT_FONT);
		passwordField.setForeground(Color.BLACK);
		passwordField.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
						BorderFactory.createEmptyBorder(12, 12, 12, 12)));

		passwordPanel.add(passwordLabel, BorderLayout.NORTH);
		passwordPanel.add(passwordField, BorderLayout.CENTER);

		formPanel.add(passwordPanel);
		formPanel.add(Box.createVerticalStrut(30));

		JButton loginButton = new JButton("Sign In");
		loginButton.setFont(BUTTON_FONT);
		loginButton.setBackground(AMAZON_ORANGE);
		loginButton.setForeground(Color.BLACK);
		loginButton.setFocusPainted(false);
		loginButton.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		loginButton.setMaximumSize(new Dimension(400, 55));
		loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				loginButton.setBackground(AMAZON_DARK_ORANGE);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				loginButton.setBackground(AMAZON_ORANGE);
			}
		});

		formPanel.add(loginButton);
		formPanel.add(Box.createVerticalStrut(15));

		JPanel signupPanel = new JPanel();
		signupPanel.setBackground(WHITE);
		signupPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel signupLabel = new JLabel("New to MyAmazon? ");
		signupLabel.setFont(LINK_FONT);
		signupLabel.setForeground(GRAY);

		JButton signupLink = new JButton("Create your account");
		signupLink.setFont(LINK_FONT);
		signupLink.setForeground(LINK_BLUE);
		signupLink.setBackground(WHITE);
		signupLink.setBorder(BorderFactory.createEmptyBorder());
		signupLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		signupLink.setFocusPainted(false);
		signupLink.setContentAreaFilled(false);

		signupPanel.add(signupLabel);
		signupPanel.add(signupLink);

		formPanel.add(signupPanel);
		formPanel.add(Box.createVerticalStrut(30));

		JPanel demoPanel = new JPanel();
		demoPanel.setLayout(new BoxLayout(demoPanel, BoxLayout.Y_AXIS));
		demoPanel.setBackground(WHITE);
		demoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel demoLabel = new JLabel("Demo Accounts:");
		demoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		demoLabel.setForeground(GRAY);
		demoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel adminLabel = new JLabel("Admin: admin / 1234");
		adminLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		adminLabel.setForeground(GRAY);
		adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel userLabel = new JLabel("User: user / 1234");
		userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		userLabel.setForeground(GRAY);
		userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		demoPanel.add(demoLabel);
		demoPanel.add(Box.createVerticalStrut(8));
		demoPanel.add(adminLabel);
		demoPanel.add(Box.createVerticalStrut(4));
		demoPanel.add(userLabel);

		formPanel.add(demoPanel);
		formPanel.add(Box.createVerticalStrut(15));

		JPanel footerPanel = new JPanel();
		footerPanel.setBackground(WHITE);
		footerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel footerLabel = new JLabel("© 2024 MyAmazon Clone");
		footerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		footerLabel.setForeground(GRAY);
		footerPanel.add(footerLabel);

		formPanel.add(footerPanel);

		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(formPanel, BorderLayout.CENTER);

		users = DataManager.loadUsers();

		ActionListener loginAction = ev -> {
			User user = users.get(usernameField.getText());
			if (user != null && user.password.equals(new String(passwordField.getPassword()))) {
				dispose();
				if (user.role == Role.ADMIN)
					new AdminFrame().setVisible(true);
				else
					new StoreGUI(user).setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this,
						"<html><div style='text-align: center;'><b>Login Failed</b><br>Invalid username or password</div></html>",
						"Authentication Error", JOptionPane.ERROR_MESSAGE);

				passwordField.setText("");
				passwordField.requestFocus();
			}
		};

		loginButton.addActionListener(loginAction);
		usernameField.addActionListener(loginAction);
		passwordField.addActionListener(loginAction);

		signupLink.addActionListener(e -> {
			dispose();
			new SignUpFrame().setVisible(true);
		});

		SwingUtilities.invokeLater(() -> usernameField.requestFocus());
	}
}

