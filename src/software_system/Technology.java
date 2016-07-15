package software_system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DBManagement;

public class Technology {
	private String tName;
	
	public String gettName() {
		return tName;
	}

	public Technology(String name) {
		tName = name;
	}
	
	public boolean addTechnology() {
		DBManagement db = new DBManagement();
		String query = db.generateAddQuery("TECHNOLOGY", new String[] {tName});
		return db.update(query);
	}
	
	public boolean updateTechnology(String name) {
		DBManagement db = new DBManagement();
		String query = db.generateUpdateQuery("TECHNOLOGY", new String[] {name}, new String[] {"TNAME"}, new String[] {tName}, new String[] {"TNAME"});
		return db.update(query);
	}
	
	public boolean deleteTechnology() {
		DBManagement db = new DBManagement();
		String query = db.generateDeleteQuery("TECHNOLOGY", new String[] {tName}, new String[] {"TNAME"});
		return db.update(query);
	}
	
	public Technology[] getAll() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("Technology", new String[] {"*"}, null, null);
		ResultSet rs = db.getQuery(query);
		ArrayList<Technology> techs = new ArrayList<Technology>();
		try {
			while(rs.next()) {
				techs.add(new Technology(rs.getString("TNAME")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return techs.toArray(new Technology[techs.size()]);
	}
}
