package com.kmpc.algobe.user.controller;

import com.kmpc.algobe.allergy.AllergyType;
import com.kmpc.algobe.annotation.CurrentUser;
import com.kmpc.algobe.security.dto.JwtCookie;
import com.kmpc.algobe.security.dto.LoginResponseDto;
import com.kmpc.algobe.security.provider.JwtProvider;
import com.kmpc.algobe.user.domain.dto.*;
import com.kmpc.algobe.user.domain.entity.User;
import com.kmpc.algobe.user.service.EmailService;
import com.kmpc.algobe.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto){
        Boolean signUpResult = userService.signUp(signUpRequestDto);
        if(signUpResult)
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공했습니다.");
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러가 발생했습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<UserInfoDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        LoginResponseDto token = userService.login(loginRequestDto);
        JwtCookie jwtCookie = jwtProvider.setJwtCookie(token);
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE,
                jwtCookie.getAccessCookie().toString(),
                jwtCookie.getRefreshCookie().toString())
                .body(token.getUserInfo());
    }

    @PostMapping("/emails")
    public ResponseEntity<String> sendEmail(@RequestBody @Valid EmailAddressDto emailAddressDto){
        emailService.sendEmail(emailAddressDto.getEmail(), emailAddressDto.getUsername());
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

    @PostMapping("/find-password")
    public ResponseEntity<String> findPassword(@RequestBody @Valid PasswordFindDto passwordFindDto){
        String result = userService.findPassword(passwordFindDto);

        if(Objects.equals(result, passwordFindDto.getEmail()))
            return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경을 완료하였습니다.");
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 변경에 실패하였습니다.");
    }

    @GetMapping("/verify-nickname")
    public ResponseEntity<Boolean> verifyNickname(@RequestParam("nickname") @Pattern(regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z\\d]{2,15}$", message = "닉네임은 한글, 영어, 숫자를 사용할 수 있으며 2자 이상 15자 이내로 입력해주세요.") String nickname){
        boolean isExist = userService.existsByNickname(nickname);

        if (isExist)
            return ResponseEntity.status(HttpStatus.OK).body(true);
        else
            return ResponseEntity.status(HttpStatus.OK).body(false);
    }

    @GetMapping("/verify-username")
    public ResponseEntity<Boolean> verifyUsername(@RequestParam("username") String username){
        boolean isExist = userService.existsByUsername(username);

        if (isExist)
            return ResponseEntity.status(HttpStatus.OK).body(true);
        else
            return ResponseEntity.status(HttpStatus.OK).body(false);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Boolean> verifyEmail(@Email @RequestParam("email") String email){
        boolean isExist = userService.existsByEmail(email);
        log.info(email);

        if (isExist)
            return ResponseEntity.status(HttpStatus.OK).body(true);
        else
            return ResponseEntity.status(HttpStatus.OK).body(false);
    }

    @PatchMapping("/users/{user_id}/allergies")
    public ResponseEntity<String> changeUserAllergy(@RequestBody List<AllergyType> allergyTypeList, @PathVariable("user_id") Long userId, @CurrentUser User user){
        if(!Objects.equals(userId, user.getUserId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("본인 정보만 변경할 수 있습니다.");

        Long result = userService.changeUserAllergy(allergyTypeList, user);
        if(Objects.equals(result, userId))
            return ResponseEntity.status(HttpStatus.OK).body("사용자 알레르기 정보 변경을 완료하였습니다.");
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 알레르기 정보 변경에 실패하였습니다.");
    }

    //TODO 이미지 url 보안 생각해볼것, S3 Presigned URL 사용 시 수정 필요
    @PatchMapping("/users/{user_id}/profile-image")
    public ResponseEntity<String> changeProfileImage(@RequestBody String imageUrl, @PathVariable("user_id") Long userId, @CurrentUser User user){
        if(!Objects.equals(userId, user.getUserId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("본인 정보만 변경할 수 있습니다.");  //TODO 예외처리 추후 구현

        Long result = userService.changeProfileImage(imageUrl, user);
        if(Objects.equals(result, userId))
            return ResponseEntity.status(HttpStatus.OK).body("프로필 사진 변경을 완료하였습니다.");
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("프로필 사진 변경에 실패하였습니다.");
    }

    @PatchMapping("/users/{user_id}/nicknames")
    public ResponseEntity<String> changeNickname(@RequestBody @Pattern(regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z\\d]{2,15}$", message = "닉네임은 한글, 영어, 숫자를 사용할 수 있으며 2자 이상 15자 이내로 입력해주세요.") String nickname,
                                                 @PathVariable("user_id") Long userId, @CurrentUser User user){
        if(!Objects.equals(userId, user.getUserId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("본인 정보만 변경할 수 있습니다.");  //TODO 예외처리 추후 구현

        if(userService.existsByNickname(nickname))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 닉네임입니다.");
        Long result = userService.changeNickname(nickname, user);

        if(Objects.equals(result, userId))
            return ResponseEntity.status(HttpStatus.OK).body("닉네임 변경을 완료하였습니다.");
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("닉네임 변경에 실패하였습니다.");
    }

    @PatchMapping("/users/{user_id}/passwords")
    public ResponseEntity<String> changePassword(@RequestBody @Valid PasswordChangeDto passwordChangeDto, @PathVariable("user_id") Long userId, @CurrentUser User user){
        if(!Objects.equals(userId, user.getUserId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("본인 정보만 변경할 수 있습니다.");

        Long result = userService.changePassword(passwordChangeDto, user);

        if(Objects.equals(result, userId))
            return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경을 완료하였습니다.");
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 변경에 실패하였습니다.");
    }
}
