package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")  // Specifying the table name. If not specified, the default is the class name in lowercase.
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing primary key
    private long studentId;

    @Column(name = "student_name", nullable = false, length = 100, unique = true) // Column name and constraints. If not specified, the default is the field name in lowercase.
    private String name;

    @Column(name = "student_college", length = 200)
    private String college;
    private String phone;
    private String fatherName;

    private boolean active = true; // Default value set to true

    @Lob
    private String about;
}
