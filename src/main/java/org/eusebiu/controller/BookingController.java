package org.eusebiu.controller;

import org.eusebiu.models.Booking;
import org.eusebiu.models.User;
import org.eusebiu.models.Vehicle;
import org.eusebiu.repository.BookingRepository;
import org.eusebiu.repository.UserRepository;
import org.eusebiu.repository.VehicleRepository;
import org.eusebiu.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public BookingController(BookingRepository bookingRepository, UserService userService, VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    // POST http://localhost:8080/api/bookings
    @PostMapping
    public ResponseEntity<?> createBooking(Principal principal, @RequestBody Map<String, Object> request) {
        try {
            if (principal == null) {
                return ResponseEntity.status(401).body("Nu esti autorizat!");
            }

            String email = principal.getName();
            User user = userService.getUserByEmail(email);

            Booking booking = new Booking();
            booking.setUserId(user.getId());
            booking.setVehicleId(Long.valueOf(request.get("vehicleId").toString()));
            booking.setStartDate(request.get("startDate").toString());
            booking.setEndDate(request.get("endDate").toString());
            booking.setStatus("Confirmed");

            Booking saved = bookingRepository.save(booking);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la crearea rezervarii: " + e.getMessage());
        }
    }

    // GET /api/bookings/business/my-bookings
    @GetMapping("/business/my-bookings")
    public ResponseEntity<?> getBusinessBookings(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body("Unauthorized");
        
        User owner = userRepository.findByEmail(principal.getName());
        if (owner == null || !"business".equalsIgnoreCase(owner.getRole())) {
            return ResponseEntity.status(403).body("Only business users can access this");
        }

        List<Vehicle> myVehicles = vehicleRepository.findByOwnerId(owner.getId());
        List<Long> vehicleIds = myVehicles.stream().map(Vehicle::getId).collect(Collectors.toList());

        if (vehicleIds.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        List<Booking> bookings = bookingRepository.findByVehicleIdIn(vehicleIds);
        
        // Enhance with vehicle details and client details
        List<Map<String, Object>> response = new ArrayList<>();
        for (Booking b : bookings) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", b.getId());
            map.put("startDate", b.getStartDate());
            map.put("endDate", b.getEndDate());
            map.put("status", b.getStatus());
            
            Vehicle v = vehicleRepository.findById(b.getVehicleId()).orElse(null);
            if (v != null) {
                map.put("carBrand", v.getBrand());
                map.put("carModel", v.getModel());
                map.put("carPrice", v.getPricePerDay());
                map.put("carImage", v.getImageUrl());
            }
            
            User client = userRepository.findById(b.getUserId()).orElse(null);
            if (client != null) {
                map.put("clientName", client.getUsername());
                map.put("clientEmail", client.getEmail());
            }
            
            response.add(map);
        }

        return ResponseEntity.ok(response);
    }
}
