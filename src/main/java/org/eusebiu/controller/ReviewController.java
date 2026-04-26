package org.eusebiu.controller;

import org.eusebiu.models.Booking;
import org.eusebiu.models.Review;
import org.eusebiu.models.User;
import org.eusebiu.repository.BookingRepository;
import org.eusebiu.repository.ReviewRepository;
import org.eusebiu.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public ReviewController(ReviewRepository reviewRepository, BookingRepository bookingRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<?> getReviews(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(reviewRepository.findByVehicleIdOrderByCreatedAtDesc(vehicleId));
    }

    @PostMapping
    public ResponseEntity<?> addReview(Principal principal, @RequestBody Map<String, Object> body) {
        if (principal == null) return ResponseEntity.status(401).body("Unauthorized");
        
        User user = userRepository.findByEmail(principal.getName());
        if (user == null) return ResponseEntity.status(401).body("User not found");

        Long vehicleId = Long.valueOf(body.get("vehicleId").toString());
        int rating = Integer.parseInt(body.get("rating").toString());
        String comment = body.getOrDefault("comment", "").toString();

        // Check if user has booked this vehicle
        List<Booking> userBookings = bookingRepository.findByUserId(user.getId());
        boolean hasBooked = userBookings.stream().anyMatch(b -> b.getVehicleId().equals(vehicleId));

        if (!hasBooked) {
            return ResponseEntity.status(403).body("You can only review cars you have booked!");
        }
        
        if (reviewRepository.existsByVehicleIdAndUserId(vehicleId, user.getId())) {
            return ResponseEntity.status(400).body("You have already reviewed this car.");
        }

        Review r = new Review();
        r.setVehicleId(vehicleId);
        r.setUserId(user.getId());
        r.setReviewerName(user.getUsername());
        r.setRating(rating);
        r.setComment(comment);

        reviewRepository.save(r);
        return ResponseEntity.ok(r);
    }
}
