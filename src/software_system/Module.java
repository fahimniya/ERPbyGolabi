package software_system;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.rmi.CORBA.UtilDelegate;

import data.DBManagement;
import resources.FacilityResource;
import resources.FundingResource;
import resources.Quantity;
import resources.Resource;
import resources.Unit;

public class Module {
	final static String MAINTENANCE = "maintenance";
	final static String DEVELOPMENT = "development";
	private String moduleName, projectName, type;
	public String getModuleName() {
		return moduleName;
	}

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
				updateRequiredFacilityResource(projectName, ((FacilityResource) res).getName());
			} 
			else {
				if(!checkFundingAvailablity(((FundingResource) res).getQuantity().getAmount(), ((FundingResource) res).getQuantity().getUnit().toString()))
					return false;
				updateFundingResource(((FundingResource) res).getQuantity().getAmount(), ((FundingResource) res).getQuantity().getUnit().toString());
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
				updateRequiredHumanResource(projectName);
		}
		return db.update(query);
	}

	private void updateFundingResource(int amount, String unit) {
		DBManagement db = new DBManagement();
		String selectQuery = db.generateSelectQuery("REQUIREDFUNDINGRESOURCE", new String[] {"*"}, new String[] {projectName, unit} , new String[] {"PROJECTNAME", "UNIT"});
		selectQuery = selectQuery.substring(selectQuery.length()-1) + " and SDATE = null;";
		
		ResultSet rs = db.getQuery(selectQuery);
		try {
			if(rs.getFetchSize()>0) {
				rs.next();
				int id = rs.getInt("FREQID");
				int requiredAmount = rs.getInt("AMOUNT");
				if(requiredAmount <= amount) {
					String updateQuery = db.generateUpdateQuery("REQUIREDFUNDINGRESOURCE", new String[] {new Date(Calendar.getInstance().getTime().getTime()).toString()}, new String[]{"SDATE"}, new String[] {Integer.toString(id)}, new String[] {"FREQID"});
					db.update(updateQuery);
				}
				else {
					String updateQuery = db.generateUpdateQuery("REQUIREDFUNDINGRESOURCE", new String[] {new Date(Calendar.getInstance().getTime().getTime()).toString(), Integer.toString(amount)}, new String[]{"SDATE", "AMOUNT"}, new String[] {Integer.toString(id)}, new String[] {"FREQID"});
					db.update(updateQuery);
					String addQuery = db.generateAddQuery("REQUIREDFUNDINGRESOURCE", new String[] {Integer.toString(new OrganizationUnit(null, null).generateReqId()), rs.getString("OID"), rs.getString("PROJECTNAME"), Integer.toString(requiredAmount - amount), unit, new Date(Calendar.getInstance().getTime().getTime()).toString(), rs.getString("PRIORITY")});
					db.update(addQuery);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		FundingResource resource = new FundingResource(new Quantity(amount, new Unit(unit)));
		resource.removeFromDB();
	}

	private void updateRequiredFacilityResource(String projectName, String facilityName) {
		DBManagement db = new DBManagement();
		String selectQuery = db.generateSelectQuery("REQUIREDFACILITYRESOURCE", new String[] {"*"}, new String[] {projectName, facilityName} , new String[] {"PROJECTNAME", "NAME"});
		selectQuery = selectQuery.substring(selectQuery.length()-1) + " and SDATE = null;";
		ResultSet rs = db.getQuery(selectQuery);
		try {
			if(rs.getFetchSize()>0) {
				rs.next();
				int id = rs.getInt("FREQID");
				int number = rs.getInt("FNUMBER");
				if(number == 1) {
					String updateQuery = db.generateUpdateQuery("REQUIREDFACILITYRESOURCE", new String[] {new Date(Calendar.getInstance().getTime().getTime()).toString()}, new String[]{"SDATE"}, new String[] {Integer.toString(id)}, new String[] {"FREQID"});
					db.update(updateQuery);
				}
				else {
					String updateQuery = db.generateUpdateQuery("REQUIREDFACILITYRESOURCE", new String[] {new Date(Calendar.getInstance().getTime().getTime()).toString(), Integer.toString(id-1)}, new String[]{"SDATE", "FNUMBER"}, new String[] {Integer.toString(id)}, new String[] {"FREQID"});
					db.update(updateQuery);
					String addQuery = db.generateAddQuery("REQUIREDFACILITYRESOURCE", new String[] {Integer.toString(new OrganizationUnit(null, null).generateReqId()), rs.getString("OID"), rs.getString("PROJECTNAME"), "1", facilityName, new Date(Calendar.getInstance().getTime().getTime()).toString(), Integer.toString(id-1), rs.getString("PRIORITY")});
					db.update(addQuery);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void updateRequiredHumanResource(String projectName) {
		DBManagement db = new DBManagement();
		String selectQuery = db.generateSelectQuery("REQUIREDHUMANRESOURCE", new String[] {"*"}, new String[] {projectName} , new String[] {"PROJECTNAME"});
		selectQuery = selectQuery.substring(selectQuery.length()-1) + " and SDATE = null;";
		ResultSet rs = db.getQuery(selectQuery);
		try {
			if(rs.getFetchSize()>0) {
				rs.next();
				int id = rs.getInt("HREQID");
				int number = rs.getInt("HNUMBER");
				if(number == 1) {
					String updateQuery = db.generateUpdateQuery("REQUIREDHUMANRESOURCE", new String[] {new Date(Calendar.getInstance().getTime().getTime()).toString()}, new String[]{"SDATE"}, new String[] {Integer.toString(id)}, new String[] {"HREQID"});
					db.update(updateQuery);
				}
				else {
					String updateQuery = db.generateUpdateQuery("REQUIREDHUMANRESOURCE", new String[] {new Date(Calendar.getInstance().getTime().getTime()).toString(), Integer.toString(id-1)}, new String[]{"SDATE", "HNUMBER"}, new String[] {Integer.toString(id)}, new String[] {"FREQID"});
					db.update(updateQuery);
					String addQuery = db.generateAddQuery("REQUIREDHUMANRESOURCE", new String[] {Integer.toString(new OrganizationUnit(null, null).generateReqId()), rs.getString("OID"), rs.getString("PROJECTNAME"), "1", rs.getString("SPECIALTY"), new Date(Calendar.getInstance().getTime().getTime()).toString(), Integer.toString(id-1), rs.getString("PRIORITY")});
					db.update(addQuery);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
