package com.example.security_with_jpa_bycrpt_jwt.controller;

import com.example.security_with_jpa_bycrpt_jwt.model.Student;
import com.example.security_with_jpa_bycrpt_jwt.model.Users;
import com.example.security_with_jpa_bycrpt_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return service.verify(user);
    }


    // For Testing purposes, we can use a static list of students
    private List<Student> students = new ArrayList<>(
            List.of(
                    new Student(1, "Navin", 60),
                    new Student(2, "Kiran", 65)
            ));

    @GetMapping("/test")
    public List<Student> getStudents() {
        return students;
    }

    @PostMapping("/test")
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }
}
