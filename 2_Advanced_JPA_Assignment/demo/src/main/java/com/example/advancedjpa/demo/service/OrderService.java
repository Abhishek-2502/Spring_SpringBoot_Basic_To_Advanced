package com.example.advancedjpa.demo.service;

import com.example.advancedjpa.demo.entity.Customer;
import com.example.advancedjpa.demo.entity.Order;
import com.example.advancedjpa.demo.repository.CustomerRepository;
import com.example.advancedjpa.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    // transactional operation: deduct credit and create order
    @Transactional
    public Order createOrderAndDeduct(UUID customerId, Order order,
                                      BigDecimal deductAmount, boolean throwAfterSave) {
        Customer c = customerRepository.findByIdAndNotDeleted(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // deduct credit limit
        c.setCreditLimit(c.getCreditLimit().subtract(deductAmount));
        customerRepository.save(c);
        // set relation and save order
        order.setCustomer(c);
        Order saved = orderRepository.save(order);
        // optionally trigger rollback
        if (throwAfterSave) {
            throw new RuntimeException("Simulated failure after saving order — should rollback everything");
        }
        return saved;
    }
}