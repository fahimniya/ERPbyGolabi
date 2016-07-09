package software_system;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DBManagement;
import resources.FacilityResource;
import resources.FundingResource;
import resources.Quantity;
import resources.Resource;
import resources.Unit;
import user_management.User;

public class ProcessWrapper {
	private static ProcessWrapper uniqueInstance;
	private Process process;
	
	private ProcessWrapper() {}
	
	public static ProcessWrapper getInstance() {
		if (uniqueInstance == null)
			return (uniqueInstance = new ProcessWrapper());
		else
			return uniqueInstance;
	}

	public boolean addDevelopmentProcess(Date from, Date to, String name) {
		Development dev = new Development(name, from, to);
		return dev.add();
	}

	public boolean addMaintenanceProcess(Date from, Date to, String name) {
		Maintenance maintenance = new Maintenance(name, from, to);
		return maintenance.add();
	}

	public boolean addModule(String moduleName, String projectName, Resource res, String type, Date from, Date to) {
		Module module = new Module(moduleName, projectName, res, type, from, to);
		return module.addResourceAllocated();
	}

	public boolean addModule(String moduleName, String projectName, HumanResource hRes, String type, Date from,
			Date to) {
		Module module = new Module(moduleName, projectName, hRes, type, from, to);
		return module.addResourceAllocated();
	}

	public boolean addOrganizationProjects(int oid, String[] projects) {
		boolean flag = true;
		for (int i = 0; i < projects.length; i++) {
			SoftwareOrganization so = new SoftwareOrganization(oid, projects[i]);
			flag = flag && so.add();
		}
		return flag;
	}

	public SoftwareOrganization[] showSoftwareOrganization() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("SOFTWARESYSTEM_ORGANIZATIONUNIT", new String[] { "*" }, null, null);
		ResultSet rs = db.getQuery(query);
		ArrayList<SoftwareOrganization> sos = new ArrayList<SoftwareOrganization>();
		try {
			while (rs.next()) {
				sos.add(new SoftwareOrganization(rs.getInt("OID"), rs.getString("SSNAME")));
			}
		} catch (Exception e) {
			return null;
		}
		return (SoftwareOrganization[]) sos.toArray();
	}

	public Process[] showProcesses() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("PROCESS", new String[] { "*" }, null, null);
		System.out.println(query);
		ResultSet rs = db.getQuery(query);
		ArrayList<Process> processes = new ArrayList<Process>();
		try {
			while (rs.next()) {
				System.out.println(rs.getString("type"));
				if (rs.getString("TYPE").equals("DEVELOPMENT"))
					processes.add(new Development(rs.getString("SOFTWARESYSTEMNAME"), rs.getDate("FROM_DATE"),
							rs.getDate("TO_DATE"), rs.getInt("ID")));
				else
					processes.add(new Maintenance(rs.getString("SOFTWARESYSTEMNAME"), rs.getDate("FROM_DATE"),
							rs.getDate("TO_DATE"), rs.getInt("ID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return processes.toArray(new Process[processes.size()]);
	}

	public Module[] showModules() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("FACILITYRESOURCEALLOCATION", new String[] { "*" }, null, null);
		ResultSet rs = db.getQuery(query);
		ArrayList<Module> modules = new ArrayList<Module>();
		try {
			while (rs.next()) {
				int id = rs.getInt("ID");
				String q = db.generateSelectQuery("FACILITYRESOURCE", new String[] { "*" },
						new String[] { Integer.toString(id) }, new String[] { "ID" });
				ResultSet temp = db.getQuery(q);
				if (!temp.next())
					break;
				FacilityResource fr = null;
				fr = new FacilityResource(temp.getString("NAME"), temp.getInt("ID"));
				modules.add(new Module(rs.getString("MODULENAME"), rs.getString("PROJECTNAME"), fr,
						rs.getString("TYPE"), rs.getDate("FROM_DATE"), rs.getDate("TO_DATE")));
			}
		} catch (Exception e) {
			return null;
		}
		query = db.generateSelectQuery("HUMANRESOURCEALLOCATION", new String[] { "*" }, null, null);
		rs = db.getQuery(query);
		try {
			while (rs.next()) {
				int id = rs.getInt("ID");
				String q = db.generateSelectQuery("HUMSNRESOURCE", new String[] { "*" },
						new String[] { Integer.toString(id) }, new String[] { "ID" });
				ResultSet temp = db.getQuery(q);
				if (!temp.next())
					break;
				HumanResource hr = null;
				q = db.generateSelectQuery("USER", new String[] { "*" }, null, null);
				ResultSet t = db.getQuery(q);
				t.next();
				User user = new User(t.getString("USERNAME"), t.getString("PASSWORD"), t.getString("NAME"));
				hr = new HumanResource(user, temp.getDate("FROM_DATE"), temp.getDate("TO_DATE"));
				modules.add(new Module(rs.getString("MODULENAME"), rs.getString("PROJECTNAME"), hr,
						rs.getString("TYPE"), rs.getDate("FROM_DATE"), rs.getDate("TO_DATE")));
			}
		} catch (Exception e) {
			return null;
		}
		query = db.generateSelectQuery("FUNDINGRESOURCEALLOCATION", new String[] { "*" }, null, null);
		rs = db.getQuery(query);
		try {
			while (rs.next()) {
				FundingResource fr = new FundingResource(
						new Quantity(rs.getInt("AMOUNT"), new Unit(rs.getString("UNIT"))));
				modules.add(new Module(rs.getString("MODULENAME"), rs.getString("PROJECTNAME"), fr,
						rs.getString("TYPE"), rs.getDate("FROM_DATE"), rs.getDate("TO_DATE")));
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public boolean removeProcess(Process[] processes) {
		boolean flag = true;
		for (int i = 0; i < processes.length; i++) {
			flag = flag && processes[i].remove();
		}
		return flag;
	}

	public boolean removeModule(Module[] modules) {
		boolean flag = true;
		for (int i = 0; i < modules.length; i++) {
			flag = flag && modules[i].deleteResourceAllocation();
		}
		return flag;
	}

	public boolean removeSoftwareSystem(SoftwareOrganization[] so) {
		boolean flag = true;
		for (int i = 0; i < so.length; i++) {
			flag = flag && so[i].remove();
		}
		return flag;
	}

	public boolean updateProcess(Process process) {
		return process.update();
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}
}
