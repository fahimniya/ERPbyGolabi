package graphical_views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import software_system.ProcessWrapper;
import software_system.Development;
import software_system.Process;

public class RemoveProcessView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame removeProcessFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JPanel processesPanel;
	private JScrollPane ppScroll;
	private JCheckBox[] processesCBS;
	
	private ProcessWrapper pw;
	private Process[] processes;
	
	public RemoveProcessView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		removeProcessFrame = new JFrame();
		removeProcessFrame.setBounds(150, 100, 600, 500);
		
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
		removeProcessFrame.add(logout);
		
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
		removeProcessFrame.add(return_);
		
		nameLabel = new JLabel("حذف فرآیندها", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		removeProcessFrame.add(nameLabel);
		
		processesPanel = new JPanel();
		processesPanel.setLayout(new BorderLayout());
		ppScroll = new JScrollPane(processesPanel);
		ppScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ppScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		processesPanel.setBounds(50, 130, 500, 400);
		removeProcessFrame.add(processesPanel);
	}
	
	@Override
	public void show() {
		removeProcessFrame.setLayout(null);
		removeProcessFrame.setVisible(true);
		
		pw = new ProcessWrapper();
		processes = pw.showProcesses();
		processesCBS = new JCheckBox[processes.length];
		for (int i = 0; i < processes.length; i++) {
			processesCBS[i] = new JCheckBox(processes[i].getProjectName() + "(" + (processes[i].getClass().equals(Development.class) ? "ایجاد" : "نگهداری") + ")");
		}
	}

	@Override
	public void hide() {
		removeProcessFrame.setVisible(false);
	}

}