package com.sit.abhishek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyFirstController {

    @RequestMapping("/Controller")
    public String hello() {
        return "hello";             //Search for hello.html or hello.jsp view (Require Thymeleaf library)
    }

    @RequestMapping("/ControllerString")
    @ResponseBody
    public String helloString() {
        return "hello String";      //Return text directly
    }
}
