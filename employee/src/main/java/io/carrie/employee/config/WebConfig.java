package io.carrie.employee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addCorsMappings(@NonNull CorsRegistry registry) {
        String[] allowedOrigins = { "http://localhost:5173/", "http://120.0.0.1:5173/" };
        registry.addMapping("/**").allowedOrigins(allowedOrigins).allowedMethods("*").allowedHeaders("*");
    }

}
