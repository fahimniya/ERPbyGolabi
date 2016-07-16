package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import software_system.ProcessWrapper;
import software_system.Technology;

public class AddTechnology implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame addTechnologyFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JLabel technologyNameLabel;
	private JTextField technologyName;
	private JButton addTechnology;
	
	public AddTechnology(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		addTechnologyFrame = new JFrame();
		addTechnologyFrame.setBounds(150, 50, 300, 300);
		
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
		addTechnologyFrame.add(logout);
		
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
		addTechnologyFrame.add(return_);
		
		nameLabel = new JLabel("افزودن فناوری", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 300, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		addTechnologyFrame.add(nameLabel);
		
		technologyNameLabel = new JLabel("نام فناوری:", SwingConstants.CENTER);
		technologyNameLabel.setBounds(0, 100, 300, 30);
		addTechnologyFrame.add(technologyNameLabel);
		technologyName = new JTextField();
		technologyName.setBounds(50, 140, 200, 30);
		addTechnologyFrame.add(technologyName);
		
		addTechnology = new JButton("افزودن");
		addTechnology.setBounds(100, 200, 100, 30);
		addTechnology.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ProcessWrapper.getInstance().addTechnology(new Technology(technologyName.getText()));
				hide();
				returnView.show();
			}
		});
		addTechnologyFrame.add(addTechnology);
	}

	@Override
	public void show() {
		addTechnologyFrame.setLayout(null);
		addTechnologyFrame.setVisible(true);
	}

	@Override
	public void hide() {
		addTechnologyFrame.setVisible(false);
	}

}
