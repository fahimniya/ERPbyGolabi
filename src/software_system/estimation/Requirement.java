package software_system.estimation;

import java.sql.Date;

import resources.Quantity;
import software_system.Module;

public class Requirement {
	final static String humanReq = "Human";
	final static String fundingReq = "funding";
	final static String facilityReq = "facility";
	
	
	public int priority;
	public int id;
	public int humans;
	public String specialty; 
	public Quantity amount;
	public int facilityNumber; 
	public String facilityName;
	public Date SDATE;
	public int oid;
	public Module[] modules;
	public int numberOfSystem;
	public String type;
	
	public Requirement(int oid,Date SDATE, Integer id, String reqType, Integer humans, String specialty, Quantity amount, Integer facilityNumber, String facilityName, Module[] modules, int priority) {
		this.numberOfSystem = 1;
		this.id = id;
		this.SDATE = SDATE;
		this.oid = oid;
		this.modules = modules;
		this.priority = priority;
		
		switch (reqType) {
		case humanReq:
			type = humanReq;
			this.humans = humans;
			this.specialty = specialty;
			break;
		case fundingReq:
			type = fundingReq;
			this.amount = amount;
			break;
		case facilityReq:
			type = facilityReq;
			this.facilityNumber = facilityNumber;
			this.facilityName = facilityName;
			break;
		}
	}
}
