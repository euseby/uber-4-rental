package org.eusebiu.repository;

import org.eusebiu.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    //Istoric de rezervari ale unui user
    List<Booking> findByUserId(Long userId);
    //Istoric de rezervari ale unei masini
    List<Booking> findByVehicleId(Long vehicleId);
}
