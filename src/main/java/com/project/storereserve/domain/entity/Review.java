package com.project.storereserve.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Reservation reservationId;
    @ManyToOne
    private User userId;

    private String reviewText;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
