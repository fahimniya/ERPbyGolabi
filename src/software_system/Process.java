package software_system;

import java.sql.Date;

public abstract class Process {
	protected String projectName;
	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public int getId() {
		return id;
	}
	protected Date from, to;
	protected int id;
	protected String type;
	
	public Process(String projectName, Date from, Date to, String type) {
		this.projectName = projectName;
		this.from = from;
		this.to = to;
		this.type = type;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public abstract int generateId();
	public abstract boolean add();
	public abstract boolean remove();
	public abstract boolean update();
}
