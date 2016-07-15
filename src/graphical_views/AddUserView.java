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

import user_management.ManagerWrapper;
import user_management.User;

public class AddUserView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame addUserFrame;
	private JLabel _nameLabel;
	private JButton logout;
	private JButton return_;
	private JTextField username;
	private JLabel usernameLabel;
	private JTextField name;
	private JLabel nameLabel;
	private JPasswordField password;
	private JLabel passwordLabel;
	private JButton add;
	
	public AddUserView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		addUserFrame = new JFrame();
		addUserFrame.setBounds(150, 100, 600, 330);
		
		logout = new JButton("خروج");
		logout.setFont(new Font(logout.getFont().getName(), Font.PLAIN, 8));
		logout.setBounds(5, 5, 50, 25);
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				hide();
				loginView.show(true);
			}
		});
		addUserFrame.add(logout);
		
		return_ = new JButton("بازگشت");
		return_.setFont(new Font(return_.getFont().getName(), Font.PLAIN, 8));
		return_.setBounds(65, 5, 60, 25);
		return_.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				hide();
				returnView.show();
			}
		});
		addUserFrame.add(return_);
		
		_nameLabel = new JLabel("افزودن کاربر", SwingConstants.CENTER);
		_nameLabel.setBounds(0, 35, 600, 45);
		_nameLabel.setFont(new Font(_nameLabel.getFont().getName(), Font.PLAIN, 40));
		addUserFrame.add(_nameLabel);
		
		username = new JTextField();
		username.setBounds(100, 100, 300, 30);
		addUserFrame.add(username);
		usernameLabel = new JLabel("نام کاربری:");
		usernameLabel.setBounds(450, 100, 100, 30);
		addUserFrame.add(usernameLabel);
		
		name = new JTextField();
		name.setBounds(100, 150, 300, 30);
		addUserFrame.add(name);
		nameLabel = new JLabel("نام:");
		nameLabel.setBounds(450, 150, 100, 30);
		addUserFrame.add(nameLabel);
		
		password = new JPasswordField();
		password.setBounds(100, 200, 300, 30);
		addUserFrame.add(password);
		passwordLabel = new JLabel("کلمه‌ی عبور:");
		passwordLabel.setBounds(450, 200, 100, 30);
		addUserFrame.add(passwordLabel);
		
		add = new JButton("افزودن");
		add.setBounds(200, 250, 100, 30);
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				User newUser = new User(username.getText(), new String(password.getPassword()), name.getText());
				ManagerWrapper.getInstance().addUser(newUser);
			}
		});
		addUserFrame.add(add);
	}
	
	@Override
	public void show() {
		addUserFrame.setLayout(null);
		addUserFrame.setVisible(true);
	}

	@Override
	public void hide() {
		addUserFrame.setVisible(false);
	}

}
