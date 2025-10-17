package com.pluralsight;
import java.math.BigDecimal;//for money amounts (+/-) more accurate than double
import java.time.LocalDate;
import java.time.LocalTime;

//Creating a class for Transactions
public class Transaction {
    //Date of transaction (example: 2025-10-14)
    private LocalDate date;
    //Time of transaction(example: 10:30:00)
    private LocalTime time;
    //Description for  what transactions is for
    private String description;
    //To see if , from whom the money was paid to or received from
    private String vendor;
    //Used BigDecimal to store both positive and negative: deposits or payments
    private BigDecimal amount;

//___________________CONSTRUCTOR:_________________
//Used to call when creating a new Transaction object
public Transaction(LocalDate date, LocalTime time, String description, String vendor, BigDecimal amount) {

        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;




    }

//_____________GETTERS_______________
//To allow other classes to access these values, so basically look at it
    public BigDecimal getAmount() {
        return amount;
    }

    public String getVendor() {
        return vendor;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }
    //_____________SETTERS:________________
    //To allow other classes to modify these values if needed, in other words change it

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    //__________________toString()____________________
    //To have a readable  string when the transaction is printed
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", time=" + time +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", amount=" + amount +
                '}';
    }
}
