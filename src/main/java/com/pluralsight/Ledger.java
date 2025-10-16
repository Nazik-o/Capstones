package com.pluralsight;

import java.io.*;                // for file reading and writing
import java.math.BigDecimal;     // for precise money values
import java.time.LocalDate;      // for date handling
import java.time.LocalTime;      // for time handling
import java.util.ArrayList;      // for dynamic list of transactions
import java.util.List;           // interface for ArrayList

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

            // Set up file reader and buffered reader to read line by line
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
            System.out.println("Transactions loaded successfully from file.");

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
}
