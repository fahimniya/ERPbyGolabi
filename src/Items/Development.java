package Items;

import java.sql.Date;
import java.util.Scanner;

import Data.DatabaseInterface;

public class Development extends Process {
	private boolean hasTeam;

	public Development() {

	}

	public Development(int id, String name, Date start, Date end) {
		hasTeam = false;
		super.setID(id);
		super.projectName = name;
		Duration duration = new Duration(start, end);
		super.duration = duration;
	}

	public Development(int id, String name, Date start, Date end, int[] res,
			int[] team) {
		hasTeam = true;
		super.setID(id);
		super.projectName = name;
		Duration duration = new Duration(start, end);
		super.duration = duration;
		super.resources = res;
		super.team = team;
	}

	@Override
	public boolean add() {
		String query = "insert into DEVELOP values (" + String.valueOf(id)
				+ ", '" + projectName + "', '"
				+ duration.getBeginnig().toString() + "', '"
				+ duration.getEnd().toString() + "');";
//		System.out.println(query);
		return DatabaseInterface.update(query);
	}

	@Override
	public boolean delete() {
		System.out.println("***************************");
		System.out.println("\n\nDelete Process **************");
		System.out.println("Please enter the ID of the process you intend to delete.");
		Scanner scan = new Scanner(System.in);
		int id = scan.nextInt();
		String query = "delete from DEVELOP where ID = " + id;
		return DatabaseInterface.update(query);
	}

}
