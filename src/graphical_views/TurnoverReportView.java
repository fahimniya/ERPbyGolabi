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

public class TurnoverReportView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame turnoverReportFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JPanel modulesPanel;
	private JScrollPane mpScroll;
	private JLabel[] modulesLS;
	
	private ProcessWrapper pw;
	private String[] modules;
	
	public TurnoverReportView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		turnoverReportFrame = new JFrame();
		turnoverReportFrame.setBounds(150, 100, 800, 500);
		
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
		turnoverReportFrame.add(logout);
		
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
		turnoverReportFrame.add(return_);
		
		nameLabel = new JLabel("لیست منابع", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 800, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		turnoverReportFrame.add(nameLabel);
		
		modulesPanel = new JPanel();
		modulesPanel.setLayout(null);
        mpScroll = new JScrollPane(modulesPanel);
        mpScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mpScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mpScroll.setBounds(50, 130, 700, 300);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(800, 550));
        contentPane.add(mpScroll);
        contentPane.add(nameLabel);
        contentPane.add(logout);
        contentPane.add(return_);
        turnoverReportFrame.setContentPane(contentPane);
        turnoverReportFrame.pack();
        turnoverReportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        turnoverReportFrame.setVisible(true);
	}
	
	@Override
	public void show() {
		turnoverReportFrame.setLayout(null);
		turnoverReportFrame.setVisible(true);
		
		pw = ProcessWrapper.getInstance();
		modules = pw.showModuleName();
		modulesLS = new JLabel[modules.length];
		for (int i = 0; i < modules.length; i++) {
			modulesLS[i] = new JLabel(modules[i]);
			modulesLS[i].setBounds(20, i * 40, 160, 30);
			modulesPanel.add(modulesLS[i]);
		}
	}

	@Override
	public void hide() {
		turnoverReportFrame.setVisible(false);
	}

}