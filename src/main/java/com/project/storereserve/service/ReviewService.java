
package com.project.storereserve.service;

import com.project.storereserve.domain.entity.*;
import com.project.storereserve.domain.reposiotry.ReservationRepository;
import com.project.storereserve.domain.reposiotry.ReviewRepository;
import com.project.storereserve.domain.reposiotry.StoreRepository;
import com.project.storereserve.domain.reposiotry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    // 리뷰 작성 서비스 로직
    public Review createReview(Integer reservationId, Integer userId, String reviewText, Integer rating, Store store) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약이 존재하지 않습니다."));

        if (!reservation.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("리뷰 작성은 예약자만 가능합니다.");
        }

        LocalDateTime now = LocalDateTime.now();

        Review review = new Review();
        review.setReservation(reservation);
        review.setUser(reservation.getUser());
        review.setReviewText(reviewText);
        review.setRating(rating);
        review.setCreatedAt(now);
        review.setUpdatedAt(now);
        review.setStore(store);
        return reviewRepository.save(review);
    }

    //리뷰 수정 서비스 로직
    public Review updateReview(Integer reviewId, String reviewText, Integer rating, Integer userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        // 작성자만 수정 가능
        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("리뷰 수정은 작성자만 가능합니다.");
        }

        review.setReviewText(reviewText);
        review.setRating(rating);
        review.setUpdatedAt(LocalDateTime.now());


        return reviewRepository.save(review);
    }

    public String deleteReview(Integer reviewId, Integer userId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        Integer storeId = review.getReservation().getStore().getId();

        if (!review.getUser().getId().equals(userId) && !isAdmin(userId, storeId)) {
            throw new IllegalArgumentException("리뷰 삭제는 작성자 또는 관리자만 가능합니다.");
        }

        reviewRepository.delete(review);
        return "리뷰가 삭제되었습니다.";
    }

    // 매장 점주인지 판별하는 로직
    private boolean isAdmin(Integer userId, Integer storeId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장이 존재하지 않습니다."));

        User owner = store.getOwner();

        return owner.getId().equals(userId) && owner.getRole() == RoleSelect.OWNER;

    }
}
