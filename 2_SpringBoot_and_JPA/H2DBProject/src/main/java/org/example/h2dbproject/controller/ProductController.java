package org.example.h2dbproject.controller;

import jakarta.validation.Valid;
import org.example.h2dbproject.dto.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    List<ProductDTO> products = new ArrayList<>();

    @GetMapping("/product")
    public List<ProductDTO> getProducts() {
        return this.products;
    }

    @PostMapping("/product")
    public String addProduct(@Valid @RequestBody ProductDTO product) {
        this.products.add(product);
        return "Product: "+ product.getName() +  " is added successfully";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        Map<String,String> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
