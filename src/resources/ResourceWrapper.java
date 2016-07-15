package resources;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DBManagement;
import software_system.HumanResource;
import user_management.User;

public class ResourceWrapper {
  public Resource[] showResources() {
    DBManagement db = new DBManagement();
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
    return resources.toArray(new Resource[resources.size()]);
  }
  
  public HumanResource[] showHumanResources() {
    DBManagement db = new DBManagement();
    String query = db.generateSelectQuery("HUMANRESOURCE", new String[] {"*"}, null, null);
    ResultSet rs = db.getQuery(query);
    ArrayList<HumanResource> humanResources = new ArrayList<HumanResource>();
    try {
      while(rs.next()) {
        String q = db.generateSelectQuery("USER", new String[] {"*"}, new String[] {rs.getString("USERNAME")}, new String[] {"USERNAME"});
        ResultSet temp = db.getQuery(q);
        if(!temp.next())
          break;
        humanResources.add(new HumanResource(new User(temp.getString("USERNAME"), temp.getString("PASSWORD"), temp.getString("NAME")), rs.getDate("FROM_DATE"), rs.getDate("TO_DATE")));
      }
    } catch (Exception e) {
      return null;
    }
    return humanResources.toArray(new HumanResource[humanResources.size()]);
  }
}