package com.sit.abhishek.controller;

import com.sit.abhishek.entity.ProductEntity;
import com.sit.abhishek.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/products")
    public List<ProductEntity> getProducts(){
        return service.getProducts();
    }

    @PostMapping("/products")
    public String addProducts(@RequestBody ProductEntity product){
        return service.addProduct(product);
    }
}


