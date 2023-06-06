package com.kmpc.algobe.point.domain.entity;

import com.kmpc.algobe.BaseTimeEntity;
import com.kmpc.algobe.user.domain.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long pointId;

    @ManyToOne
    @JoinColumn(name = "receiver")
    @NotNull
    private User user;

    @Column(name = "point_amount")
    private Integer pointAmount;

    @Column(name = "point_category")
    private PointCategory pointCategory;
}
