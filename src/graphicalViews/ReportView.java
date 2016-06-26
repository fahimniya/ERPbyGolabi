package graphicalViews;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ReportView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame reportFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JButton report1;
	private JButton report2;
	private JButton report3;
	
	public ReportView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		reportFrame = new JFrame();
		reportFrame.setBounds(150, 100, 600, 500);
		
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
		reportFrame.add(logout);
		
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
		reportFrame.add(return_);
		
		nameLabel = new JLabel("گزارش‌گیری", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		reportFrame.add(nameLabel);
		
		final View reportv = this;
		
		report1 = new JButton("گزارش نوع اول");
		report1.setFont(new Font(report1.getFont().getName(), Font.PLAIN, 30));
		report1.setBounds(100, 100, 400, 100);
		report1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ShowReportView showReport = new ShowReportView(reportv, loginView);
				showReport.show();
			}
		});
		reportFrame.add(report1);
		
		report2 = new JButton("گزارش نوع دوم");
		report2.setFont(new Font(report2.getFont().getName(), Font.PLAIN, 30));
		report2.setBounds(100, 220, 400, 100);
		report2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ShowReportView showReport = new ShowReportView(reportv, loginView);
				showReport.show();
			}
		});
		reportFrame.add(report2);
		
		report3 = new JButton("گزراش نوع سوم");
		report3.setFont(new Font(report3.getFont().getName(), Font.PLAIN, 30));
		report3.setBounds(100, 340, 400, 100);
		report3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ShowReportView showReport = new ShowReportView(reportv, loginView);
				showReport.show();
			}
		});
		reportFrame.add(report3);
	}

	@Override
	public void show() {
		reportFrame.setLayout(null);
		reportFrame.setVisible(true);
	}

	@Override
	public void hide() {
		reportFrame.setVisible(false);
	}
}
