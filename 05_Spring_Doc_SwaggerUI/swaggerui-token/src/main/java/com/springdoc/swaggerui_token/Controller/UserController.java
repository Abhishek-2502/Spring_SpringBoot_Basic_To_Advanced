package com.springdoc.swaggerui_token.Controller;

import com.springdoc.swaggerui_token.Model.Student;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Operations related to users")
public class UserController {

    private List<Student> students = new ArrayList<>(
            List.of(
                    new Student(1, "Navin", 60),
                    new Student(2, "Kiran", 65)
            ));

    @GetMapping
    @Operation(summary = "Get all users", description = "Get all users in the system")
    public List<Student> getUser() {
        return students;
    }

    @PostMapping
    @Operation(summary = "Create new user", description = "Create a new user with provided details")
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }

    @GetMapping("/with-token")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get all users with Token", description = "Get all users in the system with token")
    public List<Student> getUserWithToken() {
        return students;
    }
}
