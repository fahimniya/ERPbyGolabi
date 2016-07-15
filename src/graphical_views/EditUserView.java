package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import user_management.ManagerWrapper;
import user_management.User;
import user_management.UserWrapper;

public class EditUserView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame infoFrame;
	private JLabel _nameLabel;
	private JButton logout;
	private JButton return_;
	private JTextField username;
	private JLabel usernameLabel;
	private JTextField name;
	private JLabel nameLabel;
	private JPasswordField password;
	private JLabel passwordLabel;
	private JCheckBox isManager;
	private JLabel managerLabel;
	private JButton edit;
	private JLabel message;
	
	public EditUserView(View rv, LoginView lv, User usr) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		infoFrame = new JFrame();
		infoFrame.setBounds(150, 100, 600, 350);
		if (UserWrapper.getInstance().isManager())
			infoFrame.setBounds(150, 100, 600, 400);
		
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
		infoFrame.add(logout);
		
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
		infoFrame.add(return_);
		
		_nameLabel = new JLabel("نمایش اطلاعات کاربری", SwingConstants.CENTER);
		_nameLabel.setBounds(0, 35, 600, 45);
		_nameLabel.setFont(new Font(_nameLabel.getFont().getName(), Font.PLAIN, 40));
		infoFrame.add(_nameLabel);
		
		final User user;
		if (usr == null)
			user = UserWrapper.getInstance().getAccountInformation(UserWrapper.getInstance().getUsername());
		else
			user = usr;
		
		username = new JTextField(user.getUsername());
		username.setBounds(100, 100, 300, 30);
		infoFrame.add(username);
		usernameLabel = new JLabel("نام کاربری:");
		usernameLabel.setBounds(450, 100, 100, 30);
		infoFrame.add(usernameLabel);
		
		name = new JTextField(user.getName());
		name.setBounds(100, 150, 300, 30);
		infoFrame.add(name);
		nameLabel = new JLabel("نام:");
		nameLabel.setBounds(450, 150, 100, 30);
		infoFrame.add(nameLabel);
		
		password = new JPasswordField();
		password.setBounds(100, 200, 300, 30);
		infoFrame.add(password);
		passwordLabel = new JLabel("کلمه‌ی عبور جدید");
		passwordLabel.setBounds(450, 200, 100, 30);
		infoFrame.add(passwordLabel);
		
		isManager = new JCheckBox("بله");
		isManager.setBounds(350, 250, 50, 30);
		System.out.println(user.getRole());
		isManager.setSelected(user.getRole().equals("MANAGER"));
		if (UserWrapper.getInstance().isManager())
			infoFrame.add(isManager);
		managerLabel = new JLabel("مدیریت");
		managerLabel.setBounds(450, 250, 100, 30);
		infoFrame.add(managerLabel);
		
		edit = new JButton("ویرایش");
		edit.setBounds(260, 250, 80, 45);
		if (UserWrapper.getInstance().isManager())
			edit.setBounds(260, 300, 80, 45);
		edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserWrapper userWrapper = UserWrapper.getInstance();
				ManagerWrapper managerWrapper = ManagerWrapper.getInstance();
				boolean success;
				if (UserWrapper.getInstance().isManager()) {
					success = managerWrapper.updateUser(user.getUsername(), username.getText(), name.getText(), (new String(password.getPassword()).length() > 0)? new String(password.getPassword()) : user.getPassword(), isManager.isSelected()? "MANAGER" : "NORMAL");
				} else {
					success = userWrapper.editAccountInformation(user.getUsername(), username.getText(), name.getText(), (new String(password.getPassword()).length() > 0)? new String(password.getPassword()) : user.getPassword());
				}
				if (success)
					message.setText("موفقیت‌آمیز");
				else
					message.setText("ناموفق");
			}
		});
		infoFrame.add(edit);
		
		message = new JLabel("");
		message.setBounds(130, 250, 50, 45);
	}
	
	@Override
	public void show() {
		infoFrame.setLayout(null);
		infoFrame.setVisible(true);
	}

	@Override
	public void hide() {
		infoFrame.setVisible(false);
	}

}
