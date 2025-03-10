package com.project.storereserve.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store storeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    private LocalDateTime reservationTime;

    private Status status;
}
