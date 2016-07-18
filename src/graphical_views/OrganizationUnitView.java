package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class OrganizationUnitView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame ouFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JButton add;
	private JButton remove;
	
	public OrganizationUnitView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		ouFrame = new JFrame();
		ouFrame.setBounds(150, 100, 600, 500);
		
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
		ouFrame.add(logout);
		
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
		ouFrame.add(return_);
		
		nameLabel = new JLabel("مدیریت فرآیندها", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		ouFrame.add(nameLabel);
		
		final View pv = this;
		
		add = new JButton("افزودن");
		add.setFont(new Font(add.getFont().getName(), Font.PLAIN, 30));
		add.setBounds(350, 100, 200, 140);
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddOrganizaionUnitView addOUView = new AddOrganizaionUnitView(pv, loginView);
				addOUView.show();
			}
		});
		ouFrame.add(add);
		
		remove = new JButton("حذف");
		remove.setFont(new Font(remove.getFont().getName(), Font.PLAIN, 30));
		remove.setBounds(50, 100, 200, 140);
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RemoveProcessView removeProcessView = new RemoveProcessView(pv, loginView);
				removeProcessView.show();
			}
		});
		ouFrame.add(remove);
	}

	@Override
	public void show() {
		ouFrame.setLayout(null);
		ouFrame.setVisible(true);
	}

	@Override
	public void hide() {
		ouFrame.setVisible(false);
	}
}
