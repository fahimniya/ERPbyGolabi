package user_management;

public class ManagerWrapper extends UserWrapper {
	public boolean removeUser (String username) {
		User user = new User(username, null, null);
		return user.remove(username);
	}
	
	public boolean addUser (String username, String name, String password) {
		User user = new User(username, password, name);
		return user.add(username, password, name);
	}
}
