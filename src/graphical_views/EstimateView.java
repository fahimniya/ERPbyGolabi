package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import software_system.estimation.Estimator;

public class EstimateView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame estimateFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JButton estimateResource;
	private JButton estimateRequirements;
	
	public EstimateView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		estimateFrame = new JFrame();
		estimateFrame.setBounds(150, 100, 600, 380);
		
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
		estimateFrame.add(logout);
		
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
		estimateFrame.add(return_);
		
		nameLabel = new JLabel("تخمین", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		estimateFrame.add(nameLabel);
		
		final View reportv = this;
		
		estimateResource = new JButton("تخمین منابع");
		estimateResource.setFont(new Font(estimateResource.getFont().getName(), Font.PLAIN, 30));
		estimateResource.setBounds(100, 100, 400, 100);
		estimateResource.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EstimateResourceView estimateResourceView = new EstimateResourceView(reportv, loginView);
				estimateResourceView.show();
			}
		});
		estimateFrame.add(estimateResource);
		
		estimateRequirements = new JButton("تخمین نیازمندی‌های ضروری");
		estimateRequirements.setFont(new Font(estimateRequirements.getFont().getName(), Font.PLAIN, 30));
		estimateRequirements.setBounds(100, 220, 400, 100);
		estimateRequirements.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RequirementsReportView estimateRequirements = new RequirementsReportView(reportv, loginView, null, new Estimator().estimateEssentialRequirements());
				estimateRequirements.show();
			}
		});
		estimateFrame.add(estimateRequirements);
	}

	@Override
	public void show() {
		estimateFrame.setLayout(null);
		estimateFrame.setVisible(true);
	}

	@Override
	public void hide() {
		estimateFrame.setVisible(false);
	}
}
