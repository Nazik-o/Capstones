package com.pluralsight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

// The HomeScreen class is the main menu of the Accounting Ledger program.
// From here, users can add deposits, make payments, view the ledger, or exit the app.
public class HomeScreen {

    // One Scanner for all user input
    private static final Scanner scanner = new Scanner(System.in);

    // One Ledger object to manage transactions
    private static final Ledger ledger = new Ledger();

    // Displays the main home screen and waits for user input
    public static void showHome() {
        System.out.println("$------------------$----------------$");
        System.out.println("         Welcome to MoneyTalks!     ");
        System.out.println("$------------------$----------------$");
        System.out.println("Every penny tells a story...");
        System.out.print("\nWould you like to open your ledger? (Y/N): ");
        String response = scanner.nextLine().trim().toUpperCase();

        //
        if (!response.equals("Y")) {
            System.out.println("\nGoodbye! Keep your money talking, not walking");
            return;
        }

        System.out.println("\n...Loading your financial dashboard...");




        boolean running = true; // keeps the program running until user exits

        while (running) {
            System.out.println("\n$----------------------------------$");
            System.out.println("     ACCOUNTING LEDGER - HOME");
            System.out.println("$----------------------------------$");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) View Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");

            // Read input, trim spaces, and convert to uppercase for easier comparison
            String choice = scanner.nextLine().trim().toUpperCase();

            // Classic switch statement (Java 8/11 compatible)
            switch (choice) {
                case "D":
                    addTransaction(true);  // Add a deposit
                    break;

                case "P":
                    addTransaction(false); // Add a payment
                    break;

                case "L":
                    LedgerMenu.showMenu(); // Open the Ledger Menu
                    break;

                case "X":
                    System.out.println("Exiting program... Goodbye!");
                    running = false;       // stop the loop
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Adds either a deposit or payment, depending on isDeposit flag
    private static void addTransaction(boolean isDeposit) {
        try {
            System.out.println("\n----------------------------------");
            System.out.println(isDeposit ? "Add Deposit" : "Make Payment");
            System.out.println("----------------------------------");

            // Ask user for transaction details
            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            System.out.print("Enter vendor: ");
            String vendor = scanner.nextLine();

            System.out.print("Enter amount: ");
            BigDecimal amount = new BigDecimal(scanner.nextLine());

            // For payments, the amount should be negative
            if (!isDeposit) {
                amount = amount.negate();
            }

            // Create a new transaction with current date and time
            Transaction t = new Transaction(
                    LocalDate.now(),   // today's date
                    LocalTime.now(),   // current time
                    description,
                    vendor,
                    amount
            );

            // Add the new transaction to the ledger and save it
            ledger.addTransaction(t);
            System.out.println("Transaction added successfully!");

        } catch (Exception e) {
            // This runs if the user types something invalid, like a non-number for amount
            System.out.println("Error: Invalid input. Please try again.");
        }
    }
}
