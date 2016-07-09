package ui;

import data.DBInitiation;
import graphical_views.LoginView;

public class ERP {
	public static void main(String[] args) {
		if (args[0].equals("Install")) {
			DBInitiation dbi = new DBInitiation();
			if (dbi.initiate())
				System.out.println("Successfully Installed");
			else 
				System.out.println("Failed to Install");
		}else if (args[0].equals("Run")){
			LoginView login = new LoginView();
			login.show(false);
		}
	}
}