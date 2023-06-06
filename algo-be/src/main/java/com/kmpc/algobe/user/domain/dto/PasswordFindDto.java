package com.kmpc.algobe.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordFindDto {
    @Email
    private String email;

    @JsonProperty("new_password")
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,}$", message = "비밀번호는 영어, 숫자, 특수문자를 포함하여 8자리 이상으로 입력해주세요.")
    private String newPassword;

    @JsonProperty("new_password_confirm")
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,}$", message = "비밀번호는 영어, 숫자, 특수문자를 포함하여 8자리 이상으로 입력해주세요.")
    private String newPasswordConfirm;
}
