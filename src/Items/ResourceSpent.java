package Items;

import java.sql.Date;

public class ResourceSpent {
	private Resource resource;
	private Duration duration;
	private Process project;
	
	public ResourceSpent(Resource resource, Process project, Date beginnig, Date end) {
		this.resource = resource;
		this.project = project;
		duration = new Duration(beginnig, end);
	}
	
	public ResourceSpent(Resource resource, Process project, Date beginnig) {
		this.resource = resource;
		this.project = project;
		duration = new Duration(beginnig);
	}

	public Resource getResource() {
		return resource;
	}

	public long getDuration() {
		return duration.difference();
	}

	public Process getProject() {
		return project;
	}
	
	public void setEnd (Date end) throws Exception {
		if(!duration.setEnd(end))
			throw new Exception();
	}
}
