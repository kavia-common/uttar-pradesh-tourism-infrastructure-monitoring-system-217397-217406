package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main Spring Boot application.
 * Scheduling is enabled for background processors like NotificationQueueService.
 */
@SpringBootApplication
@EnableScheduling
@RestController
public class DemoApplication {

    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class,args);
    }

    /**
     * Simple root endpoint to confirm service is up.
     */
    // PUBLIC_INTERFACE
    @GetMapping("/")
    public String index() {
        return "UPSTDC Backend is running. See /swagger-ui/index.html for API docs.";
    }
}
