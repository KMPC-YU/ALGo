package com.kmpc.algobe.security.provider;

import com.kmpc.algobe.redis.util.RedisUtil;
import com.kmpc.algobe.security.config.Config;
import com.kmpc.algobe.security.dto.JwtCookie;
import com.kmpc.algobe.security.dto.LoginResponseDto;
import com.kmpc.algobe.user.domain.dto.UserInfoDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    @Value("$(jwt.secretKey}")
    private String secretKey;

    private static Long accessTokenExpireTime = 60 * 5 * 1000L;
    private static Long refreshTokenExpireTime = 60 * 60 * 2 * 1000L;
    public static int accessTokenCookieTime = 60 * 5;
    public static int refreshTokenCookieTime = 60 * 60 * 2;
    private final UserDetailsService userDetailsService;

    public LoginResponseDto generateToken(String username, String nickname, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("nickname", nickname);
        claims.put("role", role);
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();

        return LoginResponseDto.builder().accessToken(accessToken).refreshToken(refreshToken).userInfoDto(new UserInfoDto(nickname, role)).build();
    }

    public String regenerateToken(String refreshToken){
        if(validateToken(refreshToken))
            throw new RuntimeException();

        Claims claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(refreshToken).getBody();
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
    }



    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String username = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().getExpiration().before(new Date());
        } catch (SecurityException e) {
            throw new RuntimeException(); // TODO CustomJwtRuntimeException으로 수정
        }
    }

    public JwtCookie setJwtCookie(LoginResponseDto dto){
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", dto.getAccessToken())
                .httpOnly(true)
                .path("/")
                .maxAge(accessTokenCookieTime)
                .domain(Config.DOMAIN)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", dto.getRefreshToken())
                .httpOnly(true)
                .path("/")
                .maxAge(refreshTokenCookieTime)
                .domain(Config.DOMAIN)
                .build();

        return JwtCookie.builder()
                .accessCookie(accessCookie)
                .refreshCookie(refreshCookie)
                .build();
    }
}