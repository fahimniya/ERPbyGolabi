package resources;

import java.util.Random;

import Data.DBManagement;

public class FacilityResource extends Resource {
	private int id;
	private String name;
	
	public FacilityResource(String name, int id) {
		this.id = id;
		this.name = name;
	}
	
	public FacilityResource(String name) {
		id = generateID();
		this.name = name;
	}
	@Override
	public boolean addToDB() {
		DBManagement db = new DBManagement();
		String query = db.generateAddQuery("facilityresource".toUpperCase(),  new String[]{Integer.toString(id), name});
		return db.update(query);
	}

	@Override
	public boolean removeFromDB() {
		DBManagement db = new DBManagement();
		String query = db.generateDeleteQuery("facilityresource".toUpperCase(),  new String[]{Integer.toString(id)}, new String[]{"ID"});
		return db.update(query);
	}

	public int generateID() {
		Random rand = new Random();
		int id = 400000000 + rand.nextInt(99999999);
		return id;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}

}
