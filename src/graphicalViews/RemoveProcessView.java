package graphicalViews;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class RemoveProcessView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame removeProcessFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	
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
		
		nameLabel = new JLabel("حذف فرآیند", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		removeProcessFrame.add(nameLabel);
		
	}
	
	@Override
	public void show() {
		removeProcessFrame.setLayout(null);
		removeProcessFrame.setVisible(true);
	}

	@Override
	public void hide() {
		removeProcessFrame.setVisible(false);
	}

}