package com.kmpc.algobe.user.domain.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoDto {
    private String nickname;
    private String role;

    public UserInfoDto(String nickname, String role) {
        this.nickname = nickname;
        this.role = role;
    }
}
