package org.eusebiu.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String licenseNumber;
    private String licenseExpiry;
    private String bio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getFirstName() {return this.firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return this.lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getPhone() {return this.phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public String getAddress() {return this.address;}
    public void setAddress(String address) {this.address = address;}
    public String getLicenseNumber() {return this.licenseNumber;}
    public void setLicenseNumber(String licenseNumber) {this.licenseNumber = licenseNumber;}
    public String getLicenseExpiry() {return this.licenseExpiry;}
    public void setLicenseExpiry(String licenseExpiry) {this.licenseExpiry = licenseExpiry;}
    public String getBio() {return this.bio;}
    public void setBio(String bio) {this.bio = bio;}
}
