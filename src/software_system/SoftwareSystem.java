package software_system;

import data.DBManagement;

public class SoftwareSystem {
	private String name, technology, description;

	public SoftwareSystem(String name, String technology, String description) {
		this.name = name;
		this.technology = technology;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}

	public String getTechnology() {
		return technology;
	}

	public String getDescription() {
		return description;
	}

	public boolean add() {
		DBManagement db = new DBManagement();
		String query = db.generateAddQuery("SOFTWARESYSTEM",
				new String[] { name, technology, description });
		return db.update(query);
	}

	public boolean remove() {
		DBManagement db = new DBManagement();
		String query = db.generateDeleteQuery("SYSTEMSOFTWARE", new String[] { name }, new String[] { "NAME" });
		return db.update(query);
	}
	
}
