package com.eshop.progavanzada.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:5173")
        .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
  }
}
