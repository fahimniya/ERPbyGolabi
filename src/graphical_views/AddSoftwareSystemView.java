package graphical_views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class AddSoftwareSystemView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame addSSFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JPanel technologiesPanel;
	private JScrollPane tpScroll;
	
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
		
		nameLabel = new JLabel("افزودن سیستم نرم‌افزاری", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		addSSFrame.add(nameLabel);
		
		technologiesPanel = new JPanel();
		technologiesPanel.setLayout(null);
		technologiesPanel.setBounds(50, 100, 300, 1000);
        tpScroll = new JScrollPane(technologiesPanel);
        tpScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tpScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tpScroll.setBounds(50, 100, 300, 300);
        addSSFrame.add(tpScroll);
//        JPanel contentPane = new JPanel(null);
//        contentPane.setPreferredSize(new Dimension(600, 550));
//        contentPane.add(tpScroll);
//        contentPane.add(nameLabel);
//        contentPane.add(logout);
//        contentPane.add(return_);
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
