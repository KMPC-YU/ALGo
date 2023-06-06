package com.kmpc.algobe.point.service;

import com.kmpc.algobe.point.domain.dto.PointCreateRequestDto;
import com.kmpc.algobe.point.repository.PointRepository;
import com.kmpc.algobe.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    public Long createPoint(PointCreateRequestDto requestDto, User user) {
        return null;
    }
}
