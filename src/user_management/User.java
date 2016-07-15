package user_management;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DBManagement;

public class User {

	private String username;
	private String password;
	private String name;
	private String role;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, String name) {
		this.name = name;
		this.username = username;
		this.password = password;
		role = "NORMAL";
	}

	public User(String username, String password, String name, String role) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public boolean authenticate() {
		DBManagement db = new DBManagement();

		String query = "select * from USER where USERNAME = \"" + username + "\" and PASSWORD = \"" + password + "\";";
		ResultSet result = db.getQuery(query);
		try {
			result.next();
			System.out.println(result.getString("USERNAME"));
			UserWrapper userWrapper = UserWrapper.getInstance();
			userWrapper.setUsername(username);
			userWrapper.setManager(result.getString("ROLE").equals("MANAGER"));
			return true;
		} catch (SQLException e) {
		}
		return false;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean addToPendingUpdate(String oldUsername) {
		DBManagement db = new DBManagement();

		String query = "insert into PENDINGUPDATE values(\"" + oldUsername + "\", \"" + username + "\", \"" + password
				+ "\", \"" + name + "\", \"NORMAL\");";

		return db.update(query);
	}

	public boolean add() {
		String query = "insert into USER values(\"" + username + "\", \"" + password + "\", \"" + name
				+ "\", \"NORMAL\");";
		DBManagement db = new DBManagement();
		return db.update(query);
	}

	public boolean remove(String username) {
		String query = "select * from USER where USERNAME = \"" + username + "\" ;";
		DBManagement db = new DBManagement();
		if (db.getQuery(query) == null)
			return false;
		query = "delete from USER where USERNAME = \"" + username + "\" ;";
		return db.update(query);
	}

	public boolean update(String oldUsername, String role) {
		DBManagement db = new DBManagement();
		String query = db.generateUpdateQuery("USER", new String[] { username, password, name, role },
				new String[] { "USERNAME", "PASSWORD", "NAME", "ROLE" }, new String[] { oldUsername },
				new String[] { "USERNAME" });
		this.role = role;
		return db.update(query);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}