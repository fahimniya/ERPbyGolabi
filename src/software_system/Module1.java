package software_system;

import data.DBManagement;

public class Module1 {
	private String moduleName, projectName;

	public Module1(String moduleName, String projectName) {
		this.moduleName = moduleName;
		this.projectName = projectName;
	}

	public boolean add() {
		DBManagement db = new DBManagement();
		String query = db.generateAddQuery("MODULE", new String[] { moduleName, projectName });
		return db.update(query);
	}

	public boolean remove() {
		DBManagement db = new DBManagement();
		String query = db.generateDeleteQuery("MODULE", new String[] { moduleName, projectName },
				new String[] { "MODULENAME", "PROJECTNAME" });
		return db.update(query);
	}

	public boolean update(String oldmName, String oldpName) {
		DBManagement db = new DBManagement();
		String query = db.generateUpdateQuery("MODULE", new String[] { moduleName, projectName },
				new String[] { "MODULENAME", "PROJECTNAME" }, new String[] { oldmName, oldpName },
				new String[] { "MODULENAME", "PROJECTNAME" });
		return db.update(query);
	}

}
