package com.example.advancedjpa.demo.repository;

import com.example.advancedjpa.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>,
        CustomerRepositoryCustom {
    // exclude soft-deleted customers by default
    @Query("select c from Customer c where c.deleted = false and c.id =:id")
    Optional<Customer> findByIdAndNotDeleted(UUID id);

    @Query("select c from Customer c where c.deleted = false")
    java.util.List<Customer> findAllNotDeleted();

    boolean existsByEmail(String email);
}
