package graphicalViews;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AddResourceView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame addResourceFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	
	public AddResourceView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		addResourceFrame = new JFrame();
		addResourceFrame.setBounds(150, 100, 600, 500);
		
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
		addResourceFrame.add(logout);
		
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
		addResourceFrame.add(return_);
		
		nameLabel = new JLabel("افزودن منبع", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		addResourceFrame.add(nameLabel);
		
	}
	
	@Override
	public void show() {
		addResourceFrame.setLayout(null);
		addResourceFrame.setVisible(true);
	}

	@Override
	public void hide() {
		addResourceFrame.setVisible(false);
	}

}