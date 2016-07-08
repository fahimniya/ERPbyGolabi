package software_system;

import Data.DBManagement;

public class SoftwareOrganization {
	private int oid;
	private String softwareSystemName;

	public SoftwareOrganization(int oid, String softwareSystemName) {
		this.oid = oid;
		this.softwareSystemName = softwareSystemName;
	}

	public boolean add() {
		DBManagement db = new DBManagement();
		String query = db.generateAddQuery("SOFTWARESYSTEM_ORGANIZATIONUNIT",
				new String[] { Integer.toString(oid), softwareSystemName });
		return db.update(query);
	}

	public boolean remove() {
		DBManagement db = new DBManagement();
		String query = db.generateDeleteQuery("SOFTWARESYSTEM_ORGANIZATIONUNIT",
				new String[] { Integer.toString(oid), softwareSystemName }, new String[] { "OID", "SSNAME" });
		return db.update(query);
	}
}
