package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
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
