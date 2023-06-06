package com.kmpc.algobe.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    public static String WEB_PORT;
    public static String DOMAIN;
    public static String WEB_BASE_URL;
    public static String BACK_BASE_URL;

    @Value("${frontend.port}")
    public void setWebPort(String port){
        WEB_PORT = port;
    }
    @Value("${frontend.domain}")
    public void setDomain(String domain){
        DOMAIN = domain;
    }
    @Value("${frontend.base.url}")
    public void setWebBaseUrl(String url){
        WEB_BASE_URL = url;
    }
    @Value("${backend.base.url}")
    public void setBackBaseUrl(String back_base_url){
        BACK_BASE_URL = back_base_url;
    }
}
