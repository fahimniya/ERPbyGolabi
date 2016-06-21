package UI;

import java.util.Scanner;

public class ProcessView implements View {

	@Override
	public void show() {
		int choice;
		Scanner scan = new Scanner(System.in);
		System.out.println("*********************"
				+ "\n\n\nProcess *****************\n\n");
		while (true) {
			System.out
					.println("Please choose the action you are willing to take.");
			System.out.println("1. Add\n" + "2. View\n" + "3. Delete\n"
					+ "4. Main Menu");
			choice = scan.nextInt();
			if(choice == 1) {
				new AddProcessView().show();
				break;
			} else if(choice == 2) {
				break;
			} else if(choice == 3) {
				new DeleteProcessView().show();
				break;
			} else if(choice == 4) {
				break;
			} else {
				System.out.println("Invalid option. Please try again.");
			}
			scan.close();
		}
	}

}
