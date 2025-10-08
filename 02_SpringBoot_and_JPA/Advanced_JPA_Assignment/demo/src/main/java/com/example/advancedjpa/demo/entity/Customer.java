package com.example.advancedjpa.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customers", uniqueConstraints =
@UniqueConstraint(columnNames = "email"))
public class Customer {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotBlank
    @Size(min = 3)
    private String name;

    @Email
    @NotBlank
    private String email;

    @CreatedDate
    private LocalDateTime registeredDate;
    private boolean deleted = false; // soft delete flag
    // example extra field used for transaction demo
    private java.math.BigDecimal creditLimit = java.math.BigDecimal.ZERO;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<Order> orders = new ArrayList<>();
    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (registeredDate == null) registeredDate = LocalDateTime.now();
    }
}