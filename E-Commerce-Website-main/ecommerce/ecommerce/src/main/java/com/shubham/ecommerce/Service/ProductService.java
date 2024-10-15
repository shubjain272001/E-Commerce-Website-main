package com.shubham.ecommerce.Service;

import com.shubham.ecommerce.Entity.Products;
import com.shubham.ecommerce.Repository.Product_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    Product_repo repo;


    public List<Products> getProducts() {
        return repo.findAll();
    }

    public Products getProductById(int id) {

        return repo.findById(id).orElse(null);
    }

    public Products addProduct(Products product , MultipartFile imageFile) throws IOException {

        product.setImageName(imageFile.getName());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    public Products updateProduct(int id ,Products product, MultipartFile imageFile) throws IOException {

        product.setImageName(imageFile.getName());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    public void deleteProduct(int productId) {
        repo.deleteById(productId);
    }

    public List<Products> getProductByName(String keyword) {
        return repo.getProductByName(keyword);
    }
}
