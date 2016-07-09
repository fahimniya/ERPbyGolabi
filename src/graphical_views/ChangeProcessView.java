package graphical_views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import software_system.Development;
import software_system.Process;
import software_system.ProcessWrapper;

public class ChangeProcessView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame updateProcessListFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JPanel processesPanel;
	private JScrollPane ppScroll;
	private JRadioButton[] processesRBS;
	private JButton updateProcess;
	
	private ProcessWrapper pw;
	private Process[] processes;
	
	public ChangeProcessView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		updateProcessListFrame = new JFrame();
		updateProcessListFrame.setBounds(150, 100, 600, 550);
		
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
		updateProcessListFrame.add(logout);
		
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
		updateProcessListFrame.add(return_);
		
		nameLabel = new JLabel("لیست فرآیندها", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		updateProcessListFrame.add(nameLabel);
		
		final View cpv = this;
		
		updateProcess = new JButton("ویرایش فرآیند");
		updateProcess.setBounds(250, 450, 100, 30);
		updateProcess.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UpdateProcessView updateProcessView = new UpdateProcessView(cpv, loginView);
				updateProcessView.show();
			}
		});
		updateProcessListFrame.add(updateProcess);
		
		processesPanel = new JPanel();
		processesPanel.setLayout(null);
        ppScroll = new JScrollPane(processesPanel);
        ppScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ppScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ppScroll.setBounds(50, 130, 500, 300);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(600, 550));
        contentPane.add(ppScroll);
        contentPane.add(nameLabel);
        contentPane.add(updateProcess);
        updateProcessListFrame.setContentPane(contentPane);
        updateProcessListFrame.pack();
        updateProcessListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateProcessListFrame.setVisible(true);
	}
	
	@Override
	public void show() {
		updateProcessListFrame.setLayout(null);
		updateProcessListFrame.setVisible(true);
		
		pw = new ProcessWrapper();
		processes = pw.showProcesses();
		processesRBS = new JRadioButton[processes.length];
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < processes.length; i++) {
			processesRBS[i] = new JRadioButton(processes[i].getProjectName() + "(" + (processes[i].getClass().equals(Development.class) ? "ایجاد" : "نگهداری") + ")");
			group.add(processesRBS[i]);
		}
		
	}

	@Override
	public void hide() {
		updateProcessListFrame.setVisible(false);
	}

}