package UI;

import Items.ManagerWrapper;
import graphicalViews.LoginView;

public class ERP {
	public static void main(String[] args) {
		ManagerWrapper mw = new ManagerWrapper();
		mw.addUser("ali", "Ali", "[C@158b7f4");
		LoginView login = new LoginView();
		login.show(false);
	}
}
