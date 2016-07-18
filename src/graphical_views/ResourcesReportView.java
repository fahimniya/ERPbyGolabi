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

public class ResourcesReportView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame resourcesReportFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JPanel resourcesPanel;
	private JScrollPane rpScroll;
	private JLabel[] resourcesLS;
	
	private ResourceWrapper rw;
	private Resource[] facilityResources;
	private Resource[] fundingResources;
	private HumanResource[] humanResources;
	
	public ResourcesReportView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		resourcesReportFrame = new JFrame();
		resourcesReportFrame.setBounds(150, 100, 800, 500);
		
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
		resourcesReportFrame.add(logout);
		
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
		resourcesReportFrame.add(return_);
		
		nameLabel = new JLabel("لیست منابع", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 800, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		resourcesReportFrame.add(nameLabel);
		
		resourcesPanel = new JPanel();
		resourcesPanel.setLayout(null);
        rpScroll = new JScrollPane(resourcesPanel);
        rpScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rpScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        rpScroll.setBounds(50, 130, 700, 300);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(800, 550));
        contentPane.add(rpScroll);
        contentPane.add(nameLabel);
        contentPane.add(logout);
        contentPane.add(return_);
        resourcesReportFrame.setContentPane(contentPane);
        resourcesReportFrame.pack();
        resourcesReportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resourcesReportFrame.setVisible(true);
	}
	
	@Override
	public void show() {
		resourcesReportFrame.setLayout(null);
		resourcesReportFrame.setVisible(true);
		
		rw = new ResourceWrapper();
		facilityResources = rw.showFacilityResources();
		System.out.println("number of facility resources: " + facilityResources.length);
		fundingResources = rw.showFundingResources();
		System.out.println("number of funding resources: " + fundingResources.length);
		humanResources = rw.showHumanResources();
		System.out.println("number of human resources: " + humanResources.length);
		resourcesLS = new JLabel[facilityResources.length + fundingResources.length + humanResources.length];
		for (int i = 0; i < facilityResources.length; i++) {
			resourcesLS[i] = new JLabel(("منبع تجهیزاتی: " + ((FacilityResource) facilityResources[i]).getName()));
			resourcesLS[i].setBounds(20, i * 40, 160, 30);
			resourcesPanel.add(resourcesLS[i]);
		}
		for (int i = facilityResources.length; i < facilityResources.length + fundingResources.length; i++) {
			resourcesLS[i] = new JLabel(("منبع مالی: " + ((FundingResource) fundingResources[i - facilityResources.length]).getQuantity().getAmount() + " " + ((FundingResource) fundingResources[i - facilityResources.length]).getQuantity().getUnit()));
			resourcesLS[i].setBounds(20, i * 40, 160, 30);
			resourcesPanel.add(resourcesLS[i]);
		}
		for (int i = facilityResources.length + fundingResources.length; i < facilityResources.length + fundingResources.length + humanResources.length; i++) {
			HumanResource hr = humanResources[i - facilityResources.length - fundingResources.length];
			resourcesLS[i] = new JLabel("منبع انسانی: " + hr.getUser().getName() + " (از: " + hr.getFrom().toString() + " تا: " + hr.getTo().toString() + ")");
			resourcesLS[i].setBounds(220, (i - facilityResources.length - fundingResources.length) * 40, 460, 30);
			resourcesPanel.add(resourcesLS[i]);
		}
	}

	@Override
	public void hide() {
		resourcesReportFrame.setVisible(false);
	}

}