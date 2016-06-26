package Items;

import java.sql.ResultSet;
import java.sql.SQLException;

import Data.DatabaseInterface;

public class UserWrapper {

	public boolean login(String username, String password) {
		User user = new User(username, password);
		boolean isValid = user.authenticate();
		return isValid;
	}

	public User getAccountInformation(String username) {
		String name = null, password = null;

		DatabaseInterface db = new DatabaseInterface();
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