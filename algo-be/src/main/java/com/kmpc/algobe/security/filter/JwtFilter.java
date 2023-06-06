package com.kmpc.algobe.security.filter;

import com.kmpc.algobe.security.config.Config;
import com.kmpc.algobe.security.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            filterChain.doFilter(request, response);
            return;
        }

        Map<String, String> cookieMap = new HashMap<>();
        for (Cookie cookie: cookies)
            cookieMap.put(cookie.getName(), cookie.getValue());

        String accessToken = cookieMap.getOrDefault("accessToken", null);
        if(accessToken == null){
            String refreshToken = cookieMap.getOrDefault("refreshToken", null);
            if(refreshToken == null){
                log.error("refresh 토큰이 만료되었습니다.");
                filterChain.doFilter(request, response);
                return;
            }
            try {
                accessToken = jwtProvider.regenerateToken(refreshToken);
            } catch(Exception e) {
                throw new RuntimeException();
            }
            Cookie accessCookie = new Cookie("accessToken", accessToken);
            accessCookie.setPath("/");
            accessCookie.setHttpOnly(true);
            accessCookie.setMaxAge(JwtProvider.accessTokenCookieTime);
            accessCookie.setDomain(Config.DOMAIN);
            response.addCookie(accessCookie);
        }
        else if(jwtProvider.validateToken(accessToken)){
            log.error("잘못된 accessToken 입니다.");
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = jwtProvider.getAuthentication(accessToken);
        //authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
