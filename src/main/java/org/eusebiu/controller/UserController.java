package org.eusebiu.controller;

import org.eusebiu.dto.*;
import org.eusebiu.models.Booking;
import org.eusebiu.models.User;
import org.eusebiu.repository.VehicleRepository;
import org.eusebiu.security.JwtUtil;
import org.eusebiu.service.UserService;
import org.eusebiu.repository.BookingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;

    public UserController(UserService userService, JwtUtil jwtUtil, BookingRepository bookingRepository, VehicleRepository vehicleRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.bookingRepository = bookingRepository;
        this.vehicleRepository = vehicleRepository;
    }

    // API 1: REGISTER
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // API 1.5: VERIFY EMAIL
    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        try {
            userService.verifyUser(token);
            // In a real app you might redirect to a frontend success page:
            // return ResponseEntity.status(302).header("Location", "http://localhost:3000/login?verified=true").build();
            return ResponseEntity.ok("Contul a fost verificat cu succes! Acum te poti loga.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API 2: LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            String token = jwtUtil.generateToken(user.getEmail());
            LoginResponse response = new LoginResponse(token, user.getUsername(), user.getEmail(), user.getRole());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API 3: GET PROFILE
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Principal principal) {
        try {
            if (principal == null) return ResponseEntity.status(401).body("Unauthorized");
            User user = userService.getUserByEmail(principal.getName());
            UserProfile profileDto = new UserProfile(user);
            return ResponseEntity.ok(profileDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API 4: UPDATE PROFILE
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(Principal principal, @RequestBody UpdateProfileRequest request) {
        try {
            if (principal == null) return ResponseEntity.status(401).body("Unauthorized");
            User userModificat = userService.updateUserProfile(principal.getName(), request);
            UserProfile profileDto = new UserProfile(userModificat);
            return ResponseEntity.ok(profileDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API 5: GET DASHBOARD SUMMARY
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardSummary(Principal principal) {
        try {
            if (principal == null) return ResponseEntity.status(401).body("Unauthorized");
            User user = userService.getUserByEmail(principal.getName());
            int active = bookingRepository.countByUserIdAndStatus(user.getId(), "Confirmed");
            int total = bookingRepository.countByUserId(user.getId());
            DashboardSummary dashboardStats = new DashboardSummary(active, total, 0.0, 5.0);
            return ResponseEntity.ok(dashboardStats);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API 6: GET UPCOMING TRIPS
    @GetMapping("/trips/upcoming")
    public ResponseEntity<?> getUpcomingTrips(Principal principal) {
        try {
            if (principal == null) return ResponseEntity.status(401).body("Unauthorized");
            User user = userService.getUserByEmail(principal.getName());
            List<Booking> bookingsFromDb = bookingRepository.findByUserId(user.getId());
            List<UpcomingTrip> tripsForFrontend = new ArrayList<>();

            for (Booking b : bookingsFromDb) {
                String carName = "Unknown Car";
                double pret = 0.0;
                var vehicle = vehicleRepository.findById(b.getVehicleId());
                if (vehicle.isPresent()) {
                    carName = vehicle.get().getBrand() + " " + vehicle.get().getModel();
                    pret = vehicle.get().getPricePerDay();
                }
                tripsForFrontend.add(new UpcomingTrip(carName, b.getStartDate(), b.getStatus(), pret));
            }
            return ResponseEntity.ok(tripsForFrontend);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API 7: GET PAYMENT METHODS (real empty list — no more mock data)
    @GetMapping("/payment-methods")
    public ResponseEntity<?> getPaymentMethods(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body("Unauthorized");
        // No payment methods table implemented yet — return empty list
        return ResponseEntity.ok(List.of());
    }

    // API 8: GET TRANSACTIONS (real data from bookings table)
    @GetMapping("/transactions")
    public ResponseEntity<?> getTransactions(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body("Unauthorized");
        try {
            User user = userService.getUserByEmail(principal.getName());
            List<Booking> bookings = bookingRepository.findByUserId(user.getId());
            List<Transaction> transactions = new ArrayList<>();

            for (Booking b : bookings) {
                String carName = "Car Rental";
                double price = 0.0;
                var vehicle = vehicleRepository.findById(b.getVehicleId());
                if (vehicle.isPresent()) {
                    carName = vehicle.get().getBrand() + " " + vehicle.get().getModel() + " rental";
                    price = vehicle.get().getPricePerDay();
                }
                transactions.add(new Transaction(b.getStartDate(), carName, b.getStatus(), price));
            }
            return ResponseEntity.ok(transactions);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API 9: ADD NEW PAYMENT METHOD
    @PostMapping("/payment-methods")
    public ResponseEntity<?> addPaymentMethod(Principal principal, @RequestBody AddCardRequest request) {
        if (principal == null) return ResponseEntity.status(401).body("Unauthorized");
        try {
            String nrCard = request.getCardNumber().replaceAll("\\s+", "");
            String ultimele4 = nrCard.length() >= 4 ? nrCard.substring(nrCard.length() - 4) : "****";
            String tipCard = nrCard.startsWith("4") ? "Visa" : "Mastercard";
            PaymentMethod cardNou = new PaymentMethod(tipCard, ultimele4, request.getExpiryDate(), false);
            return ResponseEntity.ok(cardNou);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding card: " + e.getMessage());
        }
    }
}