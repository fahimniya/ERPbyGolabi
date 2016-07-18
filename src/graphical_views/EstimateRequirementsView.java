package graphical_views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import software_system.ProcessWrapper;
import software_system.Technology;
import software_system.estimation.Estimator;

public class EstimateRequirementsView implements View {
	private View returnView;
	private LoginView loginView;
	private JFrame estimateResourceFrame;
	private JLabel nameLabel;
	private JButton logout;
	private JButton return_;
	private JPanel technologiesPanel;
	private Technology[] technologies;
	private ArrayList<JCheckBox> technologiesCBS;
	private JScrollPane tpScroll;
	private JLabel technologiesLabel;
	private JTextField humanSize;
	private JLabel humanSizeLabel;
	private JTextField moduleSize;
	private JLabel moduleSizeLabel;
	private JButton estimate;
	
	public EstimateRequirementsView(View rv, LoginView lv) {
		returnView = rv;
		loginView = lv;
		returnView.hide();
		
		estimateResourceFrame = new JFrame();
		estimateResourceFrame.setBounds(150, 100, 600, 600);
		
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
		estimateResourceFrame.add(logout);
		
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
		estimateResourceFrame.add(return_);
		
		nameLabel = new JLabel("تخمین منابع", SwingConstants.CENTER);
		nameLabel.setBounds(0, 35, 600, 45);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 40));
		estimateResourceFrame.add(nameLabel);
		
		technologiesPanel = new JPanel();
		technologiesPanel.setLayout(null);
		technologiesPanel.setBounds(100, 100, 300, 1000);
        tpScroll = new JScrollPane(technologiesPanel);
        tpScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tpScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tpScroll.setBounds(100, 100, 300, 300);
        estimateResourceFrame.add(tpScroll);
        
        technologiesCBS = new ArrayList<>();
        
        technologiesLabel = new JLabel("فناوری‌ها:");
        technologiesLabel.setBounds(450, 100, 100, 30);
        estimateResourceFrame.add(technologiesLabel);
		
        humanSize = new JTextField("0");
        humanSize.setBounds(100, 420, 300, 30);
        estimateResourceFrame.add(humanSize);
        humanSizeLabel = new JLabel("تعداد انسان:");
        humanSizeLabel.setBounds(450, 420, 100, 30);
        estimateResourceFrame.add(humanSizeLabel);
        
        moduleSize = new JTextField("0");
        moduleSize.setBounds(100, 470, 300, 30);
        estimateResourceFrame.add(moduleSize);
        moduleSizeLabel = new JLabel("تعداد انسان:");
        moduleSizeLabel.setBounds(450, 470, 100, 30);
        estimateResourceFrame.add(moduleSizeLabel);
        
        final View erv = this;
        
        estimate = new JButton("تخمین");
        estimate.setBounds(200, 520, 100, 30);
        estimate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Technology> usedTechnologies = new ArrayList<>();
				for (int i = 0; i < technologiesCBS.size(); i++)
					if (technologiesCBS.get(i).isSelected())
						usedTechnologies.add(technologies[i]);
				int hSize = Integer.parseInt(humanSize.getText());
				int mSize = Integer.parseInt(moduleSize.getText());
				new RequirementsReportView(erv, loginView, null, new Estimator().estimateNeededResource(!usedTechnologies.isEmpty(), usedTechnologies.toArray(new Technology[usedTechnologies.size()]), hSize != 0, hSize, mSize != 0, mSize)).show();
			}
		});
        estimateResourceFrame.add(estimate);
	}
	
	@Override
	public void show() {
		estimateResourceFrame.setLayout(null);
		estimateResourceFrame.setVisible(true);
		
		technologies = ProcessWrapper.getInstance().showTechnologies();
		for (int i = 0; i < technologies.length; i++) {
			JCheckBox newCB = new JCheckBox(technologies[i].gettName());
			newCB.setBounds(30, i * 40, 200, 30);
			technologiesCBS.add(newCB);
			technologiesPanel.add(newCB);
		}
	}

	@Override
	public void hide() {
		estimateResourceFrame.setVisible(false);
	}

}
