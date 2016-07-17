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

public class RemoveResourceView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame removeResourceFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JPanel resourcesPanel;
	private JScrollPane rpScroll;
	private JRadioButton[] resourcesRBS;
	private JButton removeResource;
	
	private ResourceWrapper rw;
	private Resource[] facilityResources;
	private Resource[] fundingResources;
	private HumanResource[] humanResources;
	
	public RemoveResourceView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		removeResourceFrame = new JFrame();
		removeResourceFrame.setBounds(150, 100, 800, 500);
		
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
		removeResourceFrame.add(logout);
		
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
		removeResourceFrame.add(return_);
		
		nameLabel = new JLabel("حذف منابع", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 800, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		removeResourceFrame.add(nameLabel);
		
		removeResource = new JButton("حذف منبع انتخاب‌شده");
		removeResource.setBounds(220, 450, 160, 30);
		removeResource.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < facilityResources.length; i++)
					if (resourcesRBS[i].isSelected())
						new AuthorizedResourceWrapper().removeResource(facilityResources[i]);
				for (int i = facilityResources.length; i < facilityResources.length + fundingResources.length; i++) {
					if (resourcesRBS[i].isSelected())
						new AuthorizedResourceWrapper().removeResource(fundingResources[i - facilityResources.length]);
				}
				for (int i = facilityResources.length + fundingResources.length; i < facilityResources.length + fundingResources.length + humanResources.length; i++)
					if (resourcesRBS[i].isSelected())
						new AuthorizedResourceWrapper().removeHumanResource(humanResources[i - facilityResources.length - fundingResources.length]);
				hide();
				returnView.show();
			}
		});
		
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
        contentPane.add(removeResource);
        contentPane.add(logout);
        contentPane.add(return_);
        removeResourceFrame.setContentPane(contentPane);
        removeResourceFrame.pack();
        removeResourceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        removeResourceFrame.setVisible(true);
	}
	
	@Override
	public void show() {
		removeResourceFrame.setLayout(null);
		removeResourceFrame.setVisible(true);
		
		rw = new ResourceWrapper();
		facilityResources = rw.showFacilityResources();
		System.out.println("number of facility resources: " + facilityResources.length);
		fundingResources = rw.showFundingResources();
		System.out.println("number of funding resources: " + fundingResources.length);
		humanResources = rw.showHumanResources();
		System.out.println("number of human resources: " + humanResources.length);
		resourcesRBS = new JRadioButton[facilityResources.length + fundingResources.length + humanResources.length];
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < facilityResources.length; i++) {
			resourcesRBS[i] = new JRadioButton(("منبع تجهیزاتی: " + ((FacilityResource) facilityResources[i]).getName()));
			resourcesRBS[i].setBounds(20, i * 40, 160, 30);
			resourcesPanel.add(resourcesRBS[i]);
			group.add(resourcesRBS[i]);
		}
		for (int i = facilityResources.length; i < facilityResources.length + fundingResources.length; i++) {
			resourcesRBS[i] = new JRadioButton(("منبع مالی: " + ((FundingResource) fundingResources[i - facilityResources.length]).getQuantity().getAmount() + " " + ((FundingResource) fundingResources[i - facilityResources.length]).getQuantity().getUnit()));
			resourcesRBS[i].setBounds(20, i * 40, 160, 30);
			resourcesPanel.add(resourcesRBS[i]);
			group.add(resourcesRBS[i]);
		}
		for (int i = facilityResources.length + fundingResources.length; i < facilityResources.length + fundingResources.length + humanResources.length; i++) {
			HumanResource hr = humanResources[i - facilityResources.length - fundingResources.length];
			resourcesRBS[i] = new JRadioButton("منبع انسانی: " + hr.getUser().getName() + " (از: " + hr.getFrom().toString() + " تا: " + hr.getTo().toString() + ")");
			resourcesRBS[i].setBounds(220, (i - facilityResources.length - fundingResources.length) * 40, 460, 30);
			resourcesPanel.add(resourcesRBS[i]);
			group.add(resourcesRBS[i]);
		}
	}

	@Override
	public void hide() {
		removeResourceFrame.setVisible(false);
	}

}