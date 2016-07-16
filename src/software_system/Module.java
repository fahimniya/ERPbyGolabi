package software_system;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.DBManagement;
import resources.FacilityResource;
import resources.FundingResource;
import resources.Resource;

public class Module {
	final static String MAINTENANCE = "maintenance";
	final static String DEVELOPMENT = "development";
	private String moduleName, projectName, type;
	private Resource res;
	private HumanResource hres;
	private Date from, to;

	public Module(String moduleName, String projectName, Resource resource, String type, Date from, Date to) {
		this.moduleName = moduleName;
		this.projectName = projectName;
		res = resource;
		this.from = from;
		this.to = to;
		this.type = type;
	}

	public Module(String moduleName, String projectName, HumanResource humanResource, String type, Date from, Date to) {
		this.moduleName = moduleName;
		this.projectName = projectName;
		hres = humanResource;
		this.from = from;
		this.to = to;
		this.type = type;
	}

	public boolean addResourceAllocated() {
		DBManagement db = new DBManagement();
		String query = "";
		if (res != null) {
			if (res instanceof FacilityResource) {
				if (!checkFacilityAvailablity(((FacilityResource) res).getId(), from, to))
					return false;
				query = db.generateAddQuery("FACILITYRESOURCEALLOCATION", new String[] { moduleName, projectName,
						Integer.toString(((FacilityResource) res).getId()), type, from.toString(), to.toString() });
			} 
			else {
				if(!checkFundingAvailablity(((FundingResource) res).getQuantity().getAmount(), ((FundingResource) res).getQuantity().getUnit().toString()))
					return false;
				query = db.generateAddQuery("FUNDINGRESOURCEALLOCATION",
						new String[] { moduleName, projectName, ((FundingResource) res).getQuantity().getUnit().toString(),
								String.valueOf(((FundingResource) res).getQuantity().getAmount()), type, from.toString(),
								to.toString() });
			}
		}
		else if(hres!=null) {
			if(!checkUserAvailablity(hres.getUser().getUsername(), from, to))
				return false;
			query = db.generateAddQuery("HUMANRESOURCEALLOCATION", new String[] { moduleName, projectName,
				hres.getUser().getUsername(), type, from.toString(), to.toString() });
		}
		return db.update(query);
	}

	private boolean checkFacilityAvailablity(int id, Date requestedFromDate, Date requestedToDate) {
		DBManagement db = new DBManagement();
		String query = "select * from FACILITYRESOURCEALLOCATION where ID = \'" + Integer.toString(id) + "\' and not((FROM_DATE > \'" + requestedToDate.toString() + "\') or (TO_DATE < \'" + requestedFromDate.toString() + "\'));";
		ResultSet rs = db.getQuery(query);
		try {
			return !rs.next();
		} catch (SQLException e) {
			return false;
		}
	}

	private boolean checkFundingAvailablity(int amount, String unit) {
		DBManagement db = new DBManagement();
		String query = "select * from FUNDINGRESOURCEALLOCATION where UNIT =\'" + unit + "\' and AMOUNT <= \'" + Integer.toString(amount) + "\';";
		ResultSet rs = db.getQuery(query);
		try {
			return !rs.next();
		} catch (SQLException e) {
			return false;
		}
	}
	
	private boolean checkUserAvailablity(String username, Date requestedFromDate, Date requestedToDate) {
		DBManagement db = new DBManagement();
		String query = "select * from HUMANRESOURCEALLOCATION where USERNAME = \'" + username + "\' and not((FROM_DATE > \'" + requestedToDate.toString() + "\') or (TO_DATE < \'" + requestedFromDate.toString() + "\'));";
		ResultSet rs = db.getQuery(query);
		try {
			return !rs.next();
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean deleteResourceAllocation() {
		DBManagement db = new DBManagement();
		String query = "";
		if (res != null) {
			if (res instanceof FacilityResource)
				query = db.generateDeleteQuery("FACILITYRESOURCEALLOCATION",
						new String[] { moduleName, projectName, Integer.toString(((FacilityResource) res).getId()),
								from.toString(), to.toString() },
						new String[] { "MODULENAME", "PROJECTNAME", "FACILITYRESOURCEID", "FROM_DATE", "TO_DATE" });
			else
				query = db.generateDeleteQuery("FUNDINGRESOURCEALLOCATION",
						new String[] { moduleName, projectName,
								((FundingResource) res).getQuantity().getUnit().toString(), from.toString(),
								to.toString() },
						new String[] { "MODULENAME", "PROJECTNAME", "FUNDINGYRESOURCEID", "FROM_DATE", "TO_DATE" });
		} else if (hres != null) {
			query = db.generateDeleteQuery("HUMANRESOURCEALLOCATION",
					new String[] { moduleName, projectName, hres.getUser().getUsername(), from.toString(),
							to.toString() },
					new String[] { "MODULENAME", "PROJECTNAME", "USERNAME", "FROM_DATE", "TO_DATE" });
		}
		return db.update(query);
	}
}
