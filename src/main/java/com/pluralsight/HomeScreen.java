package com.pluralsight;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class HomeScreen {
    // This class represents the main home screen of your Accounting Ledger app.
// From here, users can add deposits, make payments, open the ledger, or exit.


        private static final Scanner scanner = new Scanner(System.in);

        // Ledger object to manage transactions (load, save, and display)
        private static final Ledger ledger = new Ledger();

        // Method that displays the main home screen menu
        public static void showHome() {
            boolean running = true;

            while (running) {
                // Display main menu options
                System.out.println("\n=====================================");
                System.out.println("   Accounting Ledger - Home Screen");
                System.out.println("=====================================");
                System.out.println("D) Add Deposit");
                System.out.println("P) Make Payment");
                System.out.println("L) View Ledger");
                System.out.println("X) Exit");
                System.out.print("Choose an option: ");

                // Get user input, clean spaces, make uppercase
                String choice = scanner.nextLine().trim().toUpperCase();

                // Handle user choice using a switch expression
                switch (choice) {
                    case "D" -> addTransaction(true);  // Deposit
                    case "P" -> addTransaction(false); // Payment
                    case "L" -> LedgerMenu.showMenu(); // Go to ledger menu
                    case "X" -> {
                        System.out.println("Exiting program... Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("⚠️ Invalid option, please try again.");
                }
            }
        }

        // This method adds either a deposit or a payment based on the parameter
        private static void addTransaction(boolean isDeposit) {
            try {
                System.out.println("\n----------------------------------");
                System.out.println(isDeposit ? "Add Deposit" : "Make Payment");
                System.out.println("----------------------------------");

                // Prompt the user for transaction details
                System.out.print("Enter description: ");
                String description = scanner.nextLine();

                System.out.print("Enter vendor: ");
                String vendor = scanner.nextLine();

                System.out.print("Enter amount: ");
                BigDecimal amount = new BigDecimal(scanner.nextLine());

                // For payments, make the amount negative
                if (!isDeposit) amount = amount.negate();

                // Create a new Transaction object
                Transaction t = new Transaction(
                        LocalDate.now(),   // today's date
                        LocalTime.now(),   // current time
                        description,
                        vendor,
                        amount
                );

                // Add the transaction to the ledger and save it
                ledger.addTransaction(t);
                System.out.println("Transaction added successfully!");

            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please try again. (" + e.getMessage() + ")");
            }
        }
}


