package com.kmpc.algobe.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    private static Long accessTokenExpireTime = 60 * 10 * 1000L;
    ;

    private Long refreshTokenExpireTime;

    private final UserDetailsService userDetailsService;

    public String generateToken(String username, String nickname, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("nickname", nickname);
        claims.put("role", role);
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
}