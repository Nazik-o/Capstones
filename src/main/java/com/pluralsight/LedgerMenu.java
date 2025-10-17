package com.pluralsight;
import java.util.Scanner;   // To get user input

public class LedgerMenu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Ledger ledger = new Ledger();

 //______________LEDGER MENU ________________________
    public static void showMenu() {

        boolean viewing = true;

        while (viewing) {
            System.out.println("==========================");
            System.out.println("     < LEDGER MENU >      ");
            System.out.println("==========================");
            System.out.println("A) Show All Transactions");
            System.out.println("D) Show Deposits Only");
            System.out.println("P) Show Payments Only");
            System.out.println("V) Search by Vendor");
            System.out.println("R) Reports");
            //Custom preference to be able to see Transaction summary
            System.out.println("S) Transaction Summary");
            System.out.println("B) Back to Main Menu");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A":
                    ledger.showAll();
                    break;
                case "D":
                    ledger.showDeposits();
                    break;
                case "P":
                    ledger.showPayments();
                    break;
                case "V":
                    System.out.print("Enter vendor name: ");
                    String vendor = scanner.nextLine();
                    ledger.showByVendor(vendor);
                    break;
                case "R":
                    showReportsMenu();
                    break;
                    //my personal custom implementation
                case "S":
                    ledger.showTransactionSummary();
                    break;

                case "B":
                    viewing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    // __________________REPORTS MENU______________
    public static void showReportsMenu() {
        boolean inReports = true;

        while (inReports) {
            System.out.println("\n==========================");
            System.out.println("        REPORTS MENU      ");
            System.out.println("\n==========================");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back to Ledger Menu");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    ledger.reportMonthToDate();
                    break;
                case "2":
                    ledger.reportPreviousMonth();
                    break;
                case "3":
                    ledger.reportYearToDate();
                    break;
                case "4":
                    ledger.reportPreviousYear();
                    break;
                case "5":
                    ledger.searchByVendor(scanner);
                    break;
                case "6":
                    ledger.customSearch(scanner);
                    break;
                case "0":
                    inReports = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
