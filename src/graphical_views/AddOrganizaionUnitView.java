package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import software_system.OrganizationUnit;
import user_management.User;
import user_management.UserWrapper;

public class AddOrganizaionUnitView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame addouFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JTextField OUName;
	private JLabel OUNameLabel;
	private JComboBox<String> managerName;
	private User[] users;
	private JLabel managerLabel;
	private JButton addOrganizationUnit;
	private JLabel message;

	public AddOrganizaionUnitView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();

		addouFrame = new JFrame();
		addouFrame.setBounds(150, 100, 600, 500);

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
		addouFrame.add(logout);

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
		addouFrame.add(return_);

		nameLabel = new JLabel("افزودن واحد سازمان", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		addouFrame.add(nameLabel);
		
		OUName = new JTextField();
		OUName.setBounds(100, 100, 300, 30);
		addouFrame.add(OUName);
		OUNameLabel = new JLabel("نام واحد سازمان:");
		OUNameLabel.setBounds(450, 100, 100, 30);
		addouFrame.add(OUNameLabel);
		
		managerName = new JComboBox<>();
		managerName.setBounds(100, 150, 300, 30);
		users = UserWrapper.getInstance().showUsers();
		for (User user: users)
			managerName.addItem(user.getName());
		addouFrame.add(managerName);
		managerLabel = new JLabel("مدیر واحد سازمان:");
		managerLabel.setBounds(450, 150, 100, 30);
		addouFrame.add(managerLabel);

		addOrganizationUnit = new JButton("ایجاد واحد سازمان");
		addOrganizationUnit.setBounds(170, 200, 160, 30);
		addOrganizationUnit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				User manager = users[managerName.getSelectedIndex()];
				OrganizationUnit ou = new OrganizationUnit(OUName.getText(), manager);
				boolean success = new OrganizationUnit("", null).addOrganizationUnit(ou);
				if (success)
					message.setText("موفقیت‌آمیز");
				else
					message.setText("ناموفق");
			}
		});
		addouFrame.add(addOrganizationUnit);

		message = new JLabel("", SwingConstants.CENTER);
		message.setBounds(100, 250, 250, 30);
		addouFrame.add(message);
	}

	@Override
	public void show() {
		addouFrame.setLayout(null);
		addouFrame.setVisible(true);
	}

	@Override
	public void hide() {
		addouFrame.setVisible(false);
	}

}