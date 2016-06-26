package Items;

import java.sql.Date;

import Data.DatabaseInterface;

public class HumanResource {
	private Date from, to;
	private User user;
	
	public HumanResource(User user, Date from, Date to) {
		this.user = user;
		this.from = from;
		this.to = to;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean addHumanResource() {
		String query = "insert into HUMANRESOURCE values(\'" + user.getUsername() + "\', \'" + from + "\', \'" + to + "\');";  
		DatabaseInterface db = new DatabaseInterface();
		
		return db.update(query);
	}

	public boolean deleteHumanResource() {
		String query = "delete from HUMANRESOURCE where USERNAME = \'" + user.getUsername() + "\';";   
		DatabaseInterface db = new DatabaseInterface();
		
		return db.update(query);
	}

}
