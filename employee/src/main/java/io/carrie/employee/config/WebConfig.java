package io.carrie.employee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addCorsMappings(@NonNull CorsRegistry registry) {

        // Define allowed origins for CORS
        // Note: Adjust these origins based on your deployment and development needs
        String[] allowedOrigins = {
                "http://localhost:5173",
                "http://120.0.0.1:5173",
                "http://localhost:4173",
                "http://120.0.0.1:4173",
                "https://d3bcyx0s1yb5do.cloudfront.net",
                "https://d3bcyx0s1yb5do.cloudfront.net/employees",
                "https://d3bcyx0s1yb5do.cloudfront.net/contracts",
                "https://d3bcyx0s1yb5do.cloudfront.net/departments",
                "https://employeecreator.my" };

        // Configure CORS mapping
        // This allows all methods and headers from the specified origins
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
