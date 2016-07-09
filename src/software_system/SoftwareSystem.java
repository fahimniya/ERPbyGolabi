package software_system;

import data.DBManagement;

public class SoftwareSystem {
	private String name;

	public SoftwareSystem(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public boolean add() {
		DBManagement db = new DBManagement();
		String query = db.generateAddQuery("SOFTWARESYSTEM",
				new String[] { name });
		return db.update(query);
	}

	public boolean remove() {
		DBManagement db = new DBManagement();
		String query = db.generateDeleteQuery("SYSTEMSOFTWARE", new String[] { name }, new String[] { "NAME" });
		return db.update(query);
	}
}
