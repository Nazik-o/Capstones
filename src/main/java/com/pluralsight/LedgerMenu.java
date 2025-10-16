// Handles what the user sees and types in the console
package com.pluralsight;

import java.util.Scanner;   // To get user input

public class LedgerMenu {

    // Shared Scanner for user input (so we can reuse it everywhere)
    private static final Scanner scanner = new Scanner(System.in);

    // Create one Ledger object to manage transactions
    private static final Ledger ledger = new Ledger();

    // This method displays the Ledger menu and processes user choices
    public static void showMenu() {

        boolean viewing = true;  // keeps the menu running until user goes back

        while (viewing) {
            System.out.println("\n==========================");
            System.out.println("        LEDGER MENU       ");
            System.out.println("==========================");
            System.out.println("A) Show All Transactions");
            System.out.println("D) Show Deposits Only");
            System.out.println("P) Show Payments Only");
            System.out.println("V) Search by Vendor");
            System.out.println("B) Back to Main Menu");
            System.out.print("Choose an option: ");

            // Read and clean up user input
            String choice = scanner.nextLine().trim().toUpperCase();

            // Process the user's choice
            switch (choice) {

                // Show all transactions in the ledger
                case "A":
                    ledger.showAll();
                    break;

                // Show deposits (amount > 0)
                case "D":
                    ledger.showDeposits();
                    break;

                // Show payments (amount < 0)
                case "P":
                    ledger.showPayments();
                    break;

                // Search transactions by vendor name
                case "V":
                    System.out.print("Enter vendor name: ");
                    String vendor = scanner.nextLine();
                    ledger.showByVendor(vendor);
                    break;

                // Go back to the main menu
                case "B":
                    viewing = false;
                    break;

                // Handle invalid input
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
