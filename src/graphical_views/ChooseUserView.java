package graphical_views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import user_management.User;
import user_management.UserWrapper;

public class ChooseUserView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame chooseUserFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JPanel usersPanel;
	private JScrollPane upScroll;
	private JRadioButton[] usersRBS;
	private JButton editUser;
	
	private UserWrapper uw;
	private User[] users;
	
	public ChooseUserView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		chooseUserFrame = new JFrame();
		chooseUserFrame.setBounds(150, 100, 600, 550);
		
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
		chooseUserFrame.add(logout);
		
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
		chooseUserFrame.add(return_);
		
		nameLabel = new JLabel("ویرایش اطلاعات: انتخاب کاربر", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		
		final View cpv = this;
		
		editUser = new JButton("ویرایش کاربر");
		editUser.setBounds(250, 450, 100, 30);
		editUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				User user = users[0];
				for (int i = 0; i < users.length; i++)
					if (usersRBS[i].isSelected()) {
						user = users[i];
						break;
					}
				EditUserView updateProcessView = new EditUserView(cpv, loginView, user);
				updateProcessView.show();
			}
		});
		
		usersPanel = new JPanel();
		usersPanel.setLayout(null);
        upScroll = new JScrollPane(usersPanel);
        upScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        upScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        upScroll.setBounds(50, 130, 500, 300);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(600, 550));
        contentPane.add(upScroll);
        contentPane.add(nameLabel);
        contentPane.add(editUser);
        contentPane.add(logout);
        contentPane.add(return_);
        chooseUserFrame.setContentPane(contentPane);
        chooseUserFrame.pack();
        chooseUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chooseUserFrame.setVisible(true);
	}
	
	@Override
	public void show() {
		chooseUserFrame.setLayout(null);
		chooseUserFrame.setVisible(true);
		
		uw = UserWrapper.getInstance();
		users = uw.showUsers();
		System.out.println(users.length);
		usersRBS = new JRadioButton[users.length];
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < users.length; i++) {
			usersRBS[i] = new JRadioButton(users[i].getUsername());
			usersRBS[i].setBounds(30, i * 40, 200, 30);
			group.add(usersRBS[i]);
			usersPanel.add(usersRBS[i]);
		}
		usersRBS[0].setSelected(true);
	}

	@Override
	public void hide() {
		chooseUserFrame.setVisible(false);
	}

}
