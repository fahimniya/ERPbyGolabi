package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import user_management.UserWrapper;

public class LoginView implements View {
	private JFrame loginFrame;
	private JLabel systemName;
	private JLabel message;
	private JLabel usernameLabel;
	private JTextField username;
	private JLabel passwordLabel;
	private JPasswordField password;
	private JButton login;
	
	public LoginView() {
		loginFrame = new JFrame();
		loginFrame.setBounds(200, 150, 400, 300);
		
		systemName = new JLabel("سامانه‌ی برنامه‌ریزی برای منابع انسانی", SwingConstants.CENTER);
		systemName.setBounds(0, 10, 400, 50);
		systemName.setFont(new Font(systemName.getFont().getName(), Font.PLAIN, 18));
		loginFrame.add(systemName);
		
		message = new JLabel("", SwingConstants.CENTER);
		message.setBounds(0, 60, 400, 30);
		loginFrame.add(message);
		
		usernameLabel = new JLabel();
		usernameLabel.setText("نام کاربری:");
		usernameLabel.setBounds(280, 90, 55, 30);
		loginFrame.add(usernameLabel);
		username = new JTextField();
		username.setBounds(70, 90, 190, 30);
		loginFrame.add(username);
		
		passwordLabel = new JLabel();
		passwordLabel.setText("کلمه‌ی عبور:");
		passwordLabel.setBounds(280, 140, 55, 30);
		loginFrame.add(passwordLabel);
		password = new JPasswordField();
		password.setBounds(70, 140, 190, 30);
		loginFrame.add(password);
		
		login = new JButton("ورود");
		login.setBounds(115, 190, 100, 40);
		final LoginView lv = this;
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserWrapper userWrapper = new UserWrapper();
				System.out.println("user: " + username.getText() + ", password: " + password.getPassword());
				boolean isValid = userWrapper.login(username.getText(), new String(password.getPassword()));
				isValid = true;
				if (isValid) {
					MainView mainView = new MainView(lv);
					mainView.show();
				} else {
					message.setText("ورود نامو�?ق، دوباره تلاش کنید");
				}
			}
		});
		loginFrame.add(login);
	}
	
	public void show() {    //not recommended
		show(false);
	}
	
	public void show(boolean logout) {
		username.setText("");
		password.setText("");
		if (logout)
			message.setText("شما با مو�?قیت خارج شدید");
		loginFrame.setLayout(null);
		loginFrame.setVisible(true);
	}
	
	public void hide() {
		loginFrame.setVisible(false);
	}
}
