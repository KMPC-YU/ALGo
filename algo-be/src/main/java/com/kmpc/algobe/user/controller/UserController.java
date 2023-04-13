package com.kmpc.algobe.user.controller;

import com.kmpc.algobe.annotation.CurrentUser;
import com.kmpc.algobe.user.domain.dto.LoginRequestDto;
import com.kmpc.algobe.user.domain.dto.SignUpRequestDto;
import com.kmpc.algobe.user.domain.entity.User;
import com.kmpc.algobe.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto){
        Boolean signUpResult = userService.signUp(signUpRequestDto);
        if(signUpResult)
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공했습니다.");
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러가 발생했습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        String token = userService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
