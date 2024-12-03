package com.bankingApp.BankingApplication.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://*.example.com", "http://localhost:*")  // Use patterns for matching origins
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");

    }
}
