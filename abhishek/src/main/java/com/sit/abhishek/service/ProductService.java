package com.sit.abhishek.service;

import com.sit.abhishek.entity.ProductEntity;
import com.sit.abhishek.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository repo;

    public List<ProductEntity> getProducts(){
        return repo.findAll();
    }

    public String addProduct(ProductEntity product){
        repo.save(product);
        return "Added "+product+" successfully";
    }

    public String editProduct(int id, ProductEntity updatedProduct){
        if (repo.findById(id).isEmpty()){
            return "Product is not available";
        }
        else {
            ProductEntity p = repo.findById(id).get();
            p.setName(updatedProduct.getName());
            p.setPrice(updatedProduct.getPrice());
            p.setCategory(updatedProduct.getCategory());
            repo.save(p);
            return "Product Updated successfully";
        }
    }
}
