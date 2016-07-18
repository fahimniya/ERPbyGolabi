package software_system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import data.DBManagement;
import resources.Quantity;
import user_management.User;
import user_management.UserWrapper;

public class OrganizationUnit {
	private int id;
	private String name;
	private User manager;

	public OrganizationUnit[] getAll() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("ORGANIZATIONUNIT", new String[] { "*" },
				new String[] { UserWrapper.getInstance().getUsername() }, new String[] { "USERNAME" });
		ResultSet rs = db.getQuery(query);
		ArrayList<OrganizationUnit> result = new ArrayList<OrganizationUnit>();
		try {
			while (rs.next()) {
				int currentID = rs.getInt("ID");
				String currentName = rs.getString("NAME");
				String managerUsername = rs.getString("USERNAME");
				result.add(new OrganizationUnit(currentID, currentName, new User(managerUsername, null)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result.toArray(new OrganizationUnit[result.size()]);
	}
	
	public OrganizationUnit(int id, String name, User manager) {
		this.id = id;
		this.name = name;
		this.manager = manager;
	}

	public OrganizationUnit(String name, User manager) {
		this.id = generateReqId();
		this.name = name;
		this.manager = manager;
	}
	
	public boolean addOrganizationUnit(OrganizationUnit organizationUnit) {
		return addOrganizationUnit(organizationUnit.id, organizationUnit.name, organizationUnit.manager);
	}

	public boolean addOrganizationUnit(int id, String name, User manager) {
		this.id = id;
		this.name = name;
		this.manager = manager;
		DBManagement db = new DBManagement();
		String query = db.generateAddQuery("ORGANIZATIONUNIT",
				new String[] { Integer.toString(id), this.name, this.manager.getUsername() });
		return db.update(query);
	}

	public boolean registerResource(String projectName, int humans, String specialty, Quantity amount,
			int facilityNumber, String name, int priority) {
		if (!UserWrapper.getInstance().getUsername().equals(manager.getUsername()))
			return false;
		UserWrapper.getInstance().getUsername();
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
		String query = db.generateAddQuery("REQUIREDHUMANRESOURCE",
				new String[] { Integer.toString(hReqId), Integer.toString(id), projectName,
						Integer.toString(humanNumber), specialty, null, Integer.toString(priority) });
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
		String query = db.generateAddQuery("REQUIREDFACILITYRESOURCE",
				new String[] { Integer.toString(fReqId), Integer.toString(id), projectName,
						Integer.toString(facilityNumber), name, null, Integer.toString(priority) });
		return db.update(query);
	}

	private boolean registerFundingResource(String projectName, Quantity amount, int priority) {
		DBManagement db = new DBManagement();
		int fundingReqId = generateReqId();
		String query = db.generateAddQuery("REQUIREDFUNDINGRESOURCE",
				new String[] { Integer.toString(fundingReqId), Integer.toString(id), projectName,
						Integer.toString(amount.getAmount()), amount.getUnit().toString(), null,
						Integer.toString(priority) });
		return db.update(query);
	}
}