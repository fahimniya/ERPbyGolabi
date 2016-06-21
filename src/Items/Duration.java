package Items;

import java.sql.Date;

public class Duration {
	private Date beginnig, end;
	private boolean endSet;
	
	public Duration(Date beginnig, Date end) {
		this.beginnig = beginnig;
		this.end = end;
		endSet = true;
	}
	
	public Duration(Date beginnig) {
		this.beginnig = beginnig;
		endSet = false;
	}

	public Date getEnd() {
		if(endSet)
			return end;
		else
			return null;
	}

	public boolean setEnd(Date end) {
		if(endSet)
			return false;
		this.end = end;
		endSet = true;
		return true;
	}

	public Date getBeginnig() {
		return beginnig;
	}
	
	public long difference() {
		try {
			return (end.getTime() - beginnig.getTime())/(24 * 60 * 60 * 1000);
		} catch (Exception ex) {
			return 0;
		}
		
	}
}
