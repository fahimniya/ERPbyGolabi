package user_management;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public User[] showUsers() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("USER", new String[] {"*"}, null, null);
		ResultSet rs = db.getQuery(query);
		ArrayList<User> users = new ArrayList<User>();
		try {
			while(rs.next()) {
				users.add(new User(rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("NAME"), rs.getString("ROLE")));
			}
		}catch(Exception e) {
			return null;
		}
		return (users.toArray(new User[users.size()]));
	}
	
	public boolean editAccountInformation(String oldUsername, String username, String name, String password) {
		User user = new User(username, password, name);
		
		return user.addToPendingUpdate(oldUsername);
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