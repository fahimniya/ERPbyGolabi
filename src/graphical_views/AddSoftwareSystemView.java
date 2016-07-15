package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AddSoftwareSystemView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame addSSFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JLabel message; 
	
	public AddSoftwareSystemView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		addSSFrame = new JFrame();
		addSSFrame.setBounds(150, 100, 600, 500);
		
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
		addSSFrame.add(logout);
		
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
		addSSFrame.add(return_);
		
		nameLabel = new JLabel("افزدون سیستم نرم‌افزاری", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		addSSFrame.add(nameLabel);
		
		message = new JLabel("سیستم نرم‌افزاری مورد نظر موجود نیست، نخست باید آن را به سیستم‌های موجود بیفزایید", SwingConstants.CENTER);
		message.setBounds(0, 100, 600, 30);
		addSSFrame.add(message);
	}

	@Override
	public void show() {
		addSSFrame.setLayout(null);
		addSSFrame.setVisible(true);
	}

	@Override
	public void hide() {
		addSSFrame.setVisible(false);
	}

}
