//importing a necessary libraries

package com.pluralsight;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

//This class shows and handles the all the user interactions like what user sees and types.
public class LedgerMenu {
    //setting it to final to manage transactions and reports
    private static final Scanner scanner = new Scanner(System.in);
    private static final Ledger ledger = new Ledger();
//Displays ledger menu and processes user choices
    public static void showMenu() {
        boolean viewing = true;

        while (viewing) {
            //Our menu options for user to choose from
            System.out.println("\n Ledger Menu");
            System.out.println("A) Show All Transactions");
            System.out.println("D) Show Deposits Only");
            System.out.println("P) Show Payments Only");
            System.out.println("V) Search by Vendor");
            System.out.println("R) Reports");
            System.out.println("B) Back to Main Menu");
            System.out.print("Choose an option: ");

            // Read and sanitize user input
            String choice = scanner.nextLine().trim().toUpperCase();

            //User choice in case they choose the following options:
            //Calls functions inside Ledger to show transactions
            switch (choice) {
                case "A" -> ledger.showAll();
                case "D" -> ledger.showDeposits();
                case "P" -> ledger.showPayments();
                case "R" -> showReportsMenu();
                case "V" -> {
                    System.out.print("Enter vendor name: ");
                    String vendor = scanner.nextLine();
                    ledger.showByVendor(vendor);
                }
                //stops the loop and goes "back"
                case "B" -> viewing = false;
                default -> System.out.println("️ Invalid option. Try again.");
            }
        }
    }

// If user chose the see Reports
    private static void showReportsMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nReports Menu:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back to Ledger");

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> ledger.reportMonthToDate();
                case "2" -> ledger.reportPreviousMonth();
                case "3" -> ledger.reportYearToDate();
                case "4" -> ledger.reportPreviousYear();
                case "5" -> ledger.searchByVendor(scanner);
                case "6" -> ledger.customSearch(scanner);
                //stops the switch statement because of 'false' and goes back to ledger menu
                case "0" -> running = false;
                default -> System.out.println("Invalid input. Try again.");
            }
        }
    }
}
