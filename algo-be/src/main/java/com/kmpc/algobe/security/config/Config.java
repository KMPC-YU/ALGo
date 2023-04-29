package com.kmpc.algobe.security.config;

import org.springframework.beans.factory.annotation.Value;

public class Config {
    @Value("${frontend.port}")
    public static String WEB_PORT;

    @Value("${frontend.domain}")
    public static String DOMAIN;

    @Value("${frontend.base.url}")
    public static String WEB_BASE_URL;
}
