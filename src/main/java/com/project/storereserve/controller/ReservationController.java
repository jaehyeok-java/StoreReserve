package com.project.storereserve.controller;

import com.project.storereserve.domain.dto.ReservationRequest;
import com.project.storereserve.domain.entity.Reservation;
import com.project.storereserve.domain.entity.User;
import com.project.storereserve.domain.reposiotry.UserRepository;
import com.project.storereserve.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final UserRepository userRepository;
    private final ReservationService reservationService;

    public ReservationController(UserRepository userRepository, ReservationService reservationService) {
        this.userRepository = userRepository;
        this.reservationService = reservationService;
    }

    @PostMapping("/reserve/{storeId}")
    public ResponseEntity<Reservation> reserveStore(
            @RequestBody ReservationRequest request,
            @PathVariable Integer storeId
            ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. 로그인에 실패하였습니다."));

        return ResponseEntity.ok(reservationService.reserveStore(storeId, user.getId(), request.getReservationTime()));

    }

    @PostMapping("/confirm/{reservationId}")
    public ResponseEntity<String> confirmVisit(
            @PathVariable Integer reservationId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. 로그인에 실패하였습니다."));

       return ResponseEntity.ok(reservationService.confirmVisit(user.getId(), reservationId));
    }
}
