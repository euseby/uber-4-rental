package org.eusebiu.repository;

import org.eusebiu.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByVehicleIdOrderByCreatedAtDesc(Long vehicleId);
    boolean existsByVehicleIdAndUserId(Long vehicleId, Long userId);
}
