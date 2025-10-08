package com.example.security.controller;

import com.example.security.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private List<Student> students = new ArrayList<>(List.of(
        new Student("John", "Doe", 20),
        new Student("Jane", "Smith", 22),
        new Student("Alice", "Johnson", 19)
    ));

    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    // POST, PUT, DELETE methods are not allowed without CSRF token when Spring Security is enabled. It will give 401 Unauthorized error.
    // To allow these methods, you need to:
    // 1. Disable CSRF protection or
    // 2. Make a controller method that provides the CSRF token or
    // 3. Use a frontend framework that automatically handles CSRF tokens (like Angular, React, etc.) or
    // 4. Use Samesite Strict.
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
