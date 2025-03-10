package com.project.storereserve.controller;

import com.project.storereserve.domain.dto.ReviewRequestDto;
import com.project.storereserve.domain.entity.Review;
import com.project.storereserve.domain.entity.User;
import com.project.storereserve.domain.reposiotry.UserRepository;
import com.project.storereserve.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reviews")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;

    @PostMapping("/create/{reservationId}")
    public ResponseEntity<Review> createReview(
            @PathVariable Integer reservationId,
            @RequestBody ReviewRequestDto reviewRequestDto

    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. 로그인에 실패하였습니다."));

        return ResponseEntity.ok(reviewService.createReview(reservationId, user.getId(), reviewRequestDto.getReviewText(), reviewRequestDto.getRating()));
    }

    @PutMapping("/update/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Integer reviewId,
            @RequestParam String reviewText,
            @RequestParam Integer rating
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. 로그인에 실패하였습니다."));

        return ResponseEntity.ok(reviewService.updateReview(reviewId,reviewText, rating, user.getId()));
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Integer reviewId
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. 로그인에 실패하였습니다."));

        return ResponseEntity.ok(reviewService.deleteReview(reviewId, user.getId()));
    }
}
