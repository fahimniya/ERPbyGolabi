package software_system;

import java.util.Random;

import data.DBManagement;
import resources.Quantity;
import user_management.User;

public class OrganizationUnit {
	private int id;
	private String name;
	private User manager;
	
	public boolean addOrganizationUnit(int id, String name, User manager) {
		this.id = id;
		this.name = name;
		this.manager = manager;
		DBManagement db = new DBManagement();
		String query = db.generateAddQuery("ORGANIZATIONUNIT", new String[] {Integer.toString(id), name, manager.getUsername()});
		return db.update(query);
	}
	
	public boolean registerResource(String projectName, int humans, String specialty, Quantity amount, int facilityNumber, String name, int priority) {
		boolean human = false, facility = false, fund = false;
		if (humans != 0)
			human = registerHumanResource(projectName, humans, specialty, priority);
		if (facilityNumber != 0)
			facility = registerFacilityResource(projectName, facilityNumber, name, priority);
		fund = registerFundingResource(projectName, amount, priority);
		return (human || facility || fund);
	}

	private boolean registerHumanResource(String projectName, int humanNumber, String specialty, int priority) {
		DBManagement db = new DBManagement();
		int hReqId = generateReqId();
		String query = db.generateAddQuery("REQUIREDHUMANRESOURCE", new String[] { Integer.toString(hReqId),
				Integer.toString(id), projectName, Integer.toString(humanNumber), specialty, null, Integer.toString(priority) });
		return db.update(query);
	}

	private int generateReqId() {
		Random rand = new Random();
		int id = 500000000 + rand.nextInt(99999999);
		return id;
	}

	private boolean registerFacilityResource(String projectName, int facilityNumber, String name, int priority) {
		DBManagement db = new DBManagement();
		int fReqId = generateReqId();
		String query = db.generateAddQuery("REQUIREDFACILITYRESOURCE", new String[] { Integer.toString(fReqId),
				Integer.toString(id), projectName, Integer.toString(facilityNumber), name, null, Integer.toString(priority) });
		return db.update(query);
	}

	private boolean registerFundingResource(String projectName, Quantity amount, int priority) {
		DBManagement db = new DBManagement();
		int fundingReqId = generateReqId();
		String query = db.generateAddQuery("REQUIREDFUNDINGRESOURCE", new String[] { Integer.toString(fundingReqId),
				Integer.toString(id), projectName, Integer.toString(amount.getAmount()), amount.getUnit().toString(), null, Integer.toString(priority)});
		return db.update(query);
	}
}