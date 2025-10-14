package com.pluralsight;

//Big decimal is a new thing i have discovered while doing my project.
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private BigDecimal amount;

//___________________Constructor:_____________________________________________________________________________
    public Transaction(BigDecimal amount, String vendor, String description, LocalTime time, LocalDate date) {
        this.amount = amount;
        this.vendor = vendor;
        this.description = description;
        this.time = time;
        this.date = date;
    }

    //__________________toString()_____________________________________________
    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", time=" + time +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", amount=" + amount +
                '}';
    }
//_____________________________Getters_______________________________________
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
}
