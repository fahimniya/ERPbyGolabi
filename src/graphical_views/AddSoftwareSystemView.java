package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import software_system.ProcessWrapper;
import software_system.SoftwareSystem;
import software_system.Technology;

public class AddSoftwareSystemView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame addSSFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JTextField softwareSystemName;
	private JLabel SSNameLabel;
	private JTextField description;
	private JLabel descriptionLabel;
	private JPanel technologiesPanel;
	private Technology[] technologies;
	private ArrayList<JCheckBox> technologiesCBS;
	private JScrollPane tpScroll;
	private JLabel technologiesLabel;
	private JButton addTechnology;
	private JButton addSS;
	
	public AddSoftwareSystemView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		addSSFrame = new JFrame();
		addSSFrame.setBounds(150, 50, 600, 650);
		
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
		addSSFrame.add(logout);
		
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
		addSSFrame.add(return_);
		
		nameLabel = new JLabel("افزودن سیستم نرم‌افزاری", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		addSSFrame.add(nameLabel);
		
		softwareSystemName = new JTextField();
		softwareSystemName.setBounds(100, 100, 300, 30);
		addSSFrame.add(softwareSystemName);
		SSNameLabel = new JLabel("نام:");
		SSNameLabel.setBounds(450, 100, 100, 30);
		addSSFrame.add(SSNameLabel);
		
		description = new JTextField();
		description.setBounds(100, 150, 300, 30);
		addSSFrame.add(description);
		descriptionLabel = new JLabel("توصیف:");
		descriptionLabel.setBounds(450, 150, 100, 30);
		addSSFrame.add(descriptionLabel);
		
		technologiesPanel = new JPanel();
		technologiesPanel.setLayout(null);
		technologiesPanel.setBounds(100, 200, 300, 1000);
        tpScroll = new JScrollPane(technologiesPanel);
        tpScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tpScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tpScroll.setBounds(100, 200, 300, 300);
        addSSFrame.add(tpScroll);
        
        technologiesCBS = new ArrayList<>();
        
        technologiesLabel = new JLabel("فناوری‌ها:");
        technologiesLabel.setBounds(450, 200, 100, 30);
        addSSFrame.add(technologiesLabel);
        
        final View assv = this;
        
        addTechnology = new JButton("افزودن فناوری");
        addTechnology.setBounds(420, 250, 110, 30);
        addTechnology.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddTechnology addTechnology = new AddTechnology(assv, loginView);
				addTechnology.show();
			}
		});
        addSSFrame.add(addTechnology);
        
        addSS = new JButton("افزودن");
        addSS.setBounds(200, 520, 100, 40);
        addSS.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Technology> selectedTechnologies = new ArrayList<>();
				for (int i = 0; i < technologies.length; i++)
					if (technologiesCBS.get(i).isSelected())
						selectedTechnologies.add(technologies[i]);
				ProcessWrapper.getInstance().addSoftwareSystem(new SoftwareSystem(softwareSystemName.getText(), selectedTechnologies.toArray(new Technology[selectedTechnologies.size()]), description.getText()));
				hide();
				returnView.show();
			}
		});
        addSSFrame.add(addSS);
	}

	@Override
	public void show() {
		addSSFrame.setLayout(null);
		addSSFrame.setVisible(true);
		
		technologies = ProcessWrapper.getInstance().showTechnologies();
		for (int i = 0; i < technologiesCBS.size(); i++) {
			technologiesPanel.remove(technologiesCBS.get(i));
		}
		for (int i = 0; i < technologies.length; i++) {
			JCheckBox newCB = new JCheckBox(technologies[i].gettName());
			newCB.setBounds(30, i * 40, 200, 30);
			technologiesCBS.add(newCB);
			technologiesPanel.add(newCB);
		}
	}

	@Override
	public void hide() {
		addSSFrame.setVisible(false);
	}

}
