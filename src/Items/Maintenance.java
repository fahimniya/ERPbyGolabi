package Items;

import java.sql.Date;

public class Maintenance extends Process {
	public Maintenance(Date start, Date end, String projectName) {
		Duration duration = new Duration(start, end);
		super.setDuration(duration);
		super.setProjectName(projectName);
	}
	
	@Override
	public boolean add() {
		return true;
	}

	@Override
	public boolean delete() {
		return true;
	}

}
