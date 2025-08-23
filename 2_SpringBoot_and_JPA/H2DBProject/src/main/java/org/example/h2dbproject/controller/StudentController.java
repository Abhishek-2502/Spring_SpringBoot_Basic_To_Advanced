package org.example.h2dbproject.controller;

import org.example.h2dbproject.entity.Users;
import org.example.h2dbproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    UserService service;

    @GetMapping("/users")
    public List<Users> getUsers() {
        return service.getUsers();
    }


    @PostMapping("/user")
    public String addUser(@RequestBody Users user) {
        return service.addUser(user);
    }

    @GetMapping("/user/{email}")
    public Users getUserByEmail(@PathVariable String email) {
        return service.getUserByEmail(email);
    }

}
