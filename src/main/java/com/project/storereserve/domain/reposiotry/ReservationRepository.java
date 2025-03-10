package com.project.storereserve.domain.reposiotry;

import com.project.storereserve.domain.entity.Reservation;
import com.project.storereserve.domain.entity.Status;
import com.project.storereserve.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    boolean existsByStoreIdAndReservationTimeAndStatusNot(Store storeId, LocalDateTime reservationTime, Status status);

    List<Reservation> findByStatusAndReservationTimeBefore(Status status, LocalDateTime time);

    Optional<Reservation> findById(Integer id);
}
