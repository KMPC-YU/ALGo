package com.kmpc.algobe.user.controller;

import com.kmpc.algobe.user.domain.dto.SignUpRequestDto;
import com.kmpc.algobe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        Boolean signUpResult = userService.signUp(signUpRequestDto);
        if(signUpResult)
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공했습니다.");
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러가 발생했습니다.");
    }
}
