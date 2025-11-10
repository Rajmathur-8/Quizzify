package com.raj.quiz_app_backend.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // Configure OpenAPI documentation
    @Bean
    public OpenAPI quizAppOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Quiz App API")
                        .description("Backend API for Quiz Application built using Spring Boot & React")
                        .version("1.0.0"));
    }
}