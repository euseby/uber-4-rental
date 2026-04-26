package org.eusebiu.controller;

import org.eusebiu.dto.CarResponse;
import org.eusebiu.models.User;
import org.eusebiu.models.Vehicle;
import org.eusebiu.repository.UserRepository;
import org.eusebiu.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    // PUBLIC: GET ALL AVAILABLE CARS
    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<CarResponse> result = new ArrayList<>();

        for (Vehicle v : vehicles) {
            // Only show available cars to public
            if (v.getAvailable() != null && !v.getAvailable()) continue;

            String fullName = v.getBrand() + " " + v.getModel();
            CarResponse dto = new CarResponse(
                    v.getId(), fullName, v.getFabrYear(), v.getType(),
                    v.getLocation(), v.getRating(), v.getPricePerDay(), v.getImageUrl()
            );
            result.add(dto);
        }
        return ResponseEntity.ok(result);
    }

    // BUSINESS: GET MY CARS
    @GetMapping("/my")
    public ResponseEntity<?> getMyCars(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body("Unauthorized");

        User user = userRepository.findByEmail(principal.getName());
        if (user == null || !"business".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(403).body("Only business users can access this");
        }

        List<Vehicle> myCars = vehicleRepository.findByOwnerId(user.getId());
        List<Map<String, Object>> result = new ArrayList<>();
        for (Vehicle v : myCars) {
            result.add(Map.of(
                    "id", v.getId(),
                    "brand", v.getBrand(),
                    "model", v.getModel(),
                    "year", v.getFabrYear(),
                    "type", v.getType(),
                    "location", v.getLocation(),
                    "pricePerDay", v.getPricePerDay(),
                    "imageUrl", v.getImageUrl() != null ? v.getImageUrl() : "",
                    "available", v.getAvailable() != null ? v.getAvailable() : true,
                    "rating", v.getRating()
            ));
        }
        return ResponseEntity.ok(result);
    }

    // BUSINESS: ADD NEW CAR
    @PostMapping
    public ResponseEntity<?> addVehicle(Principal principal, @RequestBody Map<String, Object> body) {
        if (principal == null) return ResponseEntity.status(401).body("Unauthorized");

        User user = userRepository.findByEmail(principal.getName());
        if (user == null || !"business".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(403).body("Only business users can add cars");
        }
        if (user.getApproved() == null || !user.getApproved()) {
            return ResponseEntity.status(403).body("Your business account is not yet approved by admin");
        }

        Vehicle v = new Vehicle();
        v.setBrand((String) body.get("brand"));
        v.setModel((String) body.get("model"));
        v.setFabrYear(body.get("year") != null ? ((Number) body.get("year")).intValue() : 2024);
        v.setType((String) body.get("type"));
        v.setLocation((String) body.get("location"));
        v.setPricePerDay(body.get("pricePerDay") != null ? ((Number) body.get("pricePerDay")).doubleValue() : 0);
        v.setImageUrl((String) body.get("imageUrl"));
        v.setOwnerId(user.getId());
        v.setAvailable(true);
        v.setRating(0.0);

        Vehicle saved = vehicleRepository.save(v);
        return ResponseEntity.ok(Map.of("message", "Car added successfully", "id", saved.getId()));
    }

    // BUSINESS: UPDATE CAR (price, availability, image)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(Principal principal, @PathVariable Long id, @RequestBody Map<String, Object> body) {
        if (principal == null) return ResponseEntity.status(401).body("Unauthorized");

        User user = userRepository.findByEmail(principal.getName());
        if (user == null) return ResponseEntity.status(401).body("User not found");

        Optional<Vehicle> opt = vehicleRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Vehicle not found");

        Vehicle v = opt.get();

        // Only owner or admin can edit
        boolean isOwner = v.getOwnerId() != null && v.getOwnerId().equals(user.getId());
        boolean isAdmin = "ADMIN".equalsIgnoreCase(user.getRole());
        if (!isOwner && !isAdmin) return ResponseEntity.status(403).body("Not your vehicle");

        if (body.containsKey("pricePerDay")) v.setPricePerDay(((Number) body.get("pricePerDay")).doubleValue());
        if (body.containsKey("available")) v.setAvailable((Boolean) body.get("available"));
        if (body.containsKey("imageUrl")) v.setImageUrl((String) body.get("imageUrl"));
        if (body.containsKey("location")) v.setLocation((String) body.get("location"));
        if (body.containsKey("brand")) v.setBrand((String) body.get("brand"));
        if (body.containsKey("model")) v.setModel((String) body.get("model"));
        if (body.containsKey("type")) v.setType((String) body.get("type"));

        vehicleRepository.save(v);
        return ResponseEntity.ok(Map.of("message", "Vehicle updated"));
    }

    // BUSINESS: DELETE CAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(Principal principal, @PathVariable Long id) {
        if (principal == null) return ResponseEntity.status(401).body("Unauthorized");

        User user = userRepository.findByEmail(principal.getName());
        Optional<Vehicle> opt = vehicleRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Vehicle not found");

        Vehicle v = opt.get();
        boolean isOwner = v.getOwnerId() != null && v.getOwnerId().equals(user.getId());
        boolean isAdmin = "ADMIN".equalsIgnoreCase(user.getRole());
        if (!isOwner && !isAdmin) return ResponseEntity.status(403).body("Not your vehicle");

        vehicleRepository.delete(v);
        return ResponseEntity.ok(Map.of("message", "Vehicle deleted"));
    }
}