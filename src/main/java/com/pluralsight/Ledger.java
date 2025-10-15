package com.pluralsight;
import java.io.*;
import java.math.BigDecimal;//for money amounts (+/-) more accurate than double
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

//This part of the program is basically the brain
public class Ledger {
    private final List<Transaction> transactions = new ArrayList<>();
    private final String filePath = "data/transactions.csv";

    //it loads data from the CSV.
    public Ledger() {
        loadFromFile();
    }
// adds the transaction and stores back to Csv
    public void addTransaction(Transaction t) {
        transactions.add(t);
        saveToFile();
    }

//loads and reads every line in csv file and splits it
    public void loadFromFile() {
        transactions.clear();
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.out.println(" No existing transaction file found.");
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    BigDecimal amount = new BigDecimal(parts[4]);
                    transactions.add(new Transaction(date, time, description, vendor, amount));
                }
            }
            System.out.println(" Transactions loaded from file.");
        } catch (IOException e) {
            System.out.println(" Error loading transactions: " + e.getMessage());
        }

    }
//writes each transaction back into Csv
    public void saveToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            for (Transaction t : transactions) {
                writer.write(String.join("|",
                        t.getDate().toString(),
                        t.getTime().toString(),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount().toString()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    public void showAll() {
        transactions.stream()
                .sorted(Comparator.comparing(Transaction::getDate).reversed()
                        .thenComparing(Transaction::getTime).reversed())
                .forEach(System.out::println);
    }
//Filter only deposits (amount > 0)
    public void showDeposits() {
        transactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .forEach(System.out::println);
    }
//Filter only payments (amount < 0)
    public void showPayments() {
        transactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) < 0)
                .forEach(System.out::println);
    }

    public void showByVendor(String vendor) {
        transactions.stream()
                .filter(t -> t.getVendor().equalsIgnoreCase(vendor))
                .forEach(System.out::println);
    }
    public void reportMonthToDate() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);

        transactions.stream()
                .filter(t -> !t.getDate().isBefore(startOfMonth))
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .forEach(System.out::println);
    }

    public void reportPreviousMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstOfThisMonth = today.withDayOfMonth(1);
        LocalDate firstOfLastMonth = firstOfThisMonth.minusMonths(1);
        LocalDate lastOfLastMonth = firstOfThisMonth.minusDays(1);

        transactions.stream()
                .filter(t -> !t.getDate().isBefore(firstOfLastMonth) && !t.getDate().isAfter(lastOfLastMonth))
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .forEach(System.out::println);
    }

    public void reportYearToDate() {
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);

        transactions.stream()
                .filter(t -> !t.getDate().isBefore(startOfYear))
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .forEach(System.out::println);
    }

    public void reportPreviousYear() {
        int lastYear = LocalDate.now().minusYears(1).getYear();

        transactions.stream()
                .filter(t -> t.getDate().getYear() == lastYear)
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .forEach(System.out::println);
    }
    public void searchByVendor(Scanner scanner) {
        System.out.print("Enter vendor name: ");
        String vendor = scanner.nextLine().trim().toLowerCase();

        transactions.stream()
                .filter(t -> t.getVendor().toLowerCase().contains(vendor))
                .forEach(System.out::println);
    }

    //Bonus : custom search
    public void customSearch(Scanner scanner) {
        System.out.print("Start date (yyyy-mm-dd or blank): ");
        String startInput = scanner.nextLine();
        System.out.print("End date (yyyy-mm-dd or blank): ");
        String endInput = scanner.nextLine();
        System.out.print("Description (or blank): ");
        String descInput = scanner.nextLine().toLowerCase();
        System.out.print("Vendor (or blank): ");
        String vendorInput = scanner.nextLine().toLowerCase();

        LocalDate startDate = startInput.isEmpty() ? LocalDate.MIN : LocalDate.parse(startInput);
        LocalDate endDate = endInput.isEmpty() ? LocalDate.MAX : LocalDate.parse(endInput);

        transactions.stream()
                .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
                .filter(t -> descInput.isEmpty() || t.getDescription().toLowerCase().contains(descInput))
                .filter(t -> vendorInput.isEmpty() || t.getVendor().toLowerCase().contains(vendorInput))
                .forEach(System.out::println);
    }

}


