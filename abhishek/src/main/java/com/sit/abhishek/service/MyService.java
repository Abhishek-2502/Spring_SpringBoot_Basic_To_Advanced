package com.sit.abhishek.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    List<String> products = new ArrayList<String>();

    public List<String> getProducts() {
        return this.products;
    }

    public String addProducts(String product) {
        this.products.add(product);
        return "Product " + product + " added successfully";
    }

    public String deleteProduct(String product) {
        this.products.remove(product);
        return "Product " + product + " is deleted successfully";
    }

}