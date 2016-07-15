package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import software_system.ProcessWrapper;
import software_system.Process;

public class UpdateProcessView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame updateProcessFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
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
	private JButton updateProcess;
	private JLabel message;
	
	public UpdateProcessView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		updateProcessFrame = new JFrame();
		updateProcessFrame.setBounds(150, 100, 600, 500);
		
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
		updateProcessFrame.add(logout);
		
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
		updateProcessFrame.add(return_);
		
		nameLabel = new JLabel("به‌روزرسانی فرآیند", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		updateProcessFrame.add(nameLabel);
		
		final Process process = ProcessWrapper.getInstance().getProcess();
		
		processName = new JTextField(process.getProjectName());
		processName.setBounds(100, 150, 250, 30);
		updateProcessFrame.add(processName);
		
		processNameLabel = new JLabel("نام فرآیند:");
		processNameLabel.setBounds(380, 150, 60, 30);
		updateProcessFrame.add(processNameLabel);
		
		from_year = new JTextField(process.getFrom().getYear());
		from_year.setBounds(100, 200, 60, 30);
		updateProcessFrame.add(from_year);
		from_month = new JTextField(process.getFrom().getMonth());
		from_month.setBounds(195, 200, 60, 30);
		updateProcessFrame.add(from_month);
		from_day = new JTextField(process.getFrom().getDay());
		from_day.setBounds(290, 200, 60, 30);
		updateProcessFrame.add(from_day);
		fromLabel = new JLabel("تاریخ شروع:");
		fromLabel.setBounds(380, 200, 60, 30);
		updateProcessFrame.add(fromLabel);
		
		to_year = new JTextField(process.getTo().getYear());
		to_year.setBounds(100, 250, 60, 30);
		updateProcessFrame.add(to_year);
		to_month = new JTextField(process.getTo().getMonth());
		to_month.setBounds(195, 250, 60, 30);
		updateProcessFrame.add(to_month);
		to_day = new JTextField(process.getTo().getDay());
		to_day.setBounds(290, 250, 60, 30);
		updateProcessFrame.add(to_day);
		toLabel = new JLabel("تاریخ اتمام:");
		toLabel.setBounds(380, 250, 60, 30);
		updateProcessFrame.add(toLabel);
		
		updateProcess = new JButton("ویرایش فرآیند");
		updateProcess.setBounds(180, 300, 90, 30);
		updateProcess.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date from = new Date(Integer.parseInt(from_year.getText()) - 1900, Integer.parseInt(from_month.getText()) - 1, Integer.parseInt(from_day.getText()));
				Date to = new Date(Integer.parseInt(to_year.getText()) - 1900, Integer.parseInt(to_month.getText()) - 1, Integer.parseInt(to_day.getText()));

				ProcessWrapper pw = ProcessWrapper.getInstance();
				process.setProjectName(processName.getText());
				process.setFrom(from);
				process.setTo(to);
				boolean success = pw.updateProcess(process);
				if (success)
					message.setText("موفقیت‌آمیز");
				else
					message.setText("ناموفق");
			}
		});
		updateProcessFrame.add(updateProcess);
		
		message = new JLabel("", SwingConstants.CENTER);
		message.setBounds(100, 350, 250, 30);
		updateProcessFrame.add(message);
	}
	
	@Override
	public void show() {
		updateProcessFrame.setLayout(null);
		updateProcessFrame.setVisible(true);
	}

	@Override
	public void hide() {
		updateProcessFrame.setVisible(false);
	}

}