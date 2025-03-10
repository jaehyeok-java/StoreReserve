package com.project.storereserve.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "store", uniqueConstraints =  @UniqueConstraint(columnNames = {"name", "location"})) // name 과 location 이 조합해서 유니크하도록 제약
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //erd 에서는 존재하는 외래키 필드이지만 jpa 에서는 외래키를 다루는 방식이 따로 존재하기에 필요없어진 필드
    // private int ownerId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String description;

    // ManyToOne 에서 many 는 현재 Store 클래스, JoinColumn 은 외래키 지정 방식
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;



}
