package user_management;

public class ManagerWrapper extends UserWrapper {
	private static ManagerWrapper uniqueInstance;
	
	private ManagerWrapper() {}
	
	public static ManagerWrapper getInstance() {
		if (uniqueInstance == null)
			return (uniqueInstance = new ManagerWrapper());
		else
			return uniqueInstance;
	}
	
	public boolean removeUser (String username) {
		User user = new User(username, null, null);
		return user.remove(username);
	}
	
	public boolean addUser (String username, String name, String password) {
		User user = new User(username, password, name);
		return user.add(username, password, name);
	}
	
	public boolean updateUser(String oldUsername, String username, String name, String password, String role) {
		User user = new User(username, password, name);
		return user.update(oldUsername, role);
	}
}
