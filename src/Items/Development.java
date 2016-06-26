package Items;

import java.sql.Date;
import java.util.Random;

import Data.DatabaseInterface;

public class Development extends Process {

	public Development(String projectName, Date from, Date to) {
		super(projectName, from, to);
		super.id = generateId();
	}

	public Development(String projectName, Date from, Date to, int id) {
		super(projectName, from, to);
		super.id = id;
	}

	@Override
	public boolean add() {
		DatabaseInterface db = new DatabaseInterface();
		String query = db.generateAddQuery("DEVELOP",
				new String[] { String.valueOf(id), projectName, from.toString(), to.toString() });
		return db.update(query);
	}

	@Override
	public boolean remove() {
		DatabaseInterface db = new DatabaseInterface();
		String query = db.generateDeleteQuery("DEVELOP", new String[] { Integer.toString(id) }, new String[] { "ID" });
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
		DatabaseInterface db = new DatabaseInterface();
		String query = db.generateUpdateQuery("DEVELOP", new String[] { projectName, from.toString(), to.toString() },
				new String[] { "PROJECTNAME", "FROM", "TO" }, new String[] { Integer.toString(id) },
				new String[] { "ID" });
		return db.update(query);
	}
}
