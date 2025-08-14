package com.sit.abhishek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyFirstController {

    @RequestMapping("/")
    public String hello() {
        return "hello";             //Search for hello.html or hello.jsp view (Require a Thymeleaf library)
    }

    @RequestMapping("/String")
    @ResponseBody
    public String helloString() {
        return "hello from Spring by RequestMapping";      //Return text directly
    }

}
