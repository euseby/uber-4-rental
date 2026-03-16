package org.eusebiu.dto;

public class UpcomingTrip {
    private String carName;
    private String date;
    private String status;
    private double total;

    public UpcomingTrip(String carName, String date, String status, double total) {
        this.carName = carName;
        this.date = date;
        this.status = status;
        this.total = total;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
