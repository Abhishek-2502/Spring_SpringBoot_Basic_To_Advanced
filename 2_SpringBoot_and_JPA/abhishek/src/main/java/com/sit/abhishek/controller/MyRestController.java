package com.sit.abhishek.controller;


import java.util.List;

import com.sit.abhishek.entity.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sit.abhishek.service.MyService;

//RestController is used to make a REST API Controller class which is used to implement GetMapping, PostMapping, etc.
@RestController
public class MyRestController {

    @Autowired
    private MyService service; // Dependency Injection is the addition of service layer so that business logic can be added separately to the service layer. This step signifies dependency injection through MyService

    @GetMapping("/get")
    public String getMessage1() {
        return "Hello from Spring by GetMapping";
    }


    /*
    // Static api calls
    @GetMapping("/products")
    public List<String> getProducts(){
        return List.of("Laptop","Mobile");
    }
    @PostMapping("/products")
    public String addProduct(@RequestBody String product) {
        return "Product added: " + product;
    }

    */


    @GetMapping("/products")
    public List<ProductModel> getProducts() {
        return service.getProducts();
    }

    @PostMapping("/products")
    public String addProduct(@RequestBody ProductModel product) {
        return service.addProducts(product);
    }

    @PutMapping("/products/{index}")
    public String updateProduct(@PathVariable int index, @RequestBody ProductModel newProduct) {
        return service.updateProduct(index, newProduct);
    }

    @DeleteMapping("/products/{name}")
    public ResponseEntity<String> deleteProduct(@PathVariable String name) {
        String result = service.deleteProduct(name);
        if (result.contains("deleted successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

}