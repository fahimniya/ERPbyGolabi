package software_system.estimation;

import java.sql.Date;

import resources.Quantity;
import software_system.Module;

public class Requirement {
	final static String humanReq = "Human";
	final static String fundingReq = "funding";
	final static String facilityReq = "facility";
	
	
	private int priority;
	public int id;
	public int humans;
	public String specialty; 
	public Quantity amount;
	public int facilityNumber; 
	public String name;
	public Date SDATE;
	public int oid;
	public Module[] modules;
	
	public Requirement(int oid,Date SDATE, Integer id, String reqType, Integer humans, String specialty, Quantity amount, Integer facilityNumber, String name, Module[] modules) {
		this.id = id;
		this.SDATE = SDATE;
		this.oid = oid;
		this.modules = modules;
		switch (reqType) {
		case humanReq:
			this.humans = humans;
			this.specialty = specialty;
			break;
		case fundingReq:
			this.amount = amount;
			break;
		case facilityReq:
			this.facilityNumber = facilityNumber;
			this.name = name;
			break;
		}
	}
}
