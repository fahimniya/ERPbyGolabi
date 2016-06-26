package Items;

import javax.management.modelmbean.RequiredModelMBean;

public abstract class Resource {
	public abstract boolean addToDB();
	
	public abstract boolean removeFromDB();
	
	public Requirement[] estimate(Requirement resourceSpent[]) {
		return null;
	}
}
	
