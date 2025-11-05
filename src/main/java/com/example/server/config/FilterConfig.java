package com.example.server.config;

import com.example.server.security.auth.JwtAuthFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    Logger logger = LoggerFactory.getLogger(FilterConfig.class);
    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilterRegistration(JwtAuthFilter filter) {
        logger.warn("进入jwtAuthFilterRegistration");
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");  // 拦截所有请求
        registration.setOrder(1);  // 设置过滤器顺序
        return registration;
    }
}