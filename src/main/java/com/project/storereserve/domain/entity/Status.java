package com.project.storereserve.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    PENDING("예약 대기중"),
    CONFIRMED("예약 완료"),
    CANCELLED("예약 취소");

    private final String description;

}
