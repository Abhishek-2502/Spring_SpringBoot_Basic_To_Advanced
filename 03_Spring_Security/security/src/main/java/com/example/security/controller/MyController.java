package com.example.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/")
    public String hello(HttpServletRequest request) {
        return "Hello World with Session ID: "+ request.getSession().getId();
    }
}
