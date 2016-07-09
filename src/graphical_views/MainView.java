package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainView implements View {
	private LoginView loginView;
	private JFrame mainFrame;
	private JButton logout;
	private JButton userManagement;
	private JButton process;
	private JButton resource;
	private JButton report;
	private JButton estimate;
	
	public MainView(LoginView lv) {
		this.loginView = lv;
		loginView.hide();
		
		mainFrame = new JFrame();
		mainFrame.setBounds(150, 100, 600, 455);
		
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
		mainFrame.add(logout);
		
		final View mv = this;
		
		userManagement = new JButton("مدیریت کاربری");
		userManagement.setFont(new Font(userManagement.getFont().getName(), Font.PLAIN, 8));
		userManagement.setBounds(65, 5, 80, 25);
		userManagement.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserManagementView userManagement = new UserManagementView(mv, loginView);
				userManagement.show();
			}
		});
		mainFrame.add(userManagement);
		
		process = new JButton("فرآیند");
		process.setFont(new Font(process.getFont().getName(), Font.PLAIN, 30));
		process.setBounds(350, 55, 200, 140);
		process.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ProcessView processView = new ProcessView(mv, loginView);
				processView.show();
			}
		});
		mainFrame.add(process);
		
		resource = new JButton("منابع");
		resource.setFont(new Font(resource.getFont().getName(), Font.PLAIN, 30));
		resource.setBounds(50, 55, 200, 140);
		resource.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ResourceView resourceView = new ResourceView(mv, loginView);
				resourceView.show();
			}
		});
		mainFrame.add(resource);
		
		report = new JButton("گزارش");
		report.setFont(new Font(report.getFont().getName(), Font.PLAIN, 30));
		report.setBounds(350, 255, 200, 140);
		report.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ReportView report = new ReportView(mv, loginView);
				report.show();
			}
		});
		mainFrame.add(report);
		
		estimate = new JButton("تخمین");
		estimate.setFont(new Font(estimate.getFont().getName(), Font.PLAIN, 30));
		estimate.setBounds(50, 255, 200, 140);
		estimate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EstimateView estimateView = new EstimateView(mv, loginView);
				estimateView.show();
			}
		});
		mainFrame.add(estimate);
	}
	
	public void show(){
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);
	}
	
	public void hide() {
		mainFrame.setVisible(false);
	}
}
