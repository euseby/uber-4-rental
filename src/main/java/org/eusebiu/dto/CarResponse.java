package org.eusebiu.dto;

public class CarResponse {
    private Long id;
    private String name;
    private int year;
    private String type;
    private String location;
    private double rating;
    private double pricePerDay;
    private String imageUrl;
    public CarResponse(Long id, String name, int year, String type, String location, double rating, double pricePerDay, String imageUrl) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.type = type;
        this.location = location;
        this.rating = rating;
        this.pricePerDay = pricePerDay;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRating() {return rating;}

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImageUrl(){return imageUrl;}
    public void setImageUrl(String imageUrl){this.imageUrl = imageUrl;}
}
