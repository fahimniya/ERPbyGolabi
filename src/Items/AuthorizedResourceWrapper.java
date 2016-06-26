package Items;

import Data.DatabaseInterface;

public class AuthorizedResourceWrapper extends ResourceWrapper{
	
	public boolean addFacilityResource(FacilityResource facility) {
		return facility.addToDB();
	}
	
	public boolean addFundingResource(Quantity quantity) {
		return new FundingResource(quantity).addToDB();
	}
	
	public boolean removeResource(Resource resource) {
		return resource.removeFromDB();
	}
}
