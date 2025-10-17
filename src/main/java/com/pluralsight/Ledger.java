package com.pluralsight;

import java.io.*;                // for file reading and writing
import java.math.BigDecimal;     // for precise money values
import java.time.LocalDate;      // for date handling
import java.time.LocalTime;      // for time handling
import java.util.ArrayList;      // for dynamic list of transactions
import java.util.List;           // interface for ArrayList
import java.util.Scanner;

// The Ledger class manages all transactions — it loads from file, stores in memory, saves back, and displays results.
public class Ledger {

    // List to store all Transaction objects in memory while the program runs
    private List<Transaction> transactions = new ArrayList<>();

    // File path to where our transactions will be stored
    private final String filePath = "data/transactions.csv";

    // Constructor — automatically loads transactions from the file when Ledger object is created
    public Ledger() {
        loadFromFile();
    }

    // Adds a new transaction to the list and then saves all transactions back to the CSV file
    public void addTransaction(Transaction t) {
        transactions.add(t);
        saveToFile();
    }

    // Loads all transactions from the CSV file
    public void loadFromFile() {
        transactions.clear(); // Clear the list to avoid duplicate entries when reloading

        try {
            // Create the file object
            File file = new File(filePath);

            // If the file doesn’t exist yet, show a message and stop loading
            if (!file.exists()) {
                System.out.println("No existing transaction file found.");
                return;
            }


            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line;
            // Keep reading until we reach the end of the file
            while ((line = reader.readLine()) != null) {
                // Split the line by "|" symbol into 5 parts
                String[] parts = line.split("\\|");

                // Check if the line has exactly 5 pieces of data
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);   // Convert text into LocalDate
                    LocalTime time = LocalTime.parse(parts[1]);   // Convert text into LocalTime
                    String description = parts[2];                // Get description text
                    String vendor = parts[3];                     // Get vendor text
                    BigDecimal amount = new BigDecimal(parts[4]); // Convert string to BigDecimal for money

                    // Create a new Transaction object and add it to our list
                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    transactions.add(t);
                }
            }

            reader.close(); // Always close the file when done


        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }

    // Saves all transactions from memory back into the CSV file
    public void saveToFile() {
        try {
            // Create a folder called 'data' if it doesn't exist
            File folder = new File("data");
            if (!folder.exists()) {
                folder.mkdir();
            }

            // Create or open the file for writing
            FileWriter fw = new FileWriter(filePath);
            BufferedWriter writer = new BufferedWriter(fw);

            // Write each transaction as one line in the file
            for (Transaction t : transactions) {
                writer.write(
                        t.getDate() + "|" +
                                t.getTime() + "|" +
                                t.getDescription() + "|" +
                                t.getVendor() + "|" +
                                t.getAmount()
                );
                writer.newLine(); // Move to the next line after each transaction
            }

            writer.close(); // Always close writer to save changes
            System.out.println("Transactions saved successfully to file.");

        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    // Displays all transactions in the ledger
    public void showAll() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions to display.");
            return;
        }

        System.out.println("\n--- ALL TRANSACTIONS ---");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    // Shows only deposits (where amount > 0)
    public void showDeposits() {
        System.out.println("\n--- DEPOSITS ---");
        for (Transaction t : transactions) {
            if (t.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                System.out.println(t);
            }
        }
    }

    // Shows only payments (where amount < 0)
    public void showPayments() {
        System.out.println("\n--- PAYMENTS ---");
        for (Transaction t : transactions) {
            if (t.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                System.out.println(t);
            }
        }
    }

    // Shows all transactions for a specific vendor name
    public void showByVendor(String vendor) {
        System.out.println("\n--- TRANSACTIONS FOR VENDOR: " + vendor + " ---");
        for (Transaction t : transactions) {
            if (t.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(t);
            }

        }
    }

    // ______________REPORT METHODS________________________
    // 1) Month To Date — shows all transactions from this month
    public void reportMonthToDate() {
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        System.out.println("\n--- REPORT: MONTH TO DATE ---");
        for (Transaction t : transactions) {
            if (t.getDate().getMonthValue() == currentMonth && t.getDate().getYear() == currentYear) {
                System.out.println(t);
            }
        }
    }

    // 2) Previous Month
    public void reportPreviousMonth() {
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        int prevMonth = currentMonth - 1;
        int yearOfPrevMonth = currentYear;

        // If it's January, previous month is December of last year
        if (prevMonth == 0) {
            prevMonth = 12;
            yearOfPrevMonth = currentYear - 1;
        }

        System.out.println("\n--- REPORT: PREVIOUS MONTH ---");
        for (Transaction t : transactions) {
            if (t.getDate().getMonthValue() == prevMonth &&
                    t.getDate().getYear() == yearOfPrevMonth) {
                System.out.println(t);
            }
        }
    }

    // 3) Year To Date
    public void reportYearToDate() {
        int currentYear = LocalDate.now().getYear();

        System.out.println("\n--- REPORT: YEAR TO DATE ---");
        for (Transaction t : transactions) {
            if (t.getDate().getYear() == currentYear) {
                System.out.println(t);
            }
        }
    }

    // 4) Previous Year
    public void reportPreviousYear() {
        int lastYear = LocalDate.now().getYear() - 1;

        System.out.println("\n--- REPORT: PREVIOUS YEAR ---");
        for (Transaction t : transactions) {
            if (t.getDate().getYear() == lastYear) {
                System.out.println(t);
            }
        }
    }

    // 5) Search by Vendor
    public void searchByVendor(Scanner scanner) {
        System.out.print("Enter vendor name to search: ");
        String name = scanner.nextLine();

        System.out.println("\n--- REPORT: SEARCH RESULTS FOR \"" + name + "\" ---");
        for (Transaction t : transactions) {
            if (t.getVendor().equalsIgnoreCase(name)) {
                System.out.println(t);
            }
        }
    }
    //_______________Bonus part : CUSTOM SEARCH______________
    public void customSearch(Scanner scanner) {
        System.out.println("\n===============================");
        System.out.println("         CUSTOM SEARCH          ");
        System.out.println("===============================");

        System.out.print("Enter a keyword to search (description/vendor/amount): ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        boolean found = false;

        for (int i = 0; i < transactions.size(); i++) {
            // Get one transaction at a time from the list
            Transaction t = transactions.get(i);

            // Convert everything to lowercase for easy comparison
            String description = t.getDescription().toLowerCase();
            String vendor = t.getVendor().toLowerCase();
            String amount = t.getAmount().toString();

            // Check if the keyword appears in ANY of these fields
            if (description.contains(keyword)
                    || vendor.contains(keyword)
                    || amount.contains(keyword)) {

                // If a match is found, print the transaction
                System.out.println(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for keyword: " + keyword);
        }
    }
    // _________________TRANSACTION SUMMARY__________________
    //Custom preference: to see the total deposit and payments
    public void showTransactionSummary() {

        // Count how many deposits and payments happened this month
        int deposits = 0;
        int payments = 0;
        // Find the current month and year
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        // Go through all transactions in the file
        for (Transaction t : transactions) {
            // Only look at the transactions from this month and year
            if (t.getDate().getMonthValue() == currentMonth && t.getDate().getYear() == currentYear) {

                // If the amount is positive, it's a deposit
                if (t.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    deposits++;
                }
                // If the amount is negative, it's a payment
                else if (t.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                    payments++;
                }
            }
        }
        // calculate the balance
        BigDecimal balance = BigDecimal.ZERO;
        for (Transaction t : transactions) {
            if (t.getDate().getMonthValue() == currentMonth && t.getDate().getYear() == currentYear) {
                balance = balance.add(t.getAmount());
            }
        }

        // Add up all transactions (deposits + payments)
        int totalTransactions = deposits + payments;

        // If you made more than 15 transactions this month, charge a $10 fee
        if (totalTransactions > 15) {
            System.out.println(" You went over the monthly transaction limit!");
            BigDecimal fee = new BigDecimal("10.00");
            balance = balance.subtract(fee);
            System.out.println(" Fee applied: $10.00");
        }

        // Show the results on screen
        System.out.println("\n===============================");
        System.out.println("       TRANSACTION SUMMARY      ");
        System.out.println("===============================");
        System.out.println(" Deposits: " + deposits);
        System.out.println(" Payments: " + payments);
        System.out.println(" Total Transactions: " + totalTransactions);
        System.out.println("-------------------------------");
        System.out.println("  Current Balance: $" + balance);
        System.out.println("===============================\n");
    }


}
