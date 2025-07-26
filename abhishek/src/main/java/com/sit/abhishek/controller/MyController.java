package com.sit.abhishek.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sit.abhishek.service.MyService;

//Controller is used to make a REST API Controller class which is used to implement GetMapping, PostMapping
@RestController
public class MyController {

    @Autowired
    private MyService service; // Dependency Injection is the addition of service layer so that business logic can be added separately to the service layer
     // This step signifies dependency injection through MyService

    @GetMapping("/")
    public String getMessage() {
        return "Hello from Spring";
    }


    /*
    // Static api calls
    @GetMapping("/myproducts")
    public List<String> getProducts(){
        return List.of("Laptop","Mobile");
    }
    @PostMapping("/myproducts")
    public String addProduct(@RequestBody String product) {
        return "Product added: " + product;
    }

    */


    @GetMapping("/myproducts")
    public List<String> getProducts(){
        return service.getProducts();
    }

    @PostMapping("/myproducts")
    public String addProduct(@RequestBody String product) {
        return service.addProducts(product); //uses service layer to add an API Call using service logic
    }

    // placeholder {} is used to signify that the specific product entered will be deleted
    @DeleteMapping("/myproducts/{name}")
    public String deleteProduct(@PathVariable String name) {
        return service.deleteProduct(name);
    }
}