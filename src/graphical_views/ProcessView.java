package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ProcessView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame processFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JButton add;
	private JButton remove;
	private JButton update;
	private JButton assign;
	
	public ProcessView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		processFrame = new JFrame();
		processFrame.setBounds(150, 100, 600, 500);
		
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
		processFrame.add(logout);
		
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
		processFrame.add(return_);
		
		nameLabel = new JLabel("مدیریت �?رآیندها", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		processFrame.add(nameLabel);
		
		final View pv = this;
		
		add = new JButton("ا�?زودن");
		add.setFont(new Font(add.getFont().getName(), Font.PLAIN, 30));
		add.setBounds(350, 100, 200, 140);
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddProcessView addProcessView = new AddProcessView(pv, loginView);
				addProcessView.show();
			}
		});
		processFrame.add(add);
		
		remove = new JButton("حذ�?");
		remove.setFont(new Font(remove.getFont().getName(), Font.PLAIN, 30));
		remove.setBounds(50, 100, 200, 140);
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RemoveProcessView removeProcessView = new RemoveProcessView(pv, loginView);
				removeProcessView.show();
			}
		});
		processFrame.add(remove);
		
		update = new JButton("به‌روزرسانی");
		update.setFont(new Font(update.getFont().getName(), Font.PLAIN, 30));
		update.setBounds(350, 300, 200, 140);
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ChangeProcessView updateList = new ChangeProcessView(pv, loginView);
				updateList.show();
			}
		});
		processFrame.add(update);
		
		assign = new JButton("تخصیص");
		assign.setFont(new Font(assign.getFont().getName(), Font.PLAIN, 30));
		assign.setBounds(50, 300, 200, 140);
		assign.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AssignProcessView assignProcess = new AssignProcessView(pv, loginView);
				assignProcess.show();
			}
		});
		processFrame.add(assign);
	}

	@Override
	public void show() {
		processFrame.setLayout(null);
		processFrame.setVisible(true);
	}

	@Override
	public void hide() {
		processFrame.setVisible(false);
	}
}
