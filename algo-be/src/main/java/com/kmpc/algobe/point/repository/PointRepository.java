package com.kmpc.algobe.point.repository;

import com.kmpc.algobe.point.domain.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
