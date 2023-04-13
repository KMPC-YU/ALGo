package com.kmpc.algobe.user.domain.dto;

import com.kmpc.algobe.user.domain.entity.Grade;
import com.kmpc.algobe.user.domain.entity.LoginType;
import com.kmpc.algobe.user.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,}$", message = "비밀번호는 영어, 숫자, 특수문자를 포함하여 8자리 이상으로 입력해주세요.")
    private String password;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,}$", message = "비밀번호는 영어, 숫자, 특수문자를 포함하여 8자리 이상으로 입력해주세요.")
    private String passwordConfirm;

    @NotBlank
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "인증번호를 입력해주세요.")
    private String code;

    @NotBlank(message="닉네임을 입력해주세요")
    @Pattern(regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z\\d]{2,15}$", message = "닉네임은 한글, 영어, 숫자를 사용할 수 있으며 2자 이상 15자 이내로 입력해주세요.")
    private String nickname;

    public User toUserEntity(BCryptPasswordEncoder encoder){ // TODO Mapper 사용하기
        return User.builder().username(username).password(encoder.encode(password)).email(email).nickname(nickname).loginType(LoginType.LOCAL).grade(Grade.USER).build();
    }
}
