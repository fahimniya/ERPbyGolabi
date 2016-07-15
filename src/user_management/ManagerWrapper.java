package user_management;

import java.sql.ResultSet;
import java.util.ArrayList;

import data.DBManagement;

public class ManagerWrapper extends UserWrapper {
	private static ManagerWrapper uniqueInstance;

	private ManagerWrapper() {
	}

	public static ManagerWrapper getInstance() {
		if (uniqueInstance == null)
			return (uniqueInstance = new ManagerWrapper());
		else
			return uniqueInstance;
	}

	public boolean removeUser(String username) {
		User user = new User(username, null, null);
		return user.remove(username);
	}

	public boolean addUser(User user) {
		return user.add();
	}

	public boolean updateUser(String oldUsername, String username, String name, String password, String role) {
		User user = new User(username, password, name);
		return user.update(oldUsername, role);
	}

	public User[] showPending() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("USER", new String[] { "*" }, null, null);
		ResultSet rs = db.getQuery(query);
		ArrayList<User> users = new ArrayList<User>();
		try {
			while (rs.next()) {
				users.add(new User(rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("NAME"),
						rs.getString("ROLE")));
			}
		} catch (Exception e) {
			return null;
		}
		return users.toArray(new User[users.size()]);
	}

	public boolean approveEdit(User[] users) {
		boolean flag = true;
		for(int i = 0; i < users.length; i++) {
			flag = flag && users[i].add();
		}
		return flag;
	}
}
