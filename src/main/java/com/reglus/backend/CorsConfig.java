package com.reglus.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://teste-api-production-4923.up.railway.app/")
                .allowedMethods("GET", "POST", "PUT", "DELETE")  
                .allowedHeaders("*");
    }
}
