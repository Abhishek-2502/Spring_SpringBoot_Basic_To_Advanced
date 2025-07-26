package com.sit.abhishek.controller;

import com.sit.abhishek.entity.ProductEntity;
import com.sit.abhishek.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/product/{id}")
    public String editProducts(@PathVariable int id, @RequestBody ProductEntity product){
        return service.editProduct(id, product);
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable int id){
        return service.deleteProduct(id);
    }
}


