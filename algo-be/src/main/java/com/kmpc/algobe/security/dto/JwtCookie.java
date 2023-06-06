package com.kmpc.algobe.security.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseCookie;

@Getter
@Builder
public class JwtCookie {
    private ResponseCookie accessCookie;
    private ResponseCookie refreshCookie;
}
