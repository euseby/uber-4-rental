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
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final BookingRepository bookingRepository;
    private final UserService userService;

    public AdminController(UserRepository userRepository, VehicleRepository vehicleRepository,
                           BookingRepository bookingRepository, UserService userService) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.bookingRepository = bookingRepository;
        this.userService = userService;
    }

    private boolean isAdmin(Principal principal) {
        if (principal == null) return false;
        User user = userRepository.findByEmail(principal.getName());
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    // GET all business users (with approval status)
    @GetMapping("/businesses")
    public ResponseEntity<?> getAllBusinesses(Principal principal) {
        if (!isAdmin(principal)) return ResponseEntity.status(403).body("Forbidden");

        List<User> businesses = userRepository.findByRole("business");
        List<Map<String, Object>> result = new ArrayList<>();
        for (User b : businesses) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", b.getId());
            map.put("username", b.getUsername());
            map.put("email", b.getEmail());
            map.put("phone", b.getPhone());
            map.put("approved", b.getApproved());
            map.put("carCount", vehicleRepository.findByOwnerId(b.getId()).size());
            result.add(map);
        }
        return ResponseEntity.ok(result);
    }

    // APPROVE a business
    @PutMapping("/businesses/{id}/approve")
    public ResponseEntity<?> approveBusiness(Principal principal, @PathVariable Long id) {
        if (!isAdmin(principal)) return ResponseEntity.status(403).body("Forbidden");

        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("User not found");

        User user = opt.get();
        user.setApproved(true);
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "Business approved", "id", id));
    }

    // REJECT a business
    @PutMapping("/businesses/{id}/reject")
    public ResponseEntity<?> rejectBusiness(Principal principal, @PathVariable Long id) {
        if (!isAdmin(principal)) return ResponseEntity.status(403).body("Forbidden");

        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("User not found");

        User user = opt.get();
        user.setApproved(false);
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "Business rejected", "id", id));
    }

    // GET all clients
    @GetMapping("/clients")
    public ResponseEntity<?> getAllClients(Principal principal) {
        if (!isAdmin(principal)) return ResponseEntity.status(403).body("Forbidden");

        List<User> clients = userRepository.findByRole("client");
        List<Map<String, Object>> result = new ArrayList<>();
        for (User c : clients) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", c.getId());
            map.put("username", c.getUsername());
            map.put("email", c.getEmail());
            map.put("phone", c.getPhone());
            map.put("bookingCount", bookingRepository.countByUserId(c.getId()));
            result.add(map);
        }
        return ResponseEntity.ok(result);
    }

    // GET all vehicles
    @GetMapping("/vehicles")
    public ResponseEntity<?> getAllVehicles(Principal principal) {
        if (!isAdmin(principal)) return ResponseEntity.status(403).body("Forbidden");

        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Vehicle v : vehicles) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", v.getId());
            map.put("brand", v.getBrand());
            map.put("model", v.getModel());
            map.put("year", v.getFabrYear());
            map.put("type", v.getType());
            map.put("location", v.getLocation());
            map.put("pricePerDay", v.getPricePerDay());
            map.put("available", v.getAvailable());
            map.put("imageUrl", v.getImageUrl());

            // Resolve owner name
            String ownerName = "System";
            if (v.getOwnerId() != null) {
                Optional<User> owner = userRepository.findById(v.getOwnerId());
                if (owner.isPresent()) ownerName = owner.get().getUsername();
            }
            map.put("ownerName", ownerName);
            result.add(map);
        }
        return ResponseEntity.ok(result);
    }

    // GET all bookings
    @GetMapping("/bookings")
    public ResponseEntity<?> getAllBookings(Principal principal) {
        if (!isAdmin(principal)) return ResponseEntity.status(403).body("Forbidden");

        List<Booking> bookings = bookingRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Booking b : bookings) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", b.getId());
            map.put("startDate", b.getStartDate());
            map.put("endDate", b.getEndDate());
            map.put("status", b.getStatus());

            // Resolve user
            String userName = "Unknown";
            if (b.getUserId() != null) {
                Optional<User> u = userRepository.findById(b.getUserId());
                if (u.isPresent()) userName = u.get().getUsername();
            }
            map.put("userName", userName);

            // Resolve vehicle
            String carName = "Unknown";
            if (b.getVehicleId() != null) {
                Optional<Vehicle> v = vehicleRepository.findById(b.getVehicleId());
                if (v.isPresent()) carName = v.get().getBrand() + " " + v.get().getModel();
            }
            map.put("carName", carName);
            result.add(map);
        }
        return ResponseEntity.ok(result);
    }

    // GET dashboard stats
    @GetMapping("/stats")
    public ResponseEntity<?> getAdminStats(Principal principal) {
        if (!isAdmin(principal)) return ResponseEntity.status(403).body("Forbidden");

        long totalUsers = userRepository.count();
        long totalVehicles = vehicleRepository.count();
        long totalBookings = bookingRepository.count();
        long pendingBusinesses = userRepository.findByRoleAndApproved("business", null).size();

        return ResponseEntity.ok(Map.of(
                "totalUsers", totalUsers,
                "totalVehicles", totalVehicles,
                "totalBookings", totalBookings,
                "pendingBusinesses", pendingBusinesses
        ));
    }
}
