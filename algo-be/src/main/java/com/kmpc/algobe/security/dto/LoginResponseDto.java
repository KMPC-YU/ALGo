package com.kmpc.algobe.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kmpc.algobe.user.domain.dto.UserInfoDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("user_info")
    private UserInfoDto userInfo;

    @Builder
    public LoginResponseDto(String accessToken, String refreshToken, UserInfoDto userInfoDto){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userInfo = userInfoDto;
    }
}
