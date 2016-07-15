package software_system;

import data.DBManagement;

public class SoftwareSystem {
	private String name, description;
	private Technology[] technology;

	public SoftwareSystem(String name, Technology[] technology, String description) {
		this.name = name;
		this.technology = technology;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public Technology[] getTechnologys() {
		return technology;
	}

	public String getDescription() {
		return description;
	}

	public boolean add() {
		DBManagement db = new DBManagement();
		String addQuery[] = new String[technology.length + 1];
		String deleteQuery[] = new String[technology.length + 1];
		addQuery[0] = db.generateAddQuery("SOFTWARESYSTEM",
				new String[] { name, description });
		deleteQuery[0] = db.generateDeleteQuery("SOFTWARESYSTEM", new String[] {name}, new String[] {"NAME"});
		if(!db.update(addQuery[0]))
			return false;
		for(int i=0; i<technology.length; i++) {
			addQuery[i+1] = db.generateAddQuery("SOFTECH", new String[] {name, technology[i].gettName()});
			deleteQuery[i+1] = db.generateDeleteQuery("SOFTECH", new String[] {name, technology[i].gettName()}, new String[] {"SNAME", "TNAME"});
		}
		boolean flag = true;
		for(int i=1; i<addQuery.length; i++) {
			if(!db.update(addQuery[i])) {
				for(int j=0; j<i; j++) {
					do{}while(!db.update(deleteQuery[j]));
					flag = false;
				}
			}
		}
		return flag;
	}

	public boolean remove() {
		DBManagement db = new DBManagement();
		String query = db.generateDeleteQuery("SYSTEMSOFTWARE", new String[] { name }, new String[] { "NAME" });
		return db.update(query);
	}
}
