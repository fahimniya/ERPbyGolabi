package software_system;

import data.DBManagement;

public class SoftwareSystem {
	private int devId, maintenanceId;
	private String name;

	public SoftwareSystem(int devId, int maintenanceId, String name) {
		this.devId = devId;
		this.maintenanceId = maintenanceId;
		this.name = name;
	}

	public boolean add() {
		DBManagement db = new DBManagement();
		String query = db.generateAddQuery("SOFTWARESYSTEM",
				new String[] { name, Integer.toString(devId), Integer.toString(maintenanceId) });
		return db.update(query);
	}

	public boolean remove() {
		DBManagement db = new DBManagement();
		String query = db.generateDeleteQuery("SYSTEMSOFTWARE", new String[] { name }, new String[] { "NAME" });
		return db.update(query);
	}

	public boolean update(String oldName) {
		DBManagement db = new DBManagement();
		String query = db.generateUpdateQuery("SOFTWARESYSTEM",
				new String[] { name, Integer.toString(devId), Integer.toString(maintenanceId) },
				new String[] { "NAME", "DEVID", "MAINTENANCEID" }, new String[] { oldName }, new String[] { "NAME" });
		return db.update(query);
	}
}
