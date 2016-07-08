package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ChangeProcessView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame updateProcessListFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	
	public ChangeProcessView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		updateProcessListFrame = new JFrame();
		updateProcessListFrame.setBounds(150, 100, 600, 500);
		
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
		
		nameLabel = new JLabel("لیست �?رآیندها", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		updateProcessListFrame.add(nameLabel);
		
	}
	
	@Override
	public void show() {
		updateProcessListFrame.setLayout(null);
		updateProcessListFrame.setVisible(true);
	}

	@Override
	public void hide() {
		updateProcessListFrame.setVisible(false);
	}

}