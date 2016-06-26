package graphicalViews;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ResourceView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame resourceFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JButton add;
	private JButton remove;
//	private JButton update;
	private JButton assign;
	
	public ResourceView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		resourceFrame = new JFrame();
		resourceFrame.setBounds(150, 100, 600, 500);
		
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
		resourceFrame.add(logout);
		
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
		resourceFrame.add(return_);
		
		nameLabel = new JLabel("مدیریت منابع", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		resourceFrame.add(nameLabel);
		
		final View resourcev = this;
		
		add = new JButton("افزودن");
		add.setFont(new Font(add.getFont().getName(), Font.PLAIN, 30));
		add.setBounds(350, 100, 200, 140);
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddResourceView addResourceView = new AddResourceView(resourcev, loginView);
				addResourceView.show();
			}
		});
		resourceFrame.add(add);
		
		remove = new JButton("حذف");
		remove.setFont(new Font(remove.getFont().getName(), Font.PLAIN, 30));
		remove.setBounds(50, 100, 200, 140);
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RemoveResourceView removeResourceView = new RemoveResourceView(resourcev, loginView);
				removeResourceView.show();
			}
		});
		resourceFrame.add(remove);
		
//		update = new JButton("به‌روزرسانی");
//		update.setFont(new Font(update.getFont().getName(), Font.PLAIN, 30));
//		update.setBounds(350, 300, 200, 140);
//		update.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				ChangeresourceView updateList = new ChangeresourceView(resourcev, loginView);
//				updateList.show();
//			}
//		});
//		resourceFrame.add(update);
		
		assign = new JButton("تخصیص");
		assign.setFont(new Font(assign.getFont().getName(), Font.PLAIN, 30));
		assign.setBounds(200, 300, 200, 140);
		assign.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AssignResourceView assignResource = new AssignResourceView(resourcev, loginView);
				assignResource.show();
			}
		});
		resourceFrame.add(assign);
	}

	@Override
	public void show() {
		resourceFrame.setLayout(null);
		resourceFrame.setVisible(true);
	}

	@Override
	public void hide() {
		resourceFrame.setVisible(false);
	}
}
