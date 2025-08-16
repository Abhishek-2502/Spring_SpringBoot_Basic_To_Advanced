package com.example.advancedjpa.demo.controller;

import com.example.advancedjpa.demo.entity.Customer;
import com.example.advancedjpa.demo.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService cs) {
        this.customerService = cs;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer c) {
        return ResponseEntity.ok(customerService.save(c));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> list() {
        // use repository findAllNotDeleted in real app via service
        throw new UnsupportedOperationException("Implement listing via repository / service");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable UUID id) {
        customerService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}
