package org.eusebiu.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "vehicles")
public class Vehicle {
        @Id
        @GeneratedValue
        private Long id;
        private String brand;
        private String model;
        private int fabrYear;
        private String imageUrl;
        private double pricePerDay;

        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getBrand() {
            return brand;
        }
        public void setBrand(String brand) {
            this.brand = brand;
        }
        public String getModel() {
            return model;
        }
        public void setModel(String model) {
            this.model = model;
        }
        public int getFabrYear() {
            return fabrYear;
        }
        public void setFabrYear(int fabrYear) {
            this.fabrYear = fabrYear;
        }
        public String getImageUrl() {
            return imageUrl;
        }
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }


}
