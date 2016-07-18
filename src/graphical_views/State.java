package graphical_views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public abstract class State implements View {
	protected View returnView;
	protected LoginView loginView;
	protected JFrame userManagementFrame;
	protected JLabel nameLabel;
	protected JButton logout;
	protected JButton return_;
	protected JButton info;
	protected JButton edit;
	protected JButton confirmChanges;
	protected JButton addUser;
	public abstract void showUserManagement(View rv, LoginView lv);
	@Override
	public void show() {
		userManagementFrame.setLayout(null);
		userManagementFrame.setVisible(true);
	}

	@Override
	public void hide() {
		userManagementFrame.setVisible(false);
	}
}
