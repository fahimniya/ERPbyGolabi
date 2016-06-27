package Items;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Data.DatabaseInterface;

public class RequirementWrapper {
	public boolean allocateResuorce(Resource resource, String moduleName, String projectName, Date from, Date to) {
		Module module = new Module(moduleName, projectName, resource, from, to);
		return module.addResourceAllocated();
	}

	public Module[] resourceTurnover() {
		DatabaseInterface db = new DatabaseInterface();
		String query = db.generateSelectQuery("FACILITYRESOURCEALLOCATION", new String[] { "*" }, null, null);
		query = query.substring(0, query.length() - 1)
				+ " inner join FACILITYRESOURCE on FACILITYRESOURCEALLOCATION.ID = FACILITYRESOURCE.ID order by ID;";
		ResultSet rs = db.getQuery(query);
		ArrayList<Module> module = new ArrayList<Module>();
		try {
			while (rs.next()) {
				module.add(new Module(rs.getString("MODULENAME"), rs.getString("PROJECTNAME"),
						new FacilityResource(rs.getString("FACILITYRESOURCE.NAME"), rs.getInt("ID")),
						rs.getDate("FROM"), rs.getDate("TO")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (Module[]) module.toArray();
	}
}