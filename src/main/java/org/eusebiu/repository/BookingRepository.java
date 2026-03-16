package org.eusebiu.repository;

import org.eusebiu.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // 1. Numara cate rezervari are un user in total
    int countByUserId(Long userId);

    // 2. Numara cate rezervari are un user cu un anumit status (ex: "Confirmed" sau "Pending")
    int countByUserIdAndStatus(Long userId, String status);

    // 3. Gaseste lista de rezervari viitoare pentru tabelul de dashboard
    List<Booking> findByUserId(Long userId);
}