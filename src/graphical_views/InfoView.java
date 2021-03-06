package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import user_management.User;
import user_management.UserWrapper;

public class InfoView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame infoFrame;
	private JLabel _nameLabel;
	private JButton logout;
	private JButton return_;
	private JLabel username;
	private JLabel usernameLabel;
	private JLabel name;
	private JLabel nameLabel;
	private JLabel isManager;
	private JLabel managerLabel;
	
	public InfoView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		infoFrame = new JFrame();
		infoFrame.setBounds(150, 100, 600, 300);
		
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
		
		User user = UserWrapper.getInstance().getAccountInformation(UserWrapper.getInstance().getUsername());
		
		
		username = new JLabel(user.getUsername(), SwingConstants.RIGHT);
		username.setBounds(100, 100, 300, 30);
		infoFrame.add(username);
		usernameLabel = new JLabel("نام کاربری:");
		usernameLabel.setBounds(450, 100, 100, 30);
		infoFrame.add(usernameLabel);
		
		name = new JLabel(user.getName(), SwingConstants.RIGHT);
		name.setBounds(100, 150, 300, 30);
		infoFrame.add(name);
		nameLabel = new JLabel("نام:");
		nameLabel.setBounds(450, 150, 100, 30);
		infoFrame.add(nameLabel);
		
		isManager = new JLabel(UserWrapper.getInstance().isManager()? "بله": "خیر", SwingConstants.RIGHT);
		isManager.setBounds(100, 200, 300, 30);
		infoFrame.add(isManager);
		managerLabel = new JLabel("مدیریت؟");
		managerLabel.setBounds(450, 200, 100, 30);
		infoFrame.add(managerLabel);
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
