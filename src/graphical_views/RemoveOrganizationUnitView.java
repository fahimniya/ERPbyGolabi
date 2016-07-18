package graphical_views;

import java.awt.Dimension;
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
import javax.swing.SwingConstants;

import software_system.ProcessWrapper;

public class RemoveOrganizationUnitView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame removeouFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JPanel ousPanel;
	private JScrollPane opScroll;
	private JCheckBox[] ousCBS;
	private JButton removeModule;
	
	private ProcessWrapper pw;
	private String[] moduleNames;
	
	public RemoveOrganizationUnitView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		removeouFrame = new JFrame();
		removeouFrame.setBounds(150, 100, 600, 500);
		
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
		removeouFrame.add(logout);
		
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
		removeouFrame.add(return_);
		
		nameLabel = new JLabel("حذف ماژول‌ها", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		removeouFrame.add(nameLabel);
		
		removeModule = new JButton("حذف ماژول‌های انتخاب‌شده");
		removeModule.setBounds(220, 450, 160, 30);
		removeModule.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> removeModules = new ArrayList<>();
				for (int i = 0; i < moduleNames.length; i++)
					if (ousCBS[i].isSelected())
						removeModules.add(moduleNames[i]);
					ProcessWrapper.getInstance().removeModule(removeModules.toArray(new String[removeModules.size()]));
				hide();
				returnView.show();
			}
		});
		
		ousPanel = new JPanel();
		ousPanel.setLayout(null);
		ousPanel.setBounds(50, 130, 500, 1000);
        opScroll = new JScrollPane(ousPanel);
        opScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        opScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        opScroll.setBounds(50, 130, 500, 300);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(600, 550));
        contentPane.add(opScroll);
        contentPane.add(nameLabel);
        contentPane.add(removeModule);
        contentPane.add(logout);
        contentPane.add(return_);
        removeouFrame.setContentPane(contentPane);
        removeouFrame.pack();
        removeouFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        removeouFrame.setVisible(true);
	}
	
	@Override
	public void show() {
		removeouFrame.setLayout(null);
		removeouFrame.setVisible(true);
		
		pw = ProcessWrapper.getInstance();
		moduleNames = pw.showModuleName();
		System.out.println("number of modules: " + moduleNames.length);
		//modules = pw.showModules();
		//System.out.println("number of modules: " + modules.length);
		ousCBS = new JCheckBox[moduleNames.length];
		for (int i = 0; i < moduleNames.length; i++) {
			ousCBS[i] = new JCheckBox(moduleNames[i]);
			ousCBS[i].setBounds(30, i * 40, 200, 30);
			ousPanel.add(ousCBS[i]);
		}
	}

	@Override
	public void hide() {
		removeouFrame.setVisible(false);
	}

}