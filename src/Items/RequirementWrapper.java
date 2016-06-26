package Items;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Data.DatabaseInterface;

public class RequirementWrapper {
	public boolean allocateResuorce(Resource resource, Module module, Date from, Date to) {
		ResourceAllocation resAl = new ResourceAllocation(module, resource, from, to);
		return resAl.addResourceAllocated();
	}

	public ResourceAllocation[] resourceTurnover() {
		DatabaseInterface db = new DatabaseInterface();
		String query = db.generateSelectQuery("FACILITYRESOURCEALLOCATION", new String[] { "*" }, null, null);
		query = query.substring(0, query.length() - 1)
				+ " inner join FACILITYRESOURCE on FACILITYRESOURCEALLOCATION.ID = FACILITYRESOURCE.ID order by ID;";
		ResultSet rs = db.getQuery(query);
		ArrayList<ResourceAllocation> resourceAllocation = new ArrayList<ResourceAllocation>();
		try {
			while(rs.next()) {
				resourceAllocation.add(new ResourceAllocation(new Module(rs.getString("MODULE")), new FacilityResource(rs.getString("FACILITYRESOURCE.NAME"), rs.getInt("ID")), rs.getDate("FROM"), rs.getDate("TO")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return (ResourceAllocation[]) resourceAllocation.toArray();
	}
}
