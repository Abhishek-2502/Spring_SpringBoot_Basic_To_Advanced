package com.sit.abhishek.entity;

// This class will represent DB Table
// Requires JPA (Java Persistence API) and MySQL Dependencies


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor  // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields
@Entity
public class ProductEntity {

    @Id  // Marks this field as the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Helps to generate a primary key automatically
    // strategy = GenerationType.IDENTITY means the database will handle the primary key generation

    // Column of Tables in DB
    private int id;
    private String name;
    private int price;
    private String category;

}
