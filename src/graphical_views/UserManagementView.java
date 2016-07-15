package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import user_management.UserWrapper;

public class UserManagementView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame userManagementFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JButton info;
	private JButton edit;
	private JButton confirmChanges;
	private JButton addUser;
	
	public UserManagementView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		userManagementFrame = new JFrame();
		userManagementFrame.setBounds(150, 100, 600, UserWrapper.getInstance().isManager()? 500 : 330);
		
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
		userManagementFrame.add(logout);
		
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
		userManagementFrame.add(return_);
		
		nameLabel = new JLabel("مدیریت کاربری", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		userManagementFrame.add(nameLabel);
		
		final View umv = this;
		
		info = new JButton("اطلاعات کاربری");
		info.setFont(new Font(info.getFont().getName(), Font.PLAIN, 28));
		info.setBounds(350, 100, 200, 140);
		info.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InfoView infoView = new InfoView(umv, loginView);
				infoView.show();
			}
		});
		userManagementFrame.add(info);
		
		edit = new JButton("ویرایش");
		if (UserWrapper.getInstance().isManager())
			edit.setText("ویرایش یا حذف");
		edit.setFont(new Font(edit.getFont().getName(), Font.PLAIN, 30));
		edit.setBounds(50, 100, 200, 140);
		edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (UserWrapper.getInstance().isManager()) {
					ChooseUserView chooseUserView = new ChooseUserView(umv, loginView);
					chooseUserView.show();
				} else {
					EditUserView editUserView = new EditUserView(umv, loginView, null);
					editUserView.show();
				}
			}
		});
		userManagementFrame.add(edit);
		
		confirmChanges = new JButton("تأیید ویرایش‌ها");
		confirmChanges.setFont(new Font(confirmChanges.getFont().getName(), Font.PLAIN, 30));
		confirmChanges.setBounds(350, 300, 200, 140);
		confirmChanges.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ConfirmChangesView confirmChanges = new ConfirmChangesView(umv, loginView);
				confirmChanges.show();
			}
		});
		if (UserWrapper.getInstance().isManager())
			userManagementFrame.add(confirmChanges);
		
		addUser = new JButton("افزودن کاربر");
		addUser.setFont(new Font(addUser.getFont().getName(), Font.PLAIN, 30));
		addUser.setBounds(50, 300, 200, 140);
		addUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddUserView addUserView = new AddUserView(umv, loginView);
				addUserView.show();
			}
		});
		if (UserWrapper.getInstance().isManager())
			userManagementFrame.add(addUser);
	}
	
	@Override
	public void show() {
		userManagementFrame.setLayout(null);
		userManagementFrame.setVisible(true);
	}

	@Override
	public void hide() {
		userManagementFrame.setVisible(false);
	}

}
