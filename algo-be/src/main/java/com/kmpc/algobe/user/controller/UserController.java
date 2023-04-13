package com.kmpc.algobe.user.controller;

import com.kmpc.algobe.annotation.CurrentUser;
import com.kmpc.algobe.user.domain.dto.*;
import com.kmpc.algobe.user.service.EmailService;
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
    private final EmailService emailService;

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

    @PostMapping("/emails")
    public ResponseEntity<String> sendEmail(@RequestBody @Valid EmailAddressDto emailAddressDto){
        emailService.sendEmail(emailAddressDto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body("이메일을 발송했습니다.");
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateCode(@RequestBody EmailCodeDto emailCodeDto){
        ResultVerifyCode verifyCode = emailService.verifyEmailCode(emailCodeDto);
        if(verifyCode == ResultVerifyCode.ATTEMPT_EXCEED)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 횟수를 초과하였습니다");
        if (verifyCode == ResultVerifyCode.INVALID)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다.");
        if (verifyCode == ResultVerifyCode.TIME_OUT)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 유효시간이 초과하였습니다.");
        else return ResponseEntity.status(HttpStatus.OK).body("이메일 인증에 성공하였습니다.");
    }
}
