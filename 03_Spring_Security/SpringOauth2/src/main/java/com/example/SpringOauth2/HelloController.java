package com.example.SpringOauth2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("/")
    public String greet() {
        return "Greetings from Abhishek, abhishekrajput.me";
    }
}