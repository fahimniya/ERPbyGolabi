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

	private ProcessWrapper() {
	}

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

	public boolean addSoftwareSystem(SoftwareSystem softwareSystem) {
		return softwareSystem.add();
	}

	public boolean addTechnology(Technology technology) {
		return technology.addTechnology();
	}

	public Technology[] showTechnologies() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("Technology", new String[] { "*" }, null, null);
		ResultSet rs = db.getQuery(query);
		ArrayList<Technology> techs = new ArrayList<Technology>();
		try {
			while (rs.next()) {
				techs.add(new Technology(rs.getString("TNAME")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return techs.toArray(new Technology[techs.size()]);
	}

	public boolean deleteTechnology(Technology technology) {
		return technology.deleteTechnology();
	}

	public boolean updateTechnology(Technology technology, String oldName) {
		return technology.updateTechnology(oldName);
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
		return sos.toArray(new SoftwareOrganization[sos.size()]);
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
			return null;
		}
		return processes.toArray(new Process[processes.size()]);
	}

	public SoftwareSystem[] showSoftwareSystem() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("SOFTWARESYSTEM", new String[] { "*" }, null, null);
		// System.err.println("query from processWrapper: " + query);
		ResultSet rs = db.getQuery(query);
		ArrayList<SoftwareSystem> softwareSystems = new ArrayList<SoftwareSystem>();
		try {
			while (rs.next()) {
				String name = rs.getString("NAME");
				System.err.println("software name: " + name);
				ResultSet rstech = db.getQuery(db.generateSelectQuery("SOFTECH", new String[] { "TNAME" },
						new String[] { name }, new String[] { "SNAME" }));
				ArrayList<Technology> techs = new ArrayList<Technology>();
				while (rstech != null && rstech.next()) {
					techs.add(new Technology(rstech.getString("TNAME")));
				}
				// System.err.println("From ProcessWrapper: " +
				// techs.toArray(new Technology[techs.size()]));
				softwareSystems.add(new SoftwareSystem(rs.getString("NAME"),
						techs.toArray(new Technology[techs.size()]), rs.getString("DESCRIPTION")));
			}
		} catch (Exception e) {
			return null;
		}
		return softwareSystems.toArray(new SoftwareSystem[softwareSystems.size()]);
	}

	public String[] showModuleName() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("FACILITYRESOURCEALLOCATION", new String[] { "MODULENAME" }, null, null);
		ResultSet rs = db.getQuery(query);
		ArrayList<String> moduleNames = new ArrayList<String>();
		try {
			while (rs.next()) {
				String temp = rs.getString("MODULENAME");
				if (!moduleNames.contains(temp))
					moduleNames.add(temp);
			}
		} catch (Exception e) {
			return null;
		}
		query = db.generateSelectQuery("HUMANRESOURCEALLOCATION", new String[] { "MODULENAME" }, null, null);
		rs = db.getQuery(query);
		try {
			while (rs.next()) {
				String temp = rs.getString("MODULENAME");
				if (!moduleNames.contains(temp))
					moduleNames.add(temp);
			}
		} catch (Exception e) {
			return null;
		}
		query = db.generateSelectQuery("FUNDINGRESOURCEALLOCATION", new String[] { "MODULENAME" }, null, null);
		rs = db.getQuery(query);
		try {
			while (rs.next()) {
				String temp = rs.getString("MODULENAME");
				if (!moduleNames.contains(temp))
					moduleNames.add(temp);
			}
		} catch (Exception e) {
			return null;
		}
		return moduleNames.toArray(new String[moduleNames.size()]);
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
				FacilityResource fr = new FacilityResource(temp.getString("NAME"), temp.getInt("ID"));
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
				q = db.generateSelectQuery("USER", new String[] { "*" }, null, null);
				ResultSet t = db.getQuery(q);
				t.next();
				User user = new User(t.getString("USERNAME"), t.getString("PASSWORD"), t.getString("NAME"));
				HumanResource hr = new HumanResource(user, temp.getDate("FROM_DATE"), temp.getDate("TO_DATE"));
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
		return modules.toArray(new Module[modules.size()]);
	}

	public boolean removeProcess(Process[] processes) {
		boolean flag = true;
		for (int i = 0; i < processes.length; i++) {
			flag = flag && processes[i].remove();
		}
		return flag;
	}

	public boolean removeModule(String[] moduleNames) {
		DBManagement db = new DBManagement();
		boolean flag = true;
		for (int i = 0; i < moduleNames.length; i++) {
			String query = db.generateDeleteQuery("FACILITYRESOURCEALLOCATION", new String[] { moduleNames[i] },
					new String[] { "MODULENAME" });
			flag = flag && db.update(query);
			query = db.generateDeleteQuery("FUNDINGRESOURCEALLOCATION", new String[] { moduleNames[i] },
					new String[] { "MODULENAME" });
			flag = flag && db.update(query);
			query = db.generateDeleteQuery("HUMANRESOURCEALLOCATION", new String[] { moduleNames[i] },
					new String[] { "MODULENAME" });
			flag = flag && db.update(query);
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

	public boolean softwareSystemExists(String name) {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("SOFTWARESYSTEM", new String[] { "*" }, new String[] { name },
				new String[] { "NAME" });
		ResultSet rs = db.getQuery(query);
		try {
			rs.next();
			if (rs.getString("NAME").equals(name))
				return true;
		} catch (Exception e) {
			return true;
		}
		return false;
	}
}
