package com.sit.abhishek.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sit.abhishek.service.MyService;

//RestController is used to make a REST API Controller class which is used to implement GetMapping, PostMapping, etc.
@RestController
public class MyRestController {

    @Autowired
    private MyService service; // Dependency Injection is the addition of service layer so that business logic can be added separately to the service layer. This step signifies dependency injection through MyService

    @RequestMapping("/")
    public String getMessage() {
        return "Hello from Spring by RequestMapping";
    }

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
    public List<String> getProducts() {
        return service.getProducts();
    }

    @PostMapping("/products")
    public String addProduct(@RequestBody String product) {
        return service.addProducts(product); //uses service layer to add an API Call using service logic
    }

    // placeholder {} is used to signify that the specific product entered will be deleted
    @DeleteMapping("/products/{name}")
    public String deleteProduct(@PathVariable String name) {
        return service.deleteProduct(name);
    }
}