package graphical_views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.attribute.GroupPrincipal;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import software_system.ProcessWrapper;
import software_system.Development;
import software_system.Process;

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
	private Resource[] resources;
	
	public RemoveResourceView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		removeResourceFrame = new JFrame();
		removeResourceFrame.setBounds(150, 100, 600, 500);
		
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
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		removeResourceFrame.add(nameLabel);
		
		final View rmv = this;
		
		removeResource = new JButton("حذف منبع انتخاب‌شده");
		removeResource.setBounds(220, 450, 160, 30);
		removeResource.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Resource resourceForRemouve = null;
				for (int i = 0; i < resources.length; i++)
					if (resourcesRBS[i].isSelected())
						resourceForRemouve = resources[i];
				new AuthorizedResourceWrapper().removeResource(resourceForRemouve);
			}
		});
		
		resourcesPanel = new JPanel();
		resourcesPanel.setLayout(null);
        rpScroll = new JScrollPane(resourcesPanel);
        rpScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rpScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        rpScroll.setBounds(50, 130, 500, 300);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(600, 550));
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
		resources = rw.showResources();
		resourcesRBS = new JRadioButton[resources.length];
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < resources.length; i++) {
			resourcesRBS[i] = new JRadioButton(resources[i].getClass().equals(FacilityResource.class)? ("منبع تجهیزاتی: " + ((FacilityResource) resources[i]).getName()) : ("منبع مالی: " + ((FundingResource) resources[i]).getQuantity().getAmount() + ((FundingResource) resources[i]).getQuantity().getUnit()));
			resourcesRBS[i].setBounds(30, i * 40, 200, 30);
			resourcesPanel.add(resourcesRBS[i]);
			group.add(resourcesRBS[i]);
		}
	}

	@Override
	public void hide() {
		removeResourceFrame.setVisible(false);
	}

}