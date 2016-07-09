package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddModuleView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame addModuleFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JRadioButton humanRB;
	private JRadioButton facilityRB;
	private JRadioButton fundingRB;
	private JLabel resourceType;
	private JTextField moduleName;
	private JLabel moduleNameLabel;
	private JTextField projectName;
	private JLabel projectNameLabel;
	private JButton addModule;
	private JRadioButton developmentRB;
	private JRadioButton maintenanceRB;
	private JLabel processType;
	private JTextField from_year;
	private JTextField from_month;
	private JTextField from_day;
	private JLabel fromLabel;
	private JTextField to_year;
	private JTextField to_month;
	private JTextField to_day;
	private JLabel toLabel;
	
	public AddModuleView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		addModuleFrame = new JFrame();
		addModuleFrame.setBounds(150, 100, 600, 550);
		
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
		addModuleFrame.add(logout);
		
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
		addModuleFrame.add(return_);
		
		nameLabel = new JLabel("افزدون ماژول", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		addModuleFrame.add(nameLabel);
		
		humanRB = new JRadioButton("منبع انسانی");
		humanRB.setBounds(100, 250, 90, 30);
		addModuleFrame.add(humanRB);
		fundingRB = new JRadioButton("منبع مالی");
		fundingRB.setBounds(190, 250, 90, 30);
		addModuleFrame.add(fundingRB);
		facilityRB = new JRadioButton("منبع تجهیزاتی");
		facilityRB.setBounds(280, 250, 90, 30);
		facilityRB.setSelected(true);
		addModuleFrame.add(facilityRB);
		ButtonGroup group = new ButtonGroup();
		group.add(humanRB);
		group.add(facilityRB);
		group.add(fundingRB);
		
		resourceType = new JLabel("نوع منبع:");
		resourceType.setBounds(380, 250, 60, 30);
		addModuleFrame.add(resourceType);
		
		developmentRB = new JRadioButton("فرآیند ایجاد");
		developmentRB.setBounds(220, 300, 100, 30);
		developmentRB.setSelected(true);
		addModuleFrame.add(developmentRB);
		maintenanceRB = new JRadioButton("فرآیند نگهداری");
		maintenanceRB.setBounds(100, 300, 100, 30);
		addModuleFrame.add(maintenanceRB);
		ButtonGroup group2 = new ButtonGroup();
		group2.add(developmentRB);
		group2.add(maintenanceRB);
		
		processType = new JLabel("نوع فرآیند:");
		processType.setBounds(380, 300, 60, 30);
		addModuleFrame.add(processType);
		
		moduleName = new JTextField();
		moduleName.setBounds(100, 150, 250, 30);
		addModuleFrame.add(moduleName);
		
		moduleNameLabel = new JLabel("نام ماژول:");
		moduleNameLabel.setBounds(380, 150, 60, 30);
		addModuleFrame.add(moduleNameLabel);
		
		projectName = new JTextField();
		projectName.setBounds(100, 200, 250, 30);
		addModuleFrame.add(projectName);
		
		projectNameLabel = new JLabel("نام پروژه:");
		projectNameLabel.setBounds(380, 200, 60, 30);
		addModuleFrame.add(projectNameLabel);
		
		from_year = new JTextField();
		from_year.setBounds(100, 350, 60, 30);
		addModuleFrame.add(from_year);
		from_month = new JTextField();
		from_month.setBounds(195, 350, 60, 30);
		addModuleFrame.add(from_month);
		from_day = new JTextField();
		from_day.setBounds(290, 350, 60, 30);
		addModuleFrame.add(from_day);
		fromLabel = new JLabel("تاریخ شروع:");
		fromLabel.setBounds(380, 350, 60, 30);
		addModuleFrame.add(fromLabel);
		
		to_year = new JTextField();
		to_year.setBounds(100, 400, 60, 30);
		addModuleFrame.add(to_year);
		to_month = new JTextField();
		to_month.setBounds(195, 400, 60, 30);
		addModuleFrame.add(to_month);
		to_day = new JTextField();
		to_day.setBounds(290, 400, 60, 30);
		addModuleFrame.add(to_day);
		toLabel = new JLabel("تاریخ اتمام:");
		toLabel.setBounds(380, 400, 60, 30);
		addModuleFrame.add(toLabel);
		
		final View amv = this;
		
		addModule = new JButton("ادامه");
		addModule.setBounds(180, 450, 90, 30);
		addModule.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date from = new Date(Integer.parseInt(from_year.getText()), Integer.parseInt(from_month.getText()), Integer.parseInt(from_day.getText()));
				Date to = new Date(Integer.parseInt(to_year.getText()), Integer.parseInt(to_month.getText()), Integer.parseInt(to_day.getText()));
				
				if (humanRB.isSelected()) {
					AddHumanModuleView addHMV = new AddHumanModuleView(amv, loginView, from, to, moduleName.getText(), projectName.getText(), developmentRB.isSelected());
					addHMV.show();
				} else if (fundingRB.isSelected()) {
					AddFundingModuleView addFMV = new AddFundingModuleView(amv, loginView, from, to, moduleName.getText(), projectName.getText(), developmentRB.isSelected());
					addFMV.show();
				} else {
					AddFacilityModuleView addFaMV = new AddFacilityModuleView(amv, loginView, from, to, moduleName.getText(), projectName.getText(), developmentRB.isSelected());
					addFaMV.show();
				}
			}
		});
		addModuleFrame.add(addModule);
	}
	
	@Override
	public void show() {
		addModuleFrame.setLayout(null);
		addModuleFrame.setVisible(true);
	}

	@Override
	public void hide() {
		addModuleFrame.setVisible(false);
	}

}
