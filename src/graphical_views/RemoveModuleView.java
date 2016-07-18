package graphical_views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import software_system.ProcessWrapper;
import software_system.Development;
import software_system.Module;
import software_system.Process;

public class RemoveModuleView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame removeModuleFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JPanel modulesPanel;
	private JScrollPane mpScroll;
	private JCheckBox[] modulesCBS;
	private JButton removeModule;
	
	private ProcessWrapper pw;
	private String[] moduleNames;
	private Module[] modules;
	
	public RemoveModuleView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		removeModuleFrame = new JFrame();
		removeModuleFrame.setBounds(150, 100, 600, 500);
		
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
		removeModuleFrame.add(logout);
		
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
		removeModuleFrame.add(return_);
		
		nameLabel = new JLabel("حذف ماژول‌ها", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		removeModuleFrame.add(nameLabel);
		
		removeModule = new JButton("حذف ماژول‌های انتخاب‌شده");
		removeModule.setBounds(220, 450, 160, 30);
		removeModule.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> removeModules = new ArrayList<>();
				for (int i = 0; i < moduleNames.length; i++)
					if (modulesCBS[i].isSelected())
						removeModules.add(moduleNames[i]);
					ProcessWrapper.getInstance().removeModule(removeModules.toArray(new String[removeModules.size()]));
				show();
			}
		});
		
		modulesPanel = new JPanel();
		modulesPanel.setLayout(null);
		modulesPanel.setBounds(50, 130, 500, 1000);
        mpScroll = new JScrollPane(modulesPanel);
        mpScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mpScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mpScroll.setBounds(50, 130, 500, 300);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(600, 550));
        contentPane.add(mpScroll);
        contentPane.add(nameLabel);
        contentPane.add(removeModule);
        contentPane.add(logout);
        contentPane.add(return_);
        removeModuleFrame.setContentPane(contentPane);
        removeModuleFrame.pack();
        removeModuleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        removeModuleFrame.setVisible(true);
	}
	
	@Override
	public void show() {
		removeModuleFrame.setLayout(null);
		removeModuleFrame.setVisible(true);
		
		pw = ProcessWrapper.getInstance();
		moduleNames = pw.showModuleName();
		System.out.println("number of modules: " + moduleNames.length);
		//modules = pw.showModules();
		//System.out.println("number of modules: " + modules.length);
		modulesCBS = new JCheckBox[moduleNames.length];
		for (int i = 0; i < moduleNames.length; i++) {
			modulesCBS[i] = new JCheckBox(moduleNames[i]);
			modulesCBS[i].setBounds(30, i * 40, 200, 30);
			modulesPanel.add(modulesCBS[i]);
		}
	}

	@Override
	public void hide() {
		removeModuleFrame.setVisible(false);
	}

}