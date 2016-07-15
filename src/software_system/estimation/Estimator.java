package software_system.estimation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import data.DBManagement;
import resources.Quantity;
import resources.Unit;
import software_system.SoftwareSystem;
import software_system.Technology;

public class Estimator {
	public Requirement estimateNeededResource(Technology[] technology, int humanSize, int moduleSize) {
		DBManagement db = new DBManagement();
		int nOfSimilarSystems = 0;
		int humans = 0;
		int amount = 0;
		int facilityNumber = 0;
		String query = db.generateSelectQuery("SOFTWARESYSTEM", new String[] {"*"}, null, null);
		ResultSet rs = db.getQuery(query);
		try {
			while(rs.next()) {
				String name = rs.getString("NAME");
				Technology[] techs = getRelatedTechnologies(name);
				int hSize = calculateHumanSize(new SoftwareSystem(rs.getString("NAME"), techs, rs.getString("DESCRIPTION")));
				int mSize = calculateModuleSize(new SoftwareSystem(rs.getString("NAME"), techs, rs.getString("DESCRIPTION")));
//				if(technology.equals(tech) && ((((double)moduleSize/mSize > 1) && (((double)humanSize/hSize) > 1)) || (((double)moduleSize/mSize < 1) && (((double)humanSize/hSize) < 1)))) {
//					nOfSimilarSystems++;
//					humans +=hSize;
//					amount += calculateAmount(new SoftwareSystem(rs.getString("NAME"), rs.getString("TECHNOLOGY"), rs.getString("DESCRIPTION")));
//					facilityNumber += calculateFacilityNumber(new SoftwareSystem(rs.getString("NAME"), rs.getString("TECHNOLOGY"), rs.getString("DESCRIPTION")));
//				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(nOfSimilarSystems != 0) {
			return new Requirement(0,null, 0, "", (Integer)humans/nOfSimilarSystems, "", new Quantity((Integer)amount/nOfSimilarSystems, new Unit("")), facilityNumber/nOfSimilarSystems, "", null);
		}
		return null;
	}
	
	private int calculateFacilityNumber(SoftwareSystem SF) {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("REQUIREDFACILITYRESOURCE", new String[] {"*"}, new String[] {SF.getName()}, new String[] {"PROJECTNAME"});
		ResultSet rs = db.getQuery(query);
		int size = 0;
		try {
			while(rs.next()) {
				size += rs.getInt("FNUMBER");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return size;
	}


	private int calculateAmount(SoftwareSystem SF) {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("REQUIREDFUNDINGRESOURCE", new String[] {"*"}, new String[] {SF.getName()}, new String[] {"PROJECTNAME"});
		ResultSet rs = db.getQuery(query);
		int size = 0;
		try {
			while(rs.next()) {
				size += rs.getInt("AMOUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return size;
	}

	private Technology[] getRelatedTechnologies(String systemName) {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("Technology", new String[] {"TNAME"}, new String[] {systemName}, new String[] {"SNAME"});
		ResultSet rs = db.getQuery(query);
		ArrayList<Technology> techs = new ArrayList<Technology>();
		try {
			while(rs.next()) {
				techs.add(new Technology(rs.getString("TNAME")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return techs.toArray(new Technology[techs.size()]);
	}
	
	private int calculateHumanSize(SoftwareSystem SF) {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("REQUIREDHUMANRESOURCE", new String[] {"*"}, new String[] {SF.getName()}, new String[] {"PROJECTNAME"});
		ResultSet rs = db.getQuery(query);
		int size = 0;
		try {
			while(rs.next()) {
				size += rs.getInt("HNUMBER");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return size;
	}
	
	private int calculateModuleSize(SoftwareSystem SF) {
		Set<String> modules = new HashSet<String>();
		DBManagement db = new DBManagement();
		String query1 = db.generateSelectQuery("FACILITYRESOURCEALLOCATION", new String[] {"MODULENAME"}, new String[] {SF.getName()}, new String[] {"PROJECTNAME"});
		String query2 = db.generateSelectQuery("HUMANRESOURCEALLOCATION", new String[] {"MODULENAME"}, new String[] {SF.getName()}, new String[] {"PROJECTNAME"});
		String query3 = db.generateSelectQuery("FUNDINGRESOURCEALLOCATION", new String[] {"MODULENAME"}, new String[] {SF.getName()}, new String[] {"PROJECTNAME"});
		
		ResultSet rs = db.getQuery(query1);
		try {
			while(rs.next()) {
				modules.add(rs.getString("MODULENAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		rs = db.getQuery(query2);
		try {
			while(rs.next()) {
				modules.add(rs.getString("MODULENAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		rs = db.getQuery(query3);
		try {
			while(rs.next()) {
				modules.add(rs.getString("MODULENAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modules.size();		
	}
	
	public Requirement[] estimateEssentialRequirements(SoftwareSystem SF) {
		return null;
	}
	private class Pair {
		private Integer point;
		private SoftwareSystem system;
		public Pair(SoftwareSystem sf) {
			point = 0;
			system = sf;
		}
		public Integer getPoint() {
			return point;
		}
		public void setPoint(Integer point) {
			this.point = point;
		}
		public SoftwareSystem getSystem() {
			return system;
		}
		
//		public boolean < (Pair p1, Pair p2) {
//			return p1.pointpoint() < p2.getpoint();
//		}
	}
}
