package Items;

import java.sql.Date;

public abstract class Process {
	protected String projectName;
	protected Date from, to;
	protected int id;
	
	public Process(String projectName, Date from, Date to) {
		this.projectName = projectName;
		this.from = from;
		this.to = to;
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
