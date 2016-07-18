package graphical_views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import resources.AuthorizedResourceWrapper;
import resources.FacilityResource;
import resources.FundingResource;
import resources.Resource;
import resources.ResourceWrapper;
import software_system.HumanResource;
import software_system.Module;
import software_system.ProcessWrapper;
import software_system.SoftwareSystem;
import software_system.estimation.Requirement;
import software_system.estimation.RequirementWrapper;

public class RequirementsReportView implements View {
	private View returnView;
	private LoginView loginView;
	private SoftwareSystem softwareSystem;
	private JFrame requirementsReportFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JPanel modulesPanel;
	private JScrollPane mpScroll;
	private JLabel[] requirementsLS;
	
	private RequirementWrapper rw;
	private Requirement[] requirements;
	
	public RequirementsReportView(View rv, LoginView lv, SoftwareSystem softwareSystem, Requirement[] requirements) {
		returnView = rv;
		loginView = lv;
		this.softwareSystem = softwareSystem;
		this.requirements = requirements;
		returnView.hide();
		
		requirementsReportFrame = new JFrame();
		requirementsReportFrame.setBounds(150, 100, 900, 500);
		
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
		requirementsReportFrame.add(logout);
		
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
		requirementsReportFrame.add(return_);
		
		if (softwareSystem != null)
			nameLabel = new JLabel("گزارش منابع مورد نیاز برای سیستم نرم‌افزاری " + softwareSystem.getName(), SwingConstants.CENTER);
		else 
			nameLabel = new JLabel("تخمین", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 900, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		requirementsReportFrame.add(nameLabel);
		
		modulesPanel = new JPanel();
		modulesPanel.setLayout(null);
        mpScroll = new JScrollPane(modulesPanel);
        mpScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mpScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mpScroll.setBounds(50, 130, 800, 300);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(900, 480));
        contentPane.add(mpScroll);
        contentPane.add(nameLabel);
        contentPane.add(logout);
        contentPane.add(return_);
        requirementsReportFrame.setContentPane(contentPane);
        requirementsReportFrame.pack();
        requirementsReportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        requirementsReportFrame.setVisible(true);
	}
	
	@Override
	public void show() {
		requirementsReportFrame.setLayout(null);
		requirementsReportFrame.setVisible(true);
		
		rw = new RequirementWrapper();
		if (softwareSystem != null)
			requirements = rw.showRequirment(softwareSystem);
		System.out.println("Req Report: " + requirements.length);
		requirementsLS = new JLabel[requirements.length];
		for (int i = 0; i < requirements.length; i++) {
			requirementsLS[i] = new JLabel(requirements[i].toString());
			requirementsLS[i].setBounds(20, i * 40, 160, 30);
			modulesPanel.add(requirementsLS[i]);
		}
	}

	@Override
	public void hide() {
		requirementsReportFrame.setVisible(false);
	}

}