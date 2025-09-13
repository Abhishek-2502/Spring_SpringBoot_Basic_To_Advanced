package org.example.h2dbproject.controller;

import jakarta.validation.Valid;
import org.example.h2dbproject.dto.ProductDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
}
