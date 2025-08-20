package com.example.advancedjpa.demo.controller;

import com.example.advancedjpa.demo.dto.OrderItemProjection;
import com.example.advancedjpa.demo.dto.OrderSummaryDto;
import com.example.advancedjpa.demo.entity.Order;
import com.example.advancedjpa.demo.entity.OrderStatus;
import com.example.advancedjpa.demo.repository.OrderRepository;
import com.example.advancedjpa.demo.repository.OrderSpecifications;
import com.example.advancedjpa.demo.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @GetMapping("/summaries")
    public ResponseEntity<List<OrderSummaryDto>> summaries() {
        return ResponseEntity.ok(orderRepository.findOrderSummaries());
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<OrderItemProjection>> items(@PathVariable Long id) {
        return ResponseEntity.ok(orderRepository.findItemsByOrderId(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Order>> search(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderDate") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) String customerNameContains,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Order> spec =
                        OrderSpecifications.hasStatus(status)
                .and(OrderSpecifications.totalAmountGreaterThanOrEqual(minAmount))
                .and(OrderSpecifications.totalAmountLessThanOrEqual(maxAmount))
                .and(OrderSpecifications.customerNameLike(customerNameContains))
                .and(OrderSpecifications.orderDateBetween(startDate, endDate));

        Page<Order> result = orderRepository.findAll(spec, pageable);
        return ResponseEntity.ok(result);
    }

    // demo transactional endpoint
    @PostMapping("/create/{customerId}")
    public ResponseEntity<Order> createAndDeduct(@PathVariable UUID customerId,
                                                 @RequestBody Order order,
                                                 @RequestParam BigDecimal deduct,
                                                 @RequestParam(defaultValue = "false") boolean failAfterSave) {
        Order saved = orderService.createOrderAndDeduct(customerId, order, deduct, failAfterSave);
        return ResponseEntity.ok(saved);
    }
}