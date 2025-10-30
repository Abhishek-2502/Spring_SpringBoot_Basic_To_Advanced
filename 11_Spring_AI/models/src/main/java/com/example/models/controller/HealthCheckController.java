package com.example.models.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    // Simple health check endpoint
    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
