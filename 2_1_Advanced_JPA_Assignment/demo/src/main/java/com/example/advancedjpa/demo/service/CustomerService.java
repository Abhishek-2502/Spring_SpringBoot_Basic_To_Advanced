package com.example.advancedjpa.demo.service;

import com.example.advancedjpa.demo.entity.Customer;
import com.example.advancedjpa.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer c) {
        return customerRepository.save(c);
    }

    public void softDelete(UUID id) {
        customerRepository.softDeleteCustomer(id);
    }
}
