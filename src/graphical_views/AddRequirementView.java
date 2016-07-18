package graphical_views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import resources.FacilityResource;
import resources.Quantity;
import resources.Resource;
import resources.ResourceWrapper;
import resources.Unit;
import software_system.OrganizationUnit;
import software_system.ProcessWrapper;
import software_system.SoftwareSystem;

public class AddRequirementView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame addRequirementFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JComboBox<String> OUNameCB;
	private JLabel OUNameLabel;
	private JComboBox<String> SSNameCB;
	private JLabel SSNameLabel;
	private JTextField humans;
	private JLabel humansLabel;
	private JTextField amount;
	private JTextField unit;
	private JLabel budget;
	private JComboBox<String> facilitiesCB;
	private JTextField facilityNumber;
	private JLabel facility;
	private JComboBox<String> priority;
	private JLabel priorityLabel;
	private JButton addRequirement;
	private JLabel message;
	
	public AddRequirementView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		addRequirementFrame = new JFrame();
		addRequirementFrame.setBounds(150, 100, 600, 550);
		
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
		addRequirementFrame.add(logout);
		
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
		addRequirementFrame.add(return_);
		
		nameLabel = new JLabel("افزودن نیازمندی", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		addRequirementFrame.add(nameLabel);
		
		OUNameCB = new JComboBox<>();
		OUNameCB.setBounds(100, 100, 300, 30);
		final OrganizationUnit[] organizationUnits = new OrganizationUnit(null, null).getAll();
		for (OrganizationUnit ou: organizationUnits)
			OUNameCB.addItem(ou.getName());
		addRequirementFrame.add(OUNameCB);
		OUNameLabel = new JLabel("نام واحد سازمانی:");
		OUNameLabel.setBounds(450, 100, 100, 30);
		addRequirementFrame.add(OUNameLabel);
		
		SSNameCB = new JComboBox<>();
		SSNameCB.setBounds(100, 150, 300, 30);
		final SoftwareSystem[] softwareSystems = ProcessWrapper.getInstance().showSoftwareSystem();
		for (SoftwareSystem ss: softwareSystems)
			SSNameCB.addItem(ss.getName());
		addRequirementFrame.add(SSNameCB);
		SSNameLabel = new JLabel("نام سیستم نرم‌افزاری:");
		SSNameLabel.setBounds(450, 150, 100, 30);
		addRequirementFrame.add(SSNameLabel);
		
		humans = new JTextField();
		humans.setBounds(300, 200, 100, 30);
		addRequirementFrame.add(humans);
		humansLabel = new JLabel("تعداد افراد:");
		humansLabel.setBounds(450, 200, 100, 30);
		addRequirementFrame.add(humansLabel);
		
		unit = new JTextField("واحد");
		unit.setBounds(100, 250, 100, 30);
		addRequirementFrame.add(unit);		
		amount = new JTextField("مقدار");
		amount.setBounds(300, 250, 100, 30);
		addRequirementFrame.add(amount);
		budget = new JLabel("بودجه:");
		budget.setBounds(450, 250, 100, 30);
		addRequirementFrame.add(budget);
		
		facilitiesCB = new JComboBox<>();
		facilitiesCB.setBounds(100, 300, 200, 30);
		final Resource[] facilities = new ResourceWrapper().showFacilityResources();
		for (Resource f: facilities)
			facilitiesCB.addItem(((FacilityResource) f).getName());
		addRequirementFrame.add(facilitiesCB);
		facilityNumber = new JTextField("تعداد");
		facilityNumber.setBounds(320, 300, 80, 30);
		addRequirementFrame.add(facilityNumber);
		facility = new JLabel("منبع تجهیزاتی:");
		facility.setBounds(450, 300, 100, 30);
		addRequirementFrame.add(facility);
		
		priority = new JComboBox<>();
		priority.setBounds(100, 350, 300, 30);
		priority.addItem("کم");
		priority.addItem("متوسط");
		priority.addItem("زیاد");
		addRequirementFrame.add(priority);
		priorityLabel = new JLabel("اولویت:");
		priorityLabel.setBounds(450, 350, 100, 30);
		addRequirementFrame.add(priorityLabel);
		
		addRequirement = new JButton("افزودن");
		addRequirement.setBounds(220, 400, 160, 30);
		addRequirement.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean success = organizationUnits[OUNameCB.getSelectedIndex()].registerResource(softwareSystems[SSNameCB.getSelectedIndex()].getName(), Integer.parseInt(humans.getText()), null, new Quantity(Integer.parseInt(amount.getText()), new Unit(unit.getText())), Integer.parseInt(facilityNumber.getText()), ((FacilityResource) facilities[facilitiesCB.getSelectedIndex()]).getName(), priority.getSelectedIndex() + 1);
				if (success)
					message.setText("موفقیت‌آمیز");
				else
					message.setText("ناموفق");
			}
		});
		addRequirementFrame.add(addRequirement);
		
		message = new JLabel("", SwingConstants.CENTER);
		message.setBounds(220, 450, 160, 30);
		addRequirementFrame.add(message);
	}
	
	@Override
	public void show() {
		addRequirementFrame.setLayout(null);
		addRequirementFrame.setVisible(true);
	}

	@Override
	public void hide() {
		addRequirementFrame.setVisible(false);
	}

}