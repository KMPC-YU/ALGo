package com.kmpc.algobe.point.controller;

import com.kmpc.algobe.annotation.CurrentUser;
import com.kmpc.algobe.point.domain.dto.PointCreateRequestDto;
import com.kmpc.algobe.point.service.PointService;
import com.kmpc.algobe.user.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/points")
public class PointController {
    private final PointService pointService;

    @PostMapping
    ResponseEntity<Long> createPoint(@RequestBody @Valid PointCreateRequestDto requestDto, @CurrentUser User user){
        Long createPointId = pointService.createPoint(requestDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createPointId);
    }
}
