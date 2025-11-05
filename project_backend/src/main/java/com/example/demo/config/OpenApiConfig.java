package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI configuration with application metadata and tags.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI projectOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("UPSTDC Infrastructure Monitoring API")
                    .description("Backend API for Uttar Pradesh Tourism infrastructure monitoring system with JWT auth and RBAC.")
                    .version("1.0.0")
                    .contact(new Contact().name("UPSTDC").email("support@example.com")))
                .tags(List.of(
                        new Tag().name("Authentication").description("JWT authentication and token refresh"),
                        new Tag().name("Example").description("Protected example endpoints for RBAC demo")
                ));
    }
}
