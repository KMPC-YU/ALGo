package com.kmpc.algobe.security.config;

import com.kmpc.algobe.security.filter.JwtFilter;
import com.kmpc.algobe.security.provider.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        AuthorityAuthorizationManager<RequestAuthorizationContext> userAuth
                = AuthorityAuthorizationManager.hasRole("USER");

        userAuth.setRoleHierarchy(roleHierarchy());

        httpSecurity
                .csrf().disable()
                .cors().and()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/signUp", "/api/v1/login", "/api/v1/emails", "/api/v1/validate", "/api/v1/verify-email", "/api/v1/verify-username", "/api/v1/verify-nickname", "/api/v1/find-password").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/boards", "/api/v1/boards/*/posts", "/api/v1/boards/*/types").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);


        httpSecurity.logout(logout -> logout.logoutUrl("/api/v1/logout")
                .logoutSuccessUrl(Config.DOMAIN)
                .addLogoutHandler(((request, response, authentication) -> {
                    Cookie accessToken = new Cookie("accessToken", null);
                    accessToken.setMaxAge(0);
                    accessToken.setHttpOnly(true);
                    accessToken.setPath("/");
                    accessToken.setDomain(Config.DOMAIN);
                    Cookie refreshToken = new Cookie("refreshToken", null);
                    refreshToken.setMaxAge(0);
                    refreshToken.setHttpOnly(true);
                    refreshToken.setPath("/");
                    refreshToken.setDomain(Config.DOMAIN);
                    response.addCookie(accessToken);
                    response.addCookie(refreshToken);
                    SecurityContextHolder.clearContext();
                }))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("accessToken", "refreshToken")
                .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK)));

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui", "/swagger-ui/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(Config.WEB_BASE_URL, "http://172.30.1.98:3000", "http://116.124.137.222:5173", "http://diablo2.kro.kr:5173/"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("X-Page-Count", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
}
