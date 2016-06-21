package UI;

import java.util.Scanner;

import Data.AddProcess;
import Items.Development;

public class DeleteProcessView implements View {
	@Override
	public void show() {
		int choice;
		Scanner scan = new Scanner(System.in);
		System.out.println("*********************"
				+ "\n\n\nDelete Process **************\n\n");
		while (true) {
			System.out
					.println("Please choose the process you are willing to delete.");
			System.out.println("1. Develop\n" + "2. Maintenance\n" + "3. Main Menu");
			choice = scan.nextInt();
			if(choice == 1) {
				Development develop = new Development();
				if (develop.delete())
					System.out.println("Deleted successfully.");
				else System.out.println("Error occured.");
				break;
			} else if(choice == 2) {
				break;
			} else if(choice == 3) {
				break;
			} else {
				System.out.println("Invalid option. Please try again.");
			}
		}
		scan.close();
	}
	
}
