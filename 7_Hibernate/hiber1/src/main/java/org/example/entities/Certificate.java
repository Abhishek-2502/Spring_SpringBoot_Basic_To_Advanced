package org.example.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_certificate")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long certificateId;

    private String title;

    private String about;

    private String link;

    @ManyToOne // Many certificates can belong to one student
    @JoinColumn(name = "student_id", nullable = false) // Foreign key column in the database
    // The name "student_id" will be used as the foreign key column in the student_certificate table
    // This column will reference the primary key of the Student entity
    // The nullable = false constraint ensures that a certificate must be associated with a student
    @ToString.Exclude   // 👈 prevents recursion
    private Student student; // Many-to-one relationship with Student entity
}
