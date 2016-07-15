package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import resources.AuthorizedResourceWrapper;
import resources.FacilityResource;
import resources.Quantity;
import resources.ResourceWrapper;
import resources.Unit;
import software_system.HumanResource;
import user_management.User;
import user_management.UserWrapper;

public class AddResourceView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame addResourceFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JSeparator separator1;
	private JSeparator separator2;
	private JButton addFundingResource;
	private JButton addFacilityResource;
	private JButton addHumanResource;
	private JTextField value;
	private JLabel valueLabel;
	private JTextField currency;
	private JLabel currencyLabel;
	private JLabel fundingMessage;
	private JLabel facilityNameLabel;
	private JTextField facilityName;
	private JLabel facilityMessage;
	private JLabel username;
	private JComboBox<String> usersCB = new JComboBox<String>();
	private JTextField from_year;
	private JTextField from_month;
	private JTextField from_day;
	private JLabel fromLabel;
	private JTextField to_year;
	private JTextField to_month;
	private JTextField to_day;
	private JLabel toLabel;
	private JLabel humanMessage;
	
	public AddResourceView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		addResourceFrame = new JFrame();
		addResourceFrame.setBounds(150, 100, 900, 540);
		
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
		addResourceFrame.add(logout);
		
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
		addResourceFrame.add(return_);
		
		nameLabel = new JLabel("افزودن منبع", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 900, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		addResourceFrame.add(nameLabel);
		
		separator1 = new JSeparator(SwingConstants.VERTICAL);
		separator1.setBounds(600, 90, 2, 400);
		addResourceFrame.add(separator1);
		separator2 = new JSeparator(SwingConstants.VERTICAL);
		separator2.setBounds(300, 90, 2, 400);
		addResourceFrame.add(separator2);
		
		
		valueLabel = new JLabel("مقدار:", SwingConstants.CENTER);
		valueLabel.setBounds(100, 100, 100, 30);
		addResourceFrame.add(valueLabel);
		value = new JTextField();
		value.setBounds(70, 140, 160, 30);
		addResourceFrame.add(value);
		
		currencyLabel = new JLabel("واحد:", SwingConstants.CENTER);
		currencyLabel.setBounds(100, 200, 100, 30);
		addResourceFrame.add(currencyLabel);
		currency = new JTextField();
		currency.setBounds(70, 240, 160, 30);
		addResourceFrame.add(currency);
		
		addFundingResource = new JButton("افزودن منبع مالی");
		addFundingResource.setBounds(70, 300, 160, 30);
		addFundingResource.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean success = new AuthorizedResourceWrapper().addFundingResource(new Quantity(Integer.parseInt(value.getText()), new Unit(currency.getText())));
				if (success)
					fundingMessage.setText("موفقیت‌آمیز");
				else
					fundingMessage.setText("ناموفق");
			}
		});
		addResourceFrame.add(addFundingResource);
		
		fundingMessage = new JLabel("", SwingConstants.CENTER);
		fundingMessage.setBounds(100, 340, 100, 30);
		addResourceFrame.add(fundingMessage);
		
		facilityNameLabel = new JLabel("نام منبع تجهیزاتی:", SwingConstants.CENTER);
		facilityNameLabel.setBounds(400, 100, 100, 30);
		addResourceFrame.add(facilityNameLabel);
		facilityName = new JTextField();
		facilityName.setBounds(370, 140, 160, 30);
		addResourceFrame.add(facilityName);
		
		addFacilityResource = new JButton("افزودن منبع تجهیزاتی");
		addFacilityResource.setBounds(370, 200, 160, 30);
		addFacilityResource.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean success = new AuthorizedResourceWrapper().addFacilityResource(new FacilityResource(facilityName.getText()));
				if (success)
					facilityMessage.setText("موفقیت‌آمیز");
				else
					facilityMessage.setText("ناموفق");
			}
		});
		addResourceFrame.add(addFacilityResource);
		
		facilityMessage = new JLabel("", SwingConstants.CENTER);
		facilityMessage.setBounds(400, 240, 100, 30);
		addResourceFrame.add(facilityMessage);
		
		username = new JLabel("نام کاربری:", SwingConstants.CENTER);
		username.setBounds(700, 100, 100, 30);
		addResourceFrame.add(username);
		usersCB = new JComboBox<>();
		usersCB.setBounds(670, 140, 160, 30);
		final User[] users = UserWrapper.getInstance().showUsers();
		for (User user: users)
			usersCB.addItem(user.getUsername());
		addResourceFrame.add(usersCB);
		
		fromLabel = new JLabel("از:", SwingConstants.CENTER);
		fromLabel.setBounds(700, 200, 100, 30);
		addResourceFrame.add(fromLabel);
		toLabel = new JLabel("تا:", SwingConstants.CENTER);
		toLabel.setBounds(700, 300, 100, 30);
		addResourceFrame.add(toLabel);
		
		from_year = new JTextField();
		from_year.setBounds(640, 240, 60, 30);
		addResourceFrame.add(from_year);
		from_month = new JTextField();
		from_month.setBounds(720, 240, 60, 30);
		addResourceFrame.add(from_month);
		from_day = new JTextField();
		from_day.setBounds(800, 240, 60, 30);
		addResourceFrame.add(from_day);
		
		to_year = new JTextField();
		to_year.setBounds(640, 340, 60, 30);
		addResourceFrame.add(to_year);
		to_month = new JTextField();
		to_month.setBounds(720, 340, 60, 30);
		addResourceFrame.add(to_month);
		to_day = new JTextField();
		to_day.setBounds(800, 340, 60, 30);
		addResourceFrame.add(to_day);
		
		addHumanResource = new JButton("افزودن منبع انسانی");
		addHumanResource.setBounds(670, 410, 160, 30);
		addHumanResource.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date from = new Date(Integer.parseInt(from_year.getText()) - 1900, Integer.parseInt(from_month.getText()) - 1, Integer.parseInt(from_day.getText()));
				Date to = new Date(Integer.parseInt(to_year.getText()) - 1900, Integer.parseInt(to_month.getText()) - 1, Integer.parseInt(to_day.getText()));
				boolean success = new AuthorizedResourceWrapper().addHumanResource(new HumanResource(users[usersCB.getSelectedIndex()], from, to));
				if (success)
					humanMessage.setText("موفقیت‌آمیز");
				else
					humanMessage.setText("ناموفق");
			}
		});
		addResourceFrame.add(addHumanResource);
		
		humanMessage = new JLabel("", SwingConstants.CENTER);
		humanMessage.setBounds(700, 440, 100, 30);
		addResourceFrame.add(humanMessage);
	}
	
	@Override
	public void show() {
		addResourceFrame.setLayout(null);
		addResourceFrame.setVisible(true);
	}

	@Override
	public void hide() {
		addResourceFrame.setVisible(false);
	}

}
