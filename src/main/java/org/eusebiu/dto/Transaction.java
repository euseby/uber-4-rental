package org.eusebiu.dto;

public class Transaction {
    private String date;
    private String description;
    private String status;
    private double amount;

    public Transaction(String date, String description, String status, double amount) {
        this.date = date;
        this.description = description;
        this.status = status;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
