package software_system;

import java.sql.Date;
import java.util.Random;

import data.DBManagement;

public class Development extends Process {

	public Development(String projectName, Date from, Date to) {
		super(projectName, from, to, "DEVELOPMENT");
		super.id = generateId();
	}

	public Development(String projectName, Date from, Date to, int id) {
		super(projectName, from, to, "DEVELOPMENT");
		super.id = id;
	}

	@Override
	public boolean add() {
		DBManagement db = new DBManagement();
		String query = db.generateAddQuery("PROCESS",
				new String[] { String.valueOf(id), projectName, from.toString(), to.toString(), type });
		System.out.println(query);
		return db.update(query);
	}

	@Override
	public boolean remove() {
		DBManagement db = new DBManagement();
		String query = db.generateDeleteQuery("PROCESS", new String[] { Integer.toString(id) }, new String[] { "ID" });
		return db.update(query);
	}

	@Override
	public int generateId() {
		Random rand = new Random();
		int id = 100000000 + rand.nextInt(99999999);
		return id;
	}

	@Override
	public boolean update() {
		DBManagement db = new DBManagement();
		String query = db.generateUpdateQuery("PROCESS",
				new String[] { projectName, from.toString(), to.toString(), "DEVELOP" },
				new String[] { "PROJECTNAME", "FROM_DATE", "TO_DATE", "TYPE" }, new String[] { Integer.toString(id) },
				new String[] { "ID" });
		return db.update(query);
	}
}
