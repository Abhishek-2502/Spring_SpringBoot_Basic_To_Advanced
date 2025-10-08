package com.example.actuatordemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoNeed {

    @GetMapping("/")
    public String noNeed() {
        return "Go to /actuator/health , /actuator/prometheus , /actuator/loggers , /actuator/metrics , /actuator/env , etc";
    }
}

