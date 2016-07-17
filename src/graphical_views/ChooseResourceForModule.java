package graphical_views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


import resources.FacilityResource;
import resources.FundingResource;
import resources.Quantity;
import resources.Resource;
import resources.ResourceWrapper;
import resources.Unit;
import software_system.HumanResource;
import software_system.ProcessWrapper;

public class ChooseResourceForModule implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame chooseResourceFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JPanel resourcesPanel;
	private JScrollPane rpScroll;
	private JRadioButton[] resourcesRBS;
	private JTextField amount;
	private JTextField unit;
	private JButton removeResource;
	private JRadioButton developmentRB;
	private JRadioButton maintenanceRB;
	private JTextField from_year;
	private JTextField from_month;
	private JTextField from_day;
	private JLabel fromLabel;
	private JTextField to_year;
	private JTextField to_month;
	private JTextField to_day;
	private JLabel toLabel;
	
	private ResourceWrapper rw;
	private Resource[] facilityResources;
	private HumanResource[] humanResources;
	
	public ChooseResourceForModule(View rv, LoginView lv, final String moduleName, final String SSName) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		chooseResourceFrame = new JFrame();
		chooseResourceFrame.setBounds(150, 100, 800, 600);
		
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
		chooseResourceFrame.add(logout);
		
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
		chooseResourceFrame.add(return_);
		
		nameLabel = new JLabel("انتخاب منبع برای اختصاص به ماژول " + moduleName + " از سیستم نرم‌افزاری " + SSName, SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 800, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 30));
		chooseResourceFrame.add(nameLabel);
		
		removeResource = new JButton("اختصاص منبع انتخاب‌شده");
		removeResource.setBounds(300, 520, 200, 40);
		removeResource.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date from = new Date(Integer.parseInt(from_year.getText()) - 1900,
						Integer.parseInt(from_month.getText()) - 1, Integer.parseInt(from_day.getText()));
				Date to = new Date(Integer.parseInt(to_year.getText()) - 1900,
						Integer.parseInt(to_month.getText()) - 1, Integer.parseInt(to_day.getText()));
				for (int i = 0; i < facilityResources.length; i++)
					if (resourcesRBS[i].isSelected())
						ProcessWrapper.getInstance().addModule(moduleName, SSName, facilityResources[i], developmentRB.isSelected()? "DEVELOPMENT" : "MAINTENANCE", from, to);
				for (int i = facilityResources.length; i < facilityResources.length + humanResources.length; i++)
					if (resourcesRBS[i].isSelected())
						ProcessWrapper.getInstance().addModule(moduleName, SSName, humanResources[i - facilityResources.length], developmentRB.isSelected()? "DEVELOPMENT" : "MAINTENANCE", from, to);
				if (resourcesRBS[facilityResources.length + humanResources.length].isSelected())
					ProcessWrapper.getInstance().addModule(moduleName, SSName, new FundingResource(new Quantity(Integer.parseInt(amount.getText()), new Unit(unit.getText()))), developmentRB.isSelected()? "DEVELOPMENT" : "MAINTENANCE", from, to);
				hide();
				returnView.show();
			}
		});
		
		resourcesPanel = new JPanel();
		resourcesPanel.setLayout(null);
        rpScroll = new JScrollPane(resourcesPanel);
        rpScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rpScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        rpScroll.setBounds(50, 130, 700, 200);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(800, 600));
        contentPane.add(rpScroll);
        contentPane.add(nameLabel);
        contentPane.add(removeResource);
        contentPane.add(logout);
        contentPane.add(return_);
        chooseResourceFrame.setContentPane(contentPane);
        chooseResourceFrame.pack();
        chooseResourceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chooseResourceFrame.setVisible(true);
        
        developmentRB = new JRadioButton("فرآیند ایجاد");
		developmentRB.setBounds(150, 350, 100, 30);
		developmentRB.setSelected(true);
		chooseResourceFrame.add(developmentRB);
		maintenanceRB = new JRadioButton("فرآیند نگهداری");
		maintenanceRB.setBounds(350, 350, 100, 30);
		chooseResourceFrame.add(maintenanceRB);
		ButtonGroup group = new ButtonGroup();
		group.add(developmentRB);
		group.add(maintenanceRB);
		
		from_year = new JTextField();
		from_year.setBounds(100, 400, 60, 30);
		chooseResourceFrame.add(from_year);
		from_month = new JTextField();
		from_month.setBounds(195, 400, 60, 30);
		chooseResourceFrame.add(from_month);
		from_day = new JTextField();
		from_day.setBounds(290, 400, 60, 30);
		chooseResourceFrame.add(from_day);
		fromLabel = new JLabel("تاریخ شروع:");
		fromLabel.setBounds(380, 400, 60, 30);
		chooseResourceFrame.add(fromLabel);

		to_year = new JTextField();
		to_year.setBounds(100, 450, 60, 30);
		chooseResourceFrame.add(to_year);
		to_month = new JTextField();
		to_month.setBounds(195, 450, 60, 30);
		chooseResourceFrame.add(to_month);
		to_day = new JTextField();
		to_day.setBounds(290, 450, 60, 30);
		chooseResourceFrame.add(to_day);
		toLabel = new JLabel("تاریخ اتمام:");
		toLabel.setBounds(380, 450, 60, 30);
		chooseResourceFrame.add(toLabel);
	}
	
	@Override
	public void show() {
		chooseResourceFrame.setLayout(null);
		chooseResourceFrame.setVisible(true);
		
		rw = new ResourceWrapper();
		facilityResources = rw.showFacilityResources();
		humanResources = rw.showHumanResources();
		resourcesRBS = new JRadioButton[facilityResources.length + humanResources.length + 1];
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < facilityResources.length; i++) {
			resourcesRBS[i] = new JRadioButton(facilityResources[i].getClass().equals(FacilityResource.class)? ("منبع تجهیزاتی: " + ((FacilityResource) facilityResources[i]).getName()) : ("منبع مالی: " + ((FundingResource) facilityResources[i]).getQuantity().getAmount() + " " + ((FundingResource) facilityResources[i]).getQuantity().getUnit()));
			resourcesRBS[i].setBounds(20, i * 40, 160, 30);
			resourcesPanel.add(resourcesRBS[i]);
			group.add(resourcesRBS[i]);
		}
		for (int i = facilityResources.length; i < facilityResources.length + humanResources.length; i++) {
			HumanResource hr = humanResources[i - facilityResources.length];
			resourcesRBS[i] = new JRadioButton("منبع انسانی: " + hr.getUser().getName() + " (از: " + hr.getFrom().toString() + " تا: " + hr.getTo().toString() + " ");
			resourcesRBS[i].setBounds(220, (i - facilityResources.length) * 40, 460, 30);
			resourcesPanel.add(resourcesRBS[i]);
			group.add(resourcesRBS[i]);
		}
		resourcesRBS[facilityResources.length + humanResources.length] = new JRadioButton("منبع مالی:");
		resourcesRBS[facilityResources.length + humanResources.length].setBounds(20, facilityResources.length * 40, 70, 30);
		resourcesPanel.add(resourcesRBS[facilityResources.length + humanResources.length]);
		group.add(resourcesRBS[facilityResources.length + humanResources.length]);
		amount = new JTextField("مقدار");
		amount.setBounds(90, facilityResources.length * 40, 50, 30);
		resourcesPanel.add(amount);
		unit = new JTextField("واحد");
		unit.setBounds(150, facilityResources.length * 40, 50, 30);
		resourcesPanel.add(unit);
	}

	@Override
	public void hide() {
		chooseResourceFrame.setVisible(false);
	}

}