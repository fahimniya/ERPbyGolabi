package UI;

import java.util.Scanner;

import Data.AddProcess;
import Items.Development;

public class AddProcessView implements View {
	@Override
	public void show() {
		int choice;
		Scanner scan = new Scanner(System.in);
		System.out.println("*********************"
				+ "\n\n\nAdd Process **************\n\n");
		while (true) {
			System.out
					.println("Please choose the process you are willing to add.");
			System.out.println("1. Develop\n" + "2. Maintenance\n" + "3. Main Menu");
			choice = scan.nextInt();
			if(choice == 1) {
				Development develop = new Development();
				new AddProcess(develop);
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
