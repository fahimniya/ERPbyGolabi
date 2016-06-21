package UI;

import java.util.Scanner;

public class MainView implements View {

	@Override
	public void show() {

		int choice;
		boolean exit = false;
		System.out.println("Welcome to Golabi Pro ERP System.");
		System.out
				.println("************************************************\n");
		while (true) {
			System.out.println("Please enter category number.");
			System.out.println("1. Processes\n" + "2. Resource\n" + "3. Exit");

			while (true) {
				Scanner scan = new Scanner(System.in);
				if (scan.hasNextInt()) {
					choice = scan.nextInt();
					if (choice == 1) {
						new ProcessView().show();
						break;
					} else if (choice == 2) {

						break;
					} else if (choice == 3) {
						exit = true;
						break;
					} else {
						System.out.println("Invalid option. Please try again.");
					}
				} else
					scan = new Scanner(System.in);
			}
			if (exit)
				break;
		}
	}
}
