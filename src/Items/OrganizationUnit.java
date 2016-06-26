package Items;

import java.util.Random;

import Data.DatabaseInterface;

public class OrganizationUnit {
	private int id;

	public boolean registerResource(int humans, String specialty, Quantity amount, int facilityNumber, String name) {
		boolean human = false, facility = false, fund = false;
		if (humans != 0)
			human = registerHumanResource(humans, specialty);
		if (facilityNumber != 0)
			facility = registerFacilityResource(facilityNumber, name);
		fund = registerFundingResource(amount);
		return (human || facility || fund);
	}

	private boolean registerHumanResource(int humanNumber, String specialty) {
		DatabaseInterface db = new DatabaseInterface();
		int hReqId = generateReqId();
		String query = db.generateAddQuery("REQUIREDHUMANRESOURCE", new String[] { Integer.toString(hReqId),
				Integer.toString(id), Integer.toString(humanNumber), specialty });
		return db.update(query);
	}

	private int generateReqId() {
		Random rand = new Random();
		int id = 500000000 + rand.nextInt(99999999);
		return id;
	}

	private boolean registerFacilityResource(int facilityNumber, String name) {
		DatabaseInterface db = new DatabaseInterface();
		int fReqId = generateReqId();
		String query = db.generateAddQuery("REQUIREDFACILITYRESOURCE", new String[] { Integer.toString(fReqId),
				Integer.toString(id), Integer.toString(facilityNumber), name });
		return db.update(query);
	}

	private boolean registerFundingResource(Quantity amount) {
		DatabaseInterface db = new DatabaseInterface();
		int fundingReqId = generateReqId();
		String query = db.generateAddQuery("REQUIREDFUNDINGRESOURCE", new String[] { Integer.toString(fundingReqId),
				Integer.toString(id), Integer.toString(amount.getAmount()), amount.getUnit().toString()});
		return db.update(query);
	}
}