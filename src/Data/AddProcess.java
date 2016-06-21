package Data;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Items.Development;
import Items.Maintenance;

public class AddProcess {
	private Date start, end;
	private String projectName;
	private int id;
	private int[] resID, teamID;

	public AddProcess(Development develop) {
		addDevelop();
	}

	private void addDevelop() {
		Development develop;
		Scanner scan = new Scanner(System.in);
		System.out.println("***************************");
		System.out.println("\n\nEnter project ID:");
		id = scan.nextInt();
		System.out.println("Enter project name:");
		scan.nextLine();
		projectName = scan.nextLine();
		System.out.println("Enter start date: (yyyy-mm-dd");
		start = Date.valueOf(scan.nextLine());
		System.out
				.println("Enter end if applicable, otherwise just press Enter.");
		try {
			end = Date.valueOf(scan.nextLine());
		} catch (Exception e) {

		}
		System.out
				.println("Choose the developer team: (only IDs (ID1 ID2 ...))");
		ResultSet rs = DatabaseInterface
				.getQuery("select ID, NAME, FAMILY from HUMAN");
		Map<String, Integer> tn = new HashMap<String, Integer>();
		String[] cols = { "ID", "NAME", "FAMILY" };
		tn.put("ID", 1);
		tn.put("NAME", 2);
		tn.put("FAMILY", 2);
		DatabaseInterface
				.showResult("Developers**************\n", rs, tn, cols);
		try {
			String temp = scan.nextLine();
			String[] tempArray = temp.split(" ");
			int[] teamID = new int[tempArray.length];
			for (int i = 0; i < teamID.length; i++) {
				teamID[i] = Integer.parseInt(tempArray[i]);
			}
		} catch (Exception e) {
			teamID = new int[0];
		}
		System.out
				.println("Please choose the resourses used in this process: (only IDs)");
		DatabaseInterface
				.showResult("Developers**************\n", rs, tn, cols);
		rs = DatabaseInterface.getQuery("select ID, NAME from EQUIPMENT");
		tn = new HashMap<String, Integer>();
		String[] col = { "ID", "NAME" };
		tn.put("ID", 1);
		tn.put("NAME", 2);
		DatabaseInterface.showResult("Equipments**************\n", rs, tn, col);
		try {
			String resource = scan.nextLine();
			String[] resArray = resource.split(" ");
			resID = new int[resArray.length];
			for (int i = 0; i < resID.length; i++) {
				resID[i] = Integer.parseInt(resArray[i]);
			}
		} catch (Exception e) {
			resID = new int[0];
		}
		if(resID.length != 0 && teamID.length != 0) 
			develop = new Development(id, projectName, start, end, resID, teamID);
		develop = new Development(id, projectName, start, end);
		if (develop.add())
			System.out.println("Development process successfully added.");
		else
			System.out.println("Could not add this record.");
		scan.close();
	}

	public AddProcess(Maintenance maintain) {

	}
}
