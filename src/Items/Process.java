package Items;

public abstract class Process extends Item {
	protected Duration duration;
	protected String projectName;
	protected int [] resources;
	protected int [] team;
	
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setResources(int[] resources) {
		this.resources = resources;
	}
	public void setTeam(int[] team) {
		this.team = team;
	}
	public void setID(int id) {
		super.id = id;
	}
	protected int getID() {
		return super.id;
	}
	
}
