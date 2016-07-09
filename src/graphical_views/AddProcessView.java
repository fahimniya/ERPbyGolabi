package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import software_system.ProcessWrapper;

public class AddProcessView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame addProcessFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JRadioButton developmentRB;
	private JRadioButton maintenanceRB;
	private JTextField processName;
	private JLabel processNameLabel;
	private JTextField from_year;
	private JTextField from_month;
	private JTextField from_day;
	private JLabel fromLabel;
	private JTextField to_year;
	private JTextField to_month;
	private JTextField to_day;
	private JLabel toLabel;
	private JButton addProcess;
	private JLabel message;
	
	public AddProcessView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		addProcessFrame = new JFrame();
		addProcessFrame.setBounds(150, 100, 600, 500);
		
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
		addProcessFrame.add(logout);
		
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
		addProcessFrame.add(return_);
		
		nameLabel = new JLabel("افزدون فرآیند", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		addProcessFrame.add(nameLabel);
		
		developmentRB = new JRadioButton("فرآیند ایجاد");
		developmentRB.setBounds(150, 100, 100, 30);
		developmentRB.setSelected(true);
		addProcessFrame.add(developmentRB);
		maintenanceRB = new JRadioButton("فرآیند نگهداری");
		maintenanceRB.setBounds(350, 100, 100, 30);
		addProcessFrame.add(maintenanceRB);
		ButtonGroup group = new ButtonGroup();
		group.add(developmentRB);
		group.add(maintenanceRB);
		
		processName = new JTextField();
		processName.setBounds(100, 150, 250, 30);
		addProcessFrame.add(processName);
		
		processNameLabel = new JLabel("نام فرآیند:");
		processNameLabel.setBounds(380, 150, 60, 30);
		addProcessFrame.add(processNameLabel);
		
		from_year = new JTextField();
		from_year.setBounds(100, 200, 60, 30);
		addProcessFrame.add(from_year);
		from_month = new JTextField();
		from_month.setBounds(195, 200, 60, 30);
		addProcessFrame.add(from_month);
		from_day = new JTextField();
		from_day.setBounds(290, 200, 60, 30);
		addProcessFrame.add(from_day);
		fromLabel = new JLabel("تاریخ شروع:");
		fromLabel.setBounds(380, 200, 60, 30);
		addProcessFrame.add(fromLabel);
		
		to_year = new JTextField();
		to_year.setBounds(100, 250, 60, 30);
		addProcessFrame.add(to_year);
		to_month = new JTextField();
		to_month.setBounds(195, 250, 60, 30);
		addProcessFrame.add(to_month);
		to_day = new JTextField();
		to_day.setBounds(290, 250, 60, 30);
		addProcessFrame.add(to_day);
		toLabel = new JLabel("تاریخ اتمام:");
		toLabel.setBounds(380, 250, 60, 30);
		addProcessFrame.add(toLabel);
		
		addProcess = new JButton("ایجاد فرآیند");
		addProcess.setBounds(180, 300, 90, 30);
		addProcess.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date from = new Date(Integer.parseInt(from_year.getText()), Integer.parseInt(from_month.getText()), Integer.parseInt(from_day.getText()));
				Date to = new Date(Integer.parseInt(to_year.getText()), Integer.parseInt(to_month.getText()), Integer.parseInt(to_day.getText()));

				ProcessWrapper pw = ProcessWrapper.getInstance();
				boolean success;
				if (developmentRB.isSelected())
					success = pw.addDevelopmentProcess(from, to, processName.getText());
				else
					success = pw.addMaintenanceProcess(from, to, processName.getText());
				if (success)
					message.setText("موفقیت‌آمیز");
				else
					message.setText("ناموفق");
			}
		});
		addProcessFrame.add(addProcess);
		
		message = new JLabel("", SwingConstants.CENTER);
		message.setBounds(100, 350, 250, 30);
		addProcessFrame.add(message);
	}
	
	@Override
	public void show() {
		addProcessFrame.setLayout(null);
		addProcessFrame.setVisible(true);
	}

	@Override
	public void hide() {
		addProcessFrame.setVisible(false);
	}

}
