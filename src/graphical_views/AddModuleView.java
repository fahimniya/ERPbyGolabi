package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import software_system.ProcessWrapper;
import software_system.SoftwareSystem;

public class AddModuleView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame addModuleFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JComboBox<String> moduleName;
	private JLabel moduleNameLabel;
	private JComboBox<String> softwareSystemName;
	private JLabel softwareSystemNameLabel;
	private JTextField newModuleName;
	private JLabel newModuleNameLabel;
	private boolean addingNewModule;
	private JButton continueAddModule;
	
	
	public AddModuleView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		addingNewModule = false;
		
		addModuleFrame = new JFrame();
		addModuleFrame.setBounds(150, 100, 600, 380);
		
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
		addModuleFrame.add(logout);
		
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
		addModuleFrame.add(return_);
		
		nameLabel = new JLabel("افزودن ماژول", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		addModuleFrame.add(nameLabel);
		
		moduleName = new JComboBox<>();
		moduleName.setBounds(100, 150, 250, 30);
		final String[] moduleNames = ProcessWrapper.getInstance().showModuleName();
		for (String name: moduleNames)
			moduleName.addItem(name);
		moduleName.addItem("اضافه کردن ماژول جدید...");
		moduleName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (moduleName.getSelectedIndex() == moduleName.getItemCount() - 1) {
					addingNewModule = true;
					addModuleFrame.add(newModuleName);
					addModuleFrame.add(newModuleNameLabel);
					continueAddModule.setBounds(180, 300, 90, 30);
					addModuleFrame.repaint();
				}
				else if (addingNewModule) {
					addingNewModule = false;
					addModuleFrame.remove(newModuleName);
					addModuleFrame.remove(newModuleNameLabel);
					continueAddModule.setBounds(180, 250, 90, 30);
					addModuleFrame.repaint();
				}
			}
		});
		addModuleFrame.add(moduleName);
		
		moduleNameLabel = new JLabel("نام ماژول:");
		moduleNameLabel.setBounds(380, 150, 60, 30);
		addModuleFrame.add(moduleNameLabel);
		
		softwareSystemName = new JComboBox<>();
		softwareSystemName.setBounds(100, 200, 250, 30);
		SoftwareSystem[] softwareSystems = ProcessWrapper.getInstance().showSoftwareSystem();
		for (SoftwareSystem softwareSystem: softwareSystems)
			softwareSystemName.addItem(softwareSystem.getName());
		addModuleFrame.add(softwareSystemName);
		
		softwareSystemNameLabel = new JLabel("نام سیستم نرم‌افزاری:");
		softwareSystemNameLabel.setBounds(380, 200, 90, 30);
		addModuleFrame.add(softwareSystemNameLabel);
		
		newModuleName = new JTextField();
		newModuleName.setBounds(100, 250, 250, 30);
		newModuleNameLabel = new JLabel("نام ماژول جدید:");
		newModuleNameLabel.setBounds(380, 250, 90, 30);
		
		final View amv = this;
		
		continueAddModule = new JButton("ادامه");
		continueAddModule.setBounds(180, 250, 90, 30);
		continueAddModule.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ChooseResourceForModule chooseResourceForModule = new ChooseResourceForModule(amv, loginView, addingNewModule? newModuleName.getText() : (String) moduleName.getSelectedItem(), (String) softwareSystemName.getSelectedItem());
				chooseResourceForModule.show();
			}
		});
		addModuleFrame.add(continueAddModule);
	}
	
	@Override
	public void show() {
		addModuleFrame.setLayout(null);
		addModuleFrame.setVisible(true);
	}

	@Override
	public void hide() {
		addModuleFrame.setVisible(false);
	}

}
