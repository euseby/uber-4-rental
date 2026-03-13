package org.eusebiu.repository;

import org.eusebiu.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {

    //Toate recenziile pt o anumita masina
    List<Rating> findByVehicleId(Long vehicleId);
}
