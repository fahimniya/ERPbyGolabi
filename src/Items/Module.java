package Items;

import java.sql.Date;

import Data.DatabaseInterface;

public class Module {
	private String moduleName, projectName;
	private Resource res;
	private HumanResource hres;
	private Date from, to;

	public Module(String moduleName, String projectName, Resource resource, Date from, Date to) {
		this.moduleName = moduleName;
		this.projectName = projectName;
		res = resource;
		this.from = from;
		this.to = to;
	}

	public Module(String moduleName, String projectName, HumanResource humanResource, Date from, Date to) {
		this.moduleName = moduleName;
		this.projectName = projectName;
		hres = humanResource;
		this.from = from;
		this.to = to;
	}

	public boolean addResourceAllocated() {
		DatabaseInterface db = new DatabaseInterface();
		String query = "";
		if (res != null) {
			if (res instanceof FacilityResource)
				query = db.generateAddQuery("FACILITYRESOURCEALLOCATION", new String[] { moduleName, projectName,
						Integer.toString(((FacilityResource) res).getId()), from.toString(), to.toString() });
			else
				query = db.generateAddQuery("FUNDINGRESOURCEALLOCATION",
						new String[] { moduleName, projectName,
								((FundingResource) res).getQuantity().getUnit().toString(), from.toString(),
								to.toString() });
		} else if (hres != null) {
			query = db.generateAddQuery("HUMANRESOURCEALLOCATION", new String[] { moduleName, projectName,
					hres.getUser().getUsername(), from.toString(), to.toString() });
		}
		return db.update(query);
	}

	public boolean deleteResourceAllocation() {
		DatabaseInterface db = new DatabaseInterface();
		String query = "";
		if (res != null) {
			if (res instanceof FacilityResource)
				query = db.generateDeleteQuery("FACILITYRESOURCEALLOCATION", new String[] { moduleName, projectName,
						Integer.toString(((FacilityResource) res).getId()), from.toString(), to.toString() }, 
						new String[] {"MODULENAME", "PROJECTNAME", "FACILITYRESOURCEID", "FROM", "TO"});
			else
				query = db.generateDeleteQuery("FUNDINGRESOURCEALLOCATION", new String[] { moduleName, projectName,
						((FundingResource) res).getQuantity().getUnit().toString(), from.toString(), to.toString() }, 
						new String[] {"MODULENAME", "PROJECTNAME", "FUNDINGYRESOURCEID", "FROM", "TO"});
		} else if (hres != null) {
			query = db.generateDeleteQuery("HUMANRESOURCEALLOCATION", new String[] { moduleName, projectName,
					hres.getUser().getUsername(), from.toString(), to.toString() }, 
					new String[] {"MODULENAME", "PROJECTNAME", "USERNAME", "FROM", "TO"});
		}
		return db.update(query);
	}
}