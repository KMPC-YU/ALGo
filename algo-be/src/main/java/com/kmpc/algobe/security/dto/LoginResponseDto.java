package com.kmpc.algobe.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String role;

    @Builder
    public LoginResponseDto(String accessToken, String refreshToken, String role){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
    }
}
