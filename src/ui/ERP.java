package ui;

import graphical_views.LoginView;

public class ERP {
	public static void main(String[] args) {
		//ManagerWrapper mw = new ManagerWrapper();
		//DBInitiation db = new DBInitiation();
		//System.out.println(db.initiate());
		//mw.addUser("ali", "Ali", "[C@1a478d1");
		LoginView login = new LoginView();
		login.show(false);
	}
}
