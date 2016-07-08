package software_system;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DBManagement;
import resources.Resource;

public class ProcessWrapper {
	
	public boolean addDevelopmentProcess(Date from, Date to, String name){
		Development dev = new Development(name, from, to);
		return dev.add();
	}
	
	public boolean addMaintenanceProcess(Date from, Date to, String name){
		Maintenance maintenance = new Maintenance(name, from, to);
		return maintenance.add();
	}
	
	public boolean addModule(String moduleName, String projectName, Resource res, Date from, Date to) {
		Module module = new Module(moduleName, projectName, res, from, to);
		return module.addResourceAllocated();
	}
	
	public boolean addModule(String moduleName, String projectName, HumanResource hRes, Date from, Date to) {
		Module module = new Module(moduleName, projectName, hRes, from, to);
		return module.addResourceAllocated();
	}
	
	public boolean addOrganizationProjects(int oid, String[] projects) {
		boolean flag = true;
		for(int i = 0; i < projects.length; i++) {
			SoftwareOrganization so = new SoftwareOrganization(oid, projects[i]);
			flag = flag && so.add();
		}
		return flag;
	}
	
	public Process[] showProcesses() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("DEVELOP", new String[] {"*"}, null, null);
		ResultSet rs = db.getQuery(query);
		ArrayList<Process> processes = new ArrayList<Process>();
		try {
			while(rs.next()) {
				processes.add(new Development(rs.getString("PROJECTNAME"), rs.getDate("FROM"), rs.getDate("TO"), rs.getInt("ID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
		query = db.generateSelectQuery("DEVELOP", new String[] {"*"}, null, null);
		rs = db.getQuery(query);
		try {
			while(rs.next()) {
				processes.add(new Maintenance(rs.getString("PROJECTNAME"), rs.getDate("FROM"), rs.getDate("TO"), rs.getInt("ID")));
			}
		} catch (SQLException e) {
			return null;
		}
		return (Process[]) processes.toArray();
	}
	
	public Module[] showModules() {
		return null;
	}
}
