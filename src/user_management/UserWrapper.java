package user_management;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DBManagement;

public class UserWrapper {
	private static UserWrapper uniqueInstance;
	
	public UserWrapper(){};
	
	public static UserWrapper getInstance() {
		if (uniqueInstance == null)
			return (uniqueInstance = new UserWrapper());
		else
			return uniqueInstance;
	}

	public boolean login(String username, String password) {
		User user = new User(username, password);
		boolean isValid = user.authenticate();
		return isValid;
	}

	public User getAccountInformation(String username) {
		String name = null, password = null;

		DBManagement db = new DBManagement();
		String query = "select * from USER where USERNAME = \"" + username + "\";";
		ResultSet result = db.getQuery(query);
		try {
			while (result.next()) {
				name = result.getString("NAME");
				password = result.getString("PASSWORD");
			}
		} catch (SQLException e) {
			return null;
		}
		try {
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		User user = new User(username, password, name);
		return user;
	}
	
	public void editAccountInformation(String oldUsername, String username, String name, String password) {
		User user = new User(username, password, name);
		
		user.addToPendingUpdate(oldUsername);
	}

}