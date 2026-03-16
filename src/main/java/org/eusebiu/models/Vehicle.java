package org.eusebiu.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private int fabrYear;
    private String imageUrl;
    private double pricePerDay;

    // --- Câmpuri noi pentru a se potrivi cu Frontend-ul ---
    private String type;     // ex: "Sedan", "SUV"
    private String location; // ex: "Downtown", "Uptown"
    private double rating = 0.0;   // Default 0.0

    // Getters and Setters pentru TOATE variabilele
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getFabrYear() { return fabrYear; }
    public void setFabrYear(int fabrYear) { this.fabrYear = fabrYear; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}