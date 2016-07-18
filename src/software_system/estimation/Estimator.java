package software_system.estimation;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import data.DBManagement;
import resources.Quantity;
import resources.Unit;
import software_system.Module;
import software_system.SoftwareSystem;
import software_system.Technology;

public class Estimator {
	public Requirement[] estimateNeededResource(boolean useTechs, Technology[] technology, boolean useHumanSize,
			int humanSize, boolean useModuleSize, int moduleSize) {
		DBManagement db = new DBManagement();
		Map<Double, SoftwareSystem> ranking = new TreeMap<Double, SoftwareSystem>();
		Map<Double, Requirement> rankedReq = new TreeMap<Double, Requirement>();
		String query = db.generateSelectQuery("SOFTWARESYSTEM", new String[] { "*" }, null, null);
		ResultSet rs = db.getQuery(query);
		try {
			while (rs.next()) {
				String name = rs.getString("NAME");
				String description = rs.getString("DESCRIPTION");
				Technology[] techs = getRelatedTechnologies(name);
				SoftwareSystem system = new SoftwareSystem(name, techs, description);
				int hSize = calculateHumanSize(system);
				int mSize = calculateModuleSize(system);

				Double point = 0.0;
				if (useTechs)
					point += addTechnologyPoints(technology, techs);
				if (useHumanSize)
					point += addHumanSizePoints(humanSize, hSize);
				if (useModuleSize)
					point += addModuleSizePoint(moduleSize, mSize);
				ranking.put(point, system);

				Requirement[] req = new RequirementWrapper().showRequirment(system);
				Map<Double, Requirement> temp = new TreeMap<Double, Requirement>(rankedReq);
				rankedReq.clear();
				for (int i = 0; i < req.length; i++) {
					for (Entry<Double, Requirement> item : temp.entrySet()) {
						Requirement x = item.getValue();
						if (x.type.equals(req[i].type)) {
							switch (x.type) {
							case Requirement.humanReq:
								if (x.specialty.equals(req[i].specialty)) {
									x.numberOfSystem++;
									x.humans += x.humans;
									rankedReq.put(point + item.getKey(), x);
								}
								break;
							case Requirement.facilityReq:
								if (x.facilityName.equals(req[i].facilityName)) {
									x.numberOfSystem++;
									x.facilityNumber += req[i].facilityNumber;
									rankedReq.put(point + item.getKey(), x);
								}
								break;
							case Requirement.fundingReq:
								if (x.amount.getUnit().equals(req[i].amount.getUnit())) {
									x.amount = new Quantity(x.amount.getAmount() + req[i].amount.getAmount(),
											x.amount.getUnit());
									x.numberOfSystem++;
									rankedReq.put(point + item.getKey(), x);
								}
								break;
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<Requirement> result = new ArrayList<Requirement>();
		for (Entry<Double, Requirement> item : rankedReq.entrySet()) {
			Requirement x = item.getValue();
			switch (x.type) {
			case Requirement.humanReq:
				x.humans /= x.numberOfSystem;
				break;
			case Requirement.facilityReq:
				x.facilityNumber /= x.numberOfSystem;
				break;
			case Requirement.fundingReq:
				x.amount = new Quantity(x.amount.getAmount() / x.numberOfSystem, x.amount.getUnit());
				break;
			}
			result.add(x);
		}
		Collections.reverse(result);
		return result.toArray(new Requirement[result.size()]);
	}

	private Requirement[] getAllUnsatisfiedReq() {
		DBManagement db = new DBManagement();

		String reqHumanQuery = db.generateSelectQuery("REQUIREDHUMANRESOURCE", new String[] { "*" },
				new String[] { "null" }, new String[] { "SDATE" });
		String reqFacilityQuery = db.generateSelectQuery("REQUIREDFACILITYRESOURCE", new String[] { "*" },
				new String[] { "null" }, new String[] { "SDATE" });
		String reqFundingQuery = db.generateSelectQuery("REQUIREDFUNDINGRESOURCE", new String[] { "*" },
				new String[] { "null" }, new String[] { "SDATE" });

		ResultSet reqHumanResult = db.getQuery(reqHumanQuery);
		ResultSet reqFacilityResult = db.getQuery(reqFacilityQuery);
		ResultSet reqFundingResult = db.getQuery(reqFundingQuery);

		ArrayList<Requirement> req = new ArrayList<Requirement>();
		try {
			while (reqHumanResult.next()) {
				int oid = reqHumanResult.getInt("OID");
				Date SDate = reqHumanResult.getDate("SDATE");
				String specialty = reqHumanResult.getString("SPECIALTY");
				int number = reqHumanResult.getInt("HNUMBER");
				int id = reqHumanResult.getInt("HREQID");
				int priority = reqHumanResult.getInt("PRIORITY");
				req.add(new Requirement(oid, SDate, id, Requirement.humanReq, number, specialty, null, null, null, null,
						priority));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (reqFacilityResult.next()) {
				int oid = reqFacilityResult.getInt("OID");
				Date SDate = reqFacilityResult.getDate("SDATE");
				int facilityNumber = reqFacilityResult.getInt("FNUMBER");
				String facilityName = reqFacilityResult.getString("NAME");
				int id = reqFacilityResult.getInt("FREQID");
				int priority = reqFacilityResult.getInt("PRIORITY");
				req.add(new Requirement(oid, SDate, id, Requirement.facilityReq, null, null, null, facilityNumber,
						facilityName, null, priority));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (reqFundingResult.next()) {
				int oid = reqFundingResult.getInt("OID");
				Date SDate = reqFundingResult.getDate("SDATE");
				int amount = reqFundingResult.getInt("AMOUNT");
				String unit = reqFundingResult.getString("UNIT");
				int id = reqFundingResult.getInt("FREQID");
				int priority = reqFundingResult.getInt("PRIORITY");
				req.add(new Requirement(oid, SDate, id, Requirement.fundingReq, null, null,
						new Quantity(amount, new Unit(unit)), null, null, null, priority));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (Requirement[]) req.toArray();

	}

	private Technology[] getRelatedTechnologies(String systemName) {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("Technology", new String[] { "TNAME" }, new String[] { systemName },
				new String[] { "SNAME" });
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

	private int calculateHumanSize(SoftwareSystem SF) {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("REQUIREDHUMANRESOURCE", new String[] { "*" },
				new String[] { SF.getName() }, new String[] { "PROJECTNAME" });
		ResultSet rs = db.getQuery(query);
		int size = 0;
		try {
			while (rs.next()) {
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
		String query1 = db.generateSelectQuery("FACILITYRESOURCEALLOCATION", new String[] { "MODULENAME" },
				new String[] { SF.getName() }, new String[] { "PROJECTNAME" });
		String query2 = db.generateSelectQuery("HUMANRESOURCEALLOCATION", new String[] { "MODULENAME" },
				new String[] { SF.getName() }, new String[] { "PROJECTNAME" });
		String query3 = db.generateSelectQuery("FUNDINGRESOURCEALLOCATION", new String[] { "MODULENAME" },
				new String[] { SF.getName() }, new String[] { "PROJECTNAME" });

		ResultSet rs = db.getQuery(query1);
		try {
			while (rs.next()) {
				modules.add(rs.getString("MODULENAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		rs = db.getQuery(query2);
		try {
			while (rs.next()) {
				modules.add(rs.getString("MODULENAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		rs = db.getQuery(query3);
		try {
			while (rs.next()) {
				modules.add(rs.getString("MODULENAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modules.size();
	}

	public Requirement[] estimateEssentialRequirements() {
		Requirement req[] = getAllUnsatisfiedReq();
		for (int i = 0; i < req.length; i++) {
			if (req[i].numberOfSystem == 0)
				continue;
			for (int j = i + 1; j < req.length; j++) {
				if (req[j].numberOfSystem == 1 && req[i].type.equals(req[j].type)) {
					switch (req[j].type) {
					case Requirement.humanReq:
						if (req[j].specialty.equals(req[i].specialty)) {
							req[j].numberOfSystem = 0;
							req[i].numberOfSystem++;
							req[i].humans += req[i].humans;
							req[i].priority += req[j].priority;
						}
						break;
					case Requirement.facilityReq:
						if (req[j].facilityName.equals(req[i].facilityName)) {
							req[j].numberOfSystem = 0;
							req[i].numberOfSystem++;
							req[i].facilityNumber += req[j].facilityNumber;
							req[i].priority += req[j].priority;
						}
						break;
					case Requirement.fundingReq:
						if (req[j].amount.getUnit().equals(req[i].amount.getUnit())) {
							req[j].numberOfSystem = 0;
							req[i].numberOfSystem++;
							req[i].amount = new Quantity(req[j].amount.getAmount() + req[i].amount.getAmount(),
									req[j].amount.getUnit());
							req[i].priority +=req[j].priority;
						}
						break;
					}
				}
			}
		}
		Map<Double, Requirement> map = new TreeMap<Double, Requirement>();
		for(int i=0; i<req.length; i++)
			if(req[i].numberOfSystem != 0)
				map.put((double)req[i].priority/req[i].numberOfSystem, req[i]);
		ArrayList<Requirement> result = new ArrayList<Requirement>();
		for(Entry<Double, Requirement> item : map.entrySet()) {
			result.add(item.getValue());
		}
		Collections.reverse(result);
		return result.toArray(new Requirement[result.size()]);
	}

	public double addTechnologyPoints(Technology searchTechs[], Technology[] systemTechs) {
		int numberOfSimilarTechs = 0;
		for (int i = 0; i < searchTechs.length; i++) {
			for (int j = 0; j < systemTechs.length; j++)
				if (searchTechs[i].gettName().equals(systemTechs[j].gettName())) {
					numberOfSimilarTechs++;
					break;
				}
		}

		return 20.1 * ((double) numberOfSimilarTechs / searchTechs.length);
	}

	public double addHumanSizePoints(int searchSize, int systemSize) {
		double diff = Math.abs(searchSize - systemSize);
		return 10.01 * Math.exp(-diff / systemSize);
	}

	public double addModuleSizePoint(int searchSize, int systemSize) {
		double diff = Math.abs(searchSize - systemSize);
		return 10.0 * Math.exp(-diff / systemSize);
	}
}
