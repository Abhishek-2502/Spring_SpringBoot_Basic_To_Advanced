package com.example.advancedjpa.demo.repository;

import com.example.advancedjpa.demo.entity.Order;
import com.example.advancedjpa.demo.entity.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderSpecifications {
    public static Specification<Order> hasStatus(OrderStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Order>
    totalAmountGreaterThanOrEqual(BigDecimal min) {
        return (root, query, cb) -> min == null ? null : cb.greaterThanOrEqualTo(root.get("totalAmount"), min);
    }

    public static Specification<Order>
    totalAmountLessThanOrEqual(BigDecimal max) {
        return (root, query, cb) -> max == null ? null : cb.lessThanOrEqualTo(root.get("totalAmount"), max);
    }

    public static Specification<Order> orderDateBetween(LocalDateTime
                                                                start, LocalDateTime end) {
        return (root, query, cb) -> {
            if (start == null && end == null) return null;
            if (start != null && end != null) return cb.between(root.get("orderDate"), start, end);
            if (start != null) return cb.greaterThanOrEqualTo(root.get("orderDate"), start);
            return cb.lessThanOrEqualTo(root.get("orderDate"), end);
        };
    }

    public static Specification<Order> customerNameLike(String namePart) {
        return (root, query, cb) -> {
            if (namePart == null || namePart.isBlank()) return null;
            return cb.like(cb.lower(root.get("customer").get("name")), "%" + namePart.toLowerCase() + "%");
        };
    }
}
