package resources;

import software_system.estimation.Requirement;

public abstract class Resource {
	public abstract boolean addToDB();
	
	public abstract boolean removeFromDB();
	
	public Requirement[] estimate(Requirement resourceSpent[]) {
		return null;
	}
}
	
