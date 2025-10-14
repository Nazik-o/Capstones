package com.pluralsight;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;


public class LedgerMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Ledger ledger = new Ledger();

    public static void showMenu() {
        boolean viewing = true;

        while (viewing) {
            System.out.println("\n Ledger Menu");
            System.out.println("A) Show All Transactions");
            System.out.println("D) Show Deposits Only");
            System.out.println("P) Show Payments Only");
            System.out.println("V) Search by Vendor");
            System.out.println("R) Reports");
            System.out.println("B) Back to Main Menu");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim().toUpperCase();

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
                case "B" -> viewing = false;
                default -> System.out.println("️ Invalid option. Try again.");
            }
        }
    }


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
            System.out.println("6) Custom Search"); // if implemented
            System.out.println("0) Back to Ledger");

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> ledger.reportMonthToDate();
                case "2" -> ledger.reportPreviousMonth();
                case "3" -> ledger.reportYearToDate();
                case "4" -> ledger.reportPreviousYear();
                case "5" -> ledger.searchByVendor(scanner);
                case "6" -> ledger.customSearch(scanner); // optional
                case "0" -> running = false;
                default -> System.out.println("Invalid input. Try again.");
            }
        }
    }
}
