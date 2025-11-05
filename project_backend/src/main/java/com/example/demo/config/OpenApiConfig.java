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
                        new Tag().name("Example").description("Protected example endpoints for RBAC demo"),
                        new Tag().name("Projects").description("Project management"),
                        new Tag().name("Contractors").description("Contractor/vendor management"),
                        new Tag().name("Tenders").description("Tender management"),
                        new Tag().name("Contracts").description("Contract award and management"),
                        new Tag().name("Milestones").description("Project milestones and progress"),
                        new Tag().name("Inspections").description("Inspections and site visits"),
                        new Tag().name("Handovers").description("Project handover"),
                        new Tag().name("Funds").description("Fund allocations and releases"),
                        new Tag().name("Payments").description("Payments to contractors"),
                        new Tag().name("Notifications").description("User notifications")
                ));
    }
}
