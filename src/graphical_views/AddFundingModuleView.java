package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AddFundingModuleView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame addFundingModuleFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	
	public AddFundingModuleView(View rv, LoginView lv, Date from, Date to, String moduleName, String projectName, boolean isDevelopment) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		addFundingModuleFrame = new JFrame();
		addFundingModuleFrame.setBounds(150, 100, 600, 500);
		
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
		addFundingModuleFrame.add(logout);
		
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
		addFundingModuleFrame.add(return_);
		
		nameLabel = new JLabel("افزودن منبع مالی به ماژول", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		addFundingModuleFrame.add(nameLabel);
		
	}
	
	@Override
	public void show() {
		addFundingModuleFrame.setLayout(null);
		addFundingModuleFrame.setVisible(true);
	}

	@Override
	public void hide() {
		addFundingModuleFrame.setVisible(false);
	}

}