package org.eusebiu.dto;

public class DashboardSummary {
    private int activeBookings;
    private int totalTrips;
    private double savedAmount;
    private double rating;

    // Constructor cu toti parametrii
    public DashboardSummary(int activeBookings, int totalTrips, double savedAmount, double rating) {
        this.activeBookings = activeBookings;
        this.totalTrips = totalTrips;
        this.savedAmount = savedAmount;
        this.rating = rating;
    }

    public int getActiveBookings() {
        return activeBookings;
    }

    public void setActiveBookings(int activeBookings) {
        this.activeBookings = activeBookings;
    }

    public int getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(int totalTrips) {
        this.totalTrips = totalTrips;
    }

    public double getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(double savedAmount) {
        this.savedAmount = savedAmount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
