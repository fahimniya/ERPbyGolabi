package Items;

import java.sql.ResultSet;

import Data.DatabaseInterface;

public class User {

	private String username;
	private String password;
	private String name;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, String name) {
		this.name = name;
		this.username = username;
		this.password = password;

	}

	public boolean authenticate() {
		DatabaseInterface db = new DatabaseInterface();

		String query = "select username, password from USER where USERNAME = \"" + username + "\" and PASSWORD = \""
				+ password + "\";";
		ResultSet result = db.getQuery(query);
		if (result == null)
			return false;
		return true;
	}

	public boolean addToPendingUpdate(String oldUsername) {
		DatabaseInterface db = new DatabaseInterface();

		String query = "insert into PENDINGUPDATE values(\"" + oldUsername + "\", \"" + username + "\", \"" + password
				+ "\", \"" + name + "\");";
		return db.update(query);
	}

	public boolean add(String username, String password, String name) {
		String query = "insert into USER values(\"" + username + "\", \"" + password + "\", \"" + name + "\");";
		DatabaseInterface db = new DatabaseInterface();
		return db.update(query);
	}

	public boolean remove(String username) {
		String query = "select * from USER where USERNAME = \"" + username + "\" ;";
		DatabaseInterface db = new DatabaseInterface();
		if (db.getQuery(query) == null)
			return false;
		query = "delete from USER where USERNAME = \"" + username + "\" ;";
		return db.update(query);
	}

	// public boolean banUser() {
	//
	// }

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