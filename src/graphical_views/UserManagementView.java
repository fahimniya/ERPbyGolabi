package graphical_views;

import user_management.UserWrapper;

public class UserManagementView {
	State state;
	public UserManagementView(View rv, LoginView lv) {
		if(UserWrapper.getInstance().isManager())
			state = new ManagerState();
		else
			state = new UserState();
		state.showUserManagement(rv, lv);
		state.show();
	}
}
