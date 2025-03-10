package com.project.storereserve.service;

import com.project.storereserve.domain.entity.Reservation;
import com.project.storereserve.domain.entity.Status;
import com.project.storereserve.domain.entity.Store;
import com.project.storereserve.domain.entity.User;
import com.project.storereserve.domain.reposiotry.ReservationRepository;
import com.project.storereserve.domain.reposiotry.StoreRepository;
import com.project.storereserve.domain.reposiotry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    //매장 예약 서비스 구현
    public Reservation reserveStore(Integer storeId, Integer userId, LocalDateTime reservationTime) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장을 찾을수 없습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 매장에 이미 같은시간대에 예약이 존재하면 예약 불가  - 예약 가능 여부 확인
        boolean isAlreadyReserved = reservationRepository.existsByStoreIdAndReservationTimeAndStatusNot(store, reservationTime, Status.CANCELLED);
        if (isAlreadyReserved) {
            throw new IllegalArgumentException("해당 시간에 예약이 이미 존재합니다.");
        }

        // 방문 확인 예약 10분전까진 이뤄져야하므로 (현재시간 + 10) 이전엔 예약 불가   -  예약 가능 여부 확인
        if (reservationTime.isBefore(LocalDateTime.now().plusMinutes(10))) {
            throw new IllegalArgumentException("예약은 최소 현재 시간으로부터 10분 후 부터 가능합니다.");
        }

        Reservation reservation = new Reservation();
        reservation.setStoreId(store);
        reservation.setUserId(user);
        reservation.setReservationTime(reservationTime);
        reservation.setStatus(Status.PRE_CONFIRMED);
        return reservationRepository.save(reservation);
    }

    // 키오스크 방문 확인 서비스 구현
    @Transactional
    public String confirmVisit(Integer userId, Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약 내역이 존재하지 않습니다."));


        /*
         자동 예약 취소 로직(ReservationScheduler.class) 으로 10분전까지 방문확인 수행하지 않았을 경우 Status 가 CANCELLED 가 되므로,
         CANCELLED 상태라면 방문가능 시간을 초과했거나 예약시간을 초과한경우 -> 방문확인 실패
         */
        if(reservation.getStatus().equals(Status.CANCELLED)){
            throw new IllegalArgumentException("방문확인 가능시간을 초과하여 예약이 취소되었습니다.");
        }

        // 예약자와 방문확인 고객의 ID 비교 검증 로직
        if (!reservation.getUserId().getId().equals(userId)) {
            throw new IllegalArgumentException("예약고객과 방문확인 고객이 일치하지 않습니다. 방문확인은 예약 당사자만 가능합니다.");
        }

        reservation.setStatus(Status.CONFIRMED);
        reservationRepository.save(reservation);
        return "방문확인이 완료되었습니다.";
    }

}

