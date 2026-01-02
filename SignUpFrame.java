package OnlineStoreApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

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

class SignUpFrame extends JFrame {

	SignUpFrame() {
		setTitle("MyAmazon - Create Account");
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);

		Color AMAZON_BLACK = new Color(19, 25, 33);
		Color AMAZON_ORANGE = new Color(255, 153, 0);
		Color AMAZON_LIGHT_BLUE = new Color(234, 237, 237);
		Color AMAZON_DARK_ORANGE = new Color(240, 136, 4);
		Color WHITE = Color.WHITE;
		Color GRAY = new Color(118, 118, 118);
		Color DARK_GRAY = new Color(60, 60, 60);
		Color LINK_BLUE = new Color(0, 102, 204);
		Color ERROR_RED = new Color(220, 53, 69);

		Font TITLE_FONT = new Font("Arial", Font.BOLD, 32);
		Font LABEL_FONT = new Font("Arial", Font.PLAIN, 16);
		Font INPUT_FONT = new Font("Arial", Font.PLAIN, 16);
		Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);
		Font LINK_FONT = new Font("Arial", Font.PLAIN, 14);
		Font SMALL_FONT = new Font("Arial", Font.PLAIN, 12);

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

		JLabel welcomeLabel = new JLabel("Create Account");
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
						BorderFactory.createEmptyBorder(40, 50, 40, 50)));

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
		formPanel.add(Box.createVerticalStrut(20));

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
		formPanel.add(Box.createVerticalStrut(20));

		JPanel confirmPasswordPanel = new JPanel(new BorderLayout(0, 10));
		confirmPasswordPanel.setBackground(WHITE);
		confirmPasswordPanel.setMaximumSize(new Dimension(400, 80));

		JLabel confirmPasswordLabel = new JLabel("Confirm Password");
		confirmPasswordLabel.setFont(LABEL_FONT);
		confirmPasswordLabel.setForeground(DARK_GRAY);

		JPasswordField confirmPasswordField = new JPasswordField();
		confirmPasswordField.setFont(INPUT_FONT);
		confirmPasswordField.setForeground(Color.BLACK);
		confirmPasswordField.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
						BorderFactory.createEmptyBorder(12, 12, 12, 12)));

		confirmPasswordPanel.add(confirmPasswordLabel, BorderLayout.NORTH);
		confirmPasswordPanel.add(confirmPasswordField, BorderLayout.CENTER);

		formPanel.add(confirmPasswordPanel);
		formPanel.add(Box.createVerticalStrut(15));

		JLabel passwordRequirements = new JLabel(
				"<html><div style='font-size:12px; color:#666;'>" + "• Password must be at least 4 characters<br>"
						+ "• Use a combination of letters and numbers for better security" + "</div></html>");
		passwordRequirements.setFont(SMALL_FONT);
		passwordRequirements.setAlignmentX(Component.LEFT_ALIGNMENT);
		formPanel.add(passwordRequirements);
		formPanel.add(Box.createVerticalStrut(25));

		JButton signupButton = new JButton("Create Account");
		signupButton.setFont(BUTTON_FONT);
		signupButton.setBackground(AMAZON_ORANGE);
		signupButton.setForeground(Color.BLACK);
		signupButton.setFocusPainted(false);
		signupButton.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
		signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		signupButton.setMaximumSize(new Dimension(400, 55));
		signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		signupButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				signupButton.setBackground(AMAZON_DARK_ORANGE);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				signupButton.setBackground(AMAZON_ORANGE);
			}
		});

		formPanel.add(signupButton);
		formPanel.add(Box.createVerticalStrut(20));

		JPanel signinPanel = new JPanel();
		signinPanel.setBackground(WHITE);
		signinPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel signinLabel = new JLabel("Already have an account? ");
		signinLabel.setFont(LINK_FONT);
		signinLabel.setForeground(GRAY);

		JButton signinLink = new JButton("Sign in");
		signinLink.setFont(LINK_FONT);
		signinLink.setForeground(LINK_BLUE);
		signinLink.setBackground(WHITE);
		signinLink.setBorder(BorderFactory.createEmptyBorder());
		signinLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		signinLink.setFocusPainted(false);
		signinLink.setContentAreaFilled(false);

		signinPanel.add(signinLabel);
		signinPanel.add(signinLink);

		formPanel.add(signinPanel);
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

		signupButton.addActionListener(e -> {
			String username = usernameField.getText().trim();
			String password = new String(passwordField.getPassword());
			String confirmPassword = new String(confirmPasswordField.getPassword());

			if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
				JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (username.length() < 3) {
				JOptionPane.showMessageDialog(this, "Username must be at least 3 characters!", "Validation Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (password.length() < 4) {
				JOptionPane.showMessageDialog(this, "Password must be at least 4 characters!", "Validation Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!password.equals(confirmPassword)) {
				JOptionPane.showMessageDialog(this, "Passwords do not match!", "Validation Error",
						JOptionPane.ERROR_MESSAGE);
				confirmPasswordField.setText("");
				confirmPasswordField.requestFocus();
				return;
			}

			boolean success = DataManager.addUser(username, password, Role.USER);

			if (success) {
				JOptionPane.showMessageDialog(this,
						"<html><div style='text-align: center;'><b>Account Created Successfully!</b><br>"
								+ "You can now log in with your new account.</div></html>",
						"Success", JOptionPane.INFORMATION_MESSAGE);

				dispose();
				new LoginFrame().setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Username '" + username + "' is already taken!",
						"Registration Failed", JOptionPane.ERROR_MESSAGE);
				usernameField.setText("");
				usernameField.requestFocus();
			}
		});

		signinLink.addActionListener(e -> {
			dispose();
			new LoginFrame().setVisible(true);
		});

		ActionListener signupAction = ev -> signupButton.doClick();
		usernameField.addActionListener(signupAction);
		passwordField.addActionListener(signupAction);
		confirmPasswordField.addActionListener(signupAction);

		SwingUtilities.invokeLater(() -> usernameField.requestFocus());
	}
}

