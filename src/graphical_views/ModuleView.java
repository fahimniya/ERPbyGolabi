package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ModuleView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame moduleFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JButton add;
	private JButton remove;
	
	public ModuleView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		moduleFrame = new JFrame();
		moduleFrame.setBounds(150, 100, 600, 350);
		
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
		moduleFrame.add(logout);
		
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
		moduleFrame.add(return_);
		
		nameLabel = new JLabel("مدیریت ماژول‌ها", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		moduleFrame.add(nameLabel);
		
		final View mov = this;
		
		add = new JButton("افزودن");
		add.setFont(new Font(add.getFont().getName(), Font.PLAIN, 30));
		add.setBounds(350, 100, 200, 140);
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddModuleView addModuleView = new AddModuleView(mov, loginView);
				addModuleView.show();
			}
		});
		moduleFrame.add(add);
		
		remove = new JButton("حذف");
		remove.setFont(new Font(remove.getFont().getName(), Font.PLAIN, 30));
		remove.setBounds(50, 100, 200, 140);
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//RemoveModuleView removeModuleView = new RemoveModuleView(mov, loginView);
				//removeModuleView.show();
			}
		});
		moduleFrame.add(remove);
	}

	@Override
	public void show() {
		moduleFrame.setLayout(null);
		moduleFrame.setVisible(true);
	}

	@Override
	public void hide() {
		moduleFrame.setVisible(false);
	}
}
