package com.project.storereserve.scheduler;

import com.project.storereserve.domain.entity.Reservation;
import com.project.storereserve.domain.entity.Status;
import com.project.storereserve.domain.reposiotry.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationScheduler {
    private final ReservationRepository reservationRepository;

    @Scheduled(cron = "0 * * * * *")
    public void cancelReservations() {

        LocalDateTime now = LocalDateTime.now();

        List<Reservation> reservations
                = reservationRepository.findByStatusAndReservationTimeBefore(Status.PRE_CONFIRMED, now.plusMinutes(10));

        for (Reservation reservation : reservations) {
            reservation.setStatus(Status.CANCELLED);
        }

        reservationRepository.saveAll(reservations);
    }

}
