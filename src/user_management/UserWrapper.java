package user_management;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DBManagement;

public class UserWrapper {
	private static UserWrapper uniqueInstance;
	private String username;
	private boolean isManager;
	
	protected UserWrapper(){};
	
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}

}