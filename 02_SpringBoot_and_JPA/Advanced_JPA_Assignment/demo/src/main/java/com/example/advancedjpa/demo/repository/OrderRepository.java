package com.example.advancedjpa.demo.repository;

import com.example.advancedjpa.demo.dto.OrderItemProjection;
import com.example.advancedjpa.demo.dto.OrderSummaryDto;
import com.example.advancedjpa.demo.entity.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    // 1. class-based DTO projection via JPQL
    @Query("select new com.example.advancedjpa.demo.dto.OrderSummaryDto(o.id, o.customer.name, o.totalAmount) from Order o")
    List<OrderSummaryDto> findOrderSummaries();

    // 2. interface projection for order items
    @Query("select i.productName as productName, i.quantity as quantity from OrderItem i where i.order.id = :orderId")
    List<OrderItemProjection> findItemsByOrderId(Long orderId);

    // 3. JPQL sorted
    @Query("select o from Order o order by o.totalAmount desc")
    List<Order> findAllOrderByTotalAmountDesc();

    // 4. native query example: highest total per order (returns order id)
    @Query(value = "SELECT * FROM orders o WHERE o.total_amount = (SELECT MAX(total_amount) FROM orders)", nativeQuery = true)
    Order findOrderWithHighestTotalNative();

    Page<Order> findAll(Pageable pageable);
}
