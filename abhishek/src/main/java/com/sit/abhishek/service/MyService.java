package com.sit.abhishek.service;

import java.util.ArrayList;
import java.util.List;

import com.sit.abhishek.entity.ProductEntity;
import com.sit.abhishek.entity.ProductModel;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    List<ProductModel> products = new ArrayList<>();

    public List<ProductModel> getProducts() {
        return this.products;
    }

    public String addProducts(ProductModel product) {
        products.add(product);
        return "Product " + product + " added successfully";
    }

    public String updateProduct(int index, ProductModel newProduct) {
        if (index < 0 || index >= products.size()) {
            return "Invalid index: " + index;
        }
        ProductModel oldProduct = products.set(index, newProduct);
        return "Product " + oldProduct + " updated to " + newProduct;
    }

    public String deleteProduct(String productName) {
        boolean removed = products.removeIf(p -> p.getName().equalsIgnoreCase(productName.trim()));
        if (removed) {
            return "Product " + productName + " is deleted successfully";
        } else {
            return "Product " + productName + " not found";
        }
    }

}