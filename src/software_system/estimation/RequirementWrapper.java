package software_system.estimation;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DBManagement;
import resources.FacilityResource;
import resources.Quantity;
import resources.Resource;
import resources.Unit;
import software_system.HumanResource;
import software_system.Module;
import software_system.SoftwareSystem;

public class RequirementWrapper {
	public boolean allocateResuorce(Resource resource, String moduleName, String projectName, String type, Date from, Date to) {
		Module module = new Module(moduleName, projectName, resource, type, from, to);
		return module.addResourceAllocated();
	}

	public Module[] resourceTurnover() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("FACILITYRESOURCEALLOCATION", new String[] { "*" }, null, null);
		query = query.substring(0, query.length() - 1)
				+ " inner join FACILITYRESOURCE on FACILITYRESOURCEALLOCATION.ID = FACILITYRESOURCE.ID order by ID;";
		ResultSet rs = db.getQuery(query);
		ArrayList<Module> module = new ArrayList<Module>();
		try {
			while (rs.next()) {
				module.add(new Module(rs.getString("MODULENAME"), rs.getString("PROJECTNAME"),
						new FacilityResource(rs.getString("FACILITYRESOURCE.NAME"), rs.getInt("ID")),
						rs.getString("FACILITYRESOURCEALLOCATION.TYPE"), rs.getDate("FROM_DATE"), rs.getDate("TO_DATE")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (Module[]) module.toArray();
	}
	
	public Requirement[] showRequirment(SoftwareSystem softwareSystem) {
		DBManagement db = new DBManagement();
		
		String reqHumanQuery = db.generateSelectQuery("REQUIREDHUMANRESOURCE", new String[] {"*"}, new String[] {softwareSystem.getName()}, new String[] {"PROJECTNAME"});
		String reqFacilityQuery = db.generateSelectQuery("REQUIREDFACILITYRESOURCE", new String[] {"*"}, new String[] {softwareSystem.getName()}, new String[] {"PROJECTNAME"});
		String reqFundingQuery = db.generateSelectQuery("REQUIREDFUNDINGRESOURCE", new String[] {"*"}, new String[] {softwareSystem.getName()}, new String[] {"PROJECTNAME"});
		
		ResultSet reqHumanResult = db.getQuery(reqHumanQuery);
		ResultSet reqFacilityResult = db.getQuery(reqFacilityQuery);
		ResultSet reqFundingResult = db.getQuery(reqFundingQuery);
		
		ArrayList<Requirement> req = new ArrayList<Requirement>();
		
		String resourceAllocationQuery = db.generateSelectQuery("FACILITYRESOURCEALLOCATION", new String[]{"*"}, new String[] {softwareSystem.getName()}, new String[] {"PROJECTNAME"});
		ResultSet rs = db.getQuery(resourceAllocationQuery);
		
		ArrayList<Module> moduleArray = new ArrayList<Module>();
		try {
			while(rs.next()) {
				moduleArray.add(new Module(rs.getString("MODULENAME"), rs.getString("PROJECTNAME"), new HumanResource(null, null, null), rs.getString("TYPE"), rs.getDate("FROM_DATE"), rs.getDate("TO_DATE")));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(reqHumanResult.next()) {
				int oid = reqHumanResult.getInt("OID");
				Date SDate = reqHumanResult.getDate("SDATE");
				String specialty = reqHumanResult.getString("SPECIALTY");
				int number = reqHumanResult.getInt("HNUMBER");
				int id = reqHumanResult.getInt("HREQID");
				int priority = reqHumanResult.getInt("PRIORITY");
				req.add(new Requirement(oid, SDate, id, Requirement.humanReq, number, specialty, null, null, null, (Module[])moduleArray.toArray(), priority));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(reqFacilityResult.next()) {
				int oid = reqFacilityResult.getInt("OID");
				Date SDate = reqFacilityResult.getDate("SDATE");
				int facilityNumber = reqFacilityResult.getInt("FNUMBER");
				String facilityName = reqFacilityResult.getString("NAME");
				int id = reqFacilityResult.getInt("FREQID");
				int priority = reqFacilityResult.getInt("PRIORITY");
				req.add(new Requirement(oid, SDate, id, Requirement.facilityReq, null, null, null, facilityNumber, facilityName, (Module[])moduleArray.toArray(), priority));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(reqFundingResult.next()) {
				int oid = reqFundingResult.getInt("OID");
				Date SDate = reqFundingResult.getDate("SDATE");
				int amount = reqFundingResult.getInt("AMOUNT");
				String unit = reqFundingResult.getString("UNIT");
				int id = reqFundingResult.getInt("FREQID");
				int priority = reqFundingResult.getInt("PRIORITY");
				req.add(new Requirement(oid, SDate, id, Requirement.fundingReq, null, null, new Quantity(amount, new Unit(unit)), null, null, (Module[])moduleArray.toArray(), priority));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (Requirement[])req.toArray();
	}
}
