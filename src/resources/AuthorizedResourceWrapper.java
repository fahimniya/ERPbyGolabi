package resources;

import software_system.HumanResource;

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
	
	public boolean addHumanResource(HumanResource humanResource) {
		return humanResource.addHumanResource();
	}
	
	public boolean removeHumanResource(HumanResource humanResource) {
		return humanResource.deleteHumanResource();
	}
}
