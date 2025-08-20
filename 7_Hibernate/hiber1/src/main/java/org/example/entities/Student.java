package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")  // Specifying the table name. If not specified, the default is the class name in lowercase.
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing primary key
    private long studentId;

    @Column(name = "student_name", nullable = false, length = 100, unique = true)
    // Column name and constraints. If not specified, the default is the field name in lowercase.
    private String name;

    @Column(name = "student_college", length = 200)
    private String college;
    private String phone;
    private String fatherName;

    private boolean active = true; // Default value set to true

    @Lob
    private String about;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    // One student can have multiple certificates
    @ToString.Exclude   // 👈 prevents recursion
    private List<Certificate> certificates = new ArrayList<>(); // One-to-many relationship with Certificate entity

    /* CascadeType.ALL means that all operations (persist, merge, remove, refresh, detach) will be cascaded to the related entities.
    Ex: If a student is deleted, all their certificates will also be deleted.

    You can also use specific cascade types:
    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH
     */

    /* FetchType.LAZY means that the related entities will be loaded lazily, i.e., they will not be loaded until explicitly accessed.
    FetchType.LAZY is generally preferred for performance reasons, especially if the related entities are not always needed.
    Ex: Certificates will not be loaded until student.getCertificates() is called.

    FetchType.EAGER can be used if you want to load the related entities immediately when the parent entity is loaded.
     */


    // orphanRemoval = true means that if a certificate is removed from the list, it will also be deleted from the database.

    /*
    mappedBy = "student" indicates that the Student entity is not the owner of the relationship.
    The owner is the Certificate entity, which has a field named "student" that maps to this relationship.
    This means that the foreign key will be in the Certificate table, not in the Student table.
    If you want to make Student the owner of the relationship, you would need to add a @JoinColumn annotation in the Certificate entity to specify the foreign key column.
     */


    /*
    @OneToMany: Indicates a one-to-many relationship. Ex: One user can have multiple emails.
    @ManyToOne: Indicates a many-to-one relationship. Ex: Many users can belong to one department.
    @ManyToMany: Indicates a many-to-many relationship. Ex: Many students can enroll in many courses.
    @OneToOne: Indicates a one-to-one relationship. Ex: One user can have one aadhar card.
    @JoinColumn: Specify the foreign key column in the database for the relationship.
    @JoinTable: Specify the join table for many-to-many relationships.
     */

    public void addCertificate(Certificate certificate) {
        certificates.add(certificate);
        certificate.setStudent(this);
    }

}

