package Items;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Data.DatabaseInterface;

public class ResourceWrapper {
	public Resource[] showResources() {
		DatabaseInterface db = new DatabaseInterface();
		String query = db.generateSelectQuery("FACILITYRESOURCE", new String[] { "*" }, null, null);
		ResultSet rs = db.getQuery(query);
		ArrayList<Resource> resources = new ArrayList<Resource>();
		try {
			while (rs.next()) {
				resources.add(new FacilityResource(rs.getString("NAME"), rs.getInt("ID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		query = db.generateSelectQuery("FUNDINGRESOURCE", new String[] { "*" }, null, null);
		rs = db.getQuery(query);
		try {
			while (rs.next()) {
				resources.add(new FundingResource(new Quantity(rs.getInt("AMOUNT"), new Unit(rs.getString("UNIT")))));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (Resource[]) resources.toArray();
	}
}