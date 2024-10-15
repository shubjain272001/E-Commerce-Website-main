package com.shubham.ecommerce.Repository;


import com.shubham.ecommerce.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Product_repo extends JpaRepository<Products,Integer> {

    @Query("SELECT p from Products p WHERE "+
            "LOWER(p.name) LIKE LOWER(CONCAT('%' , :keyword, '%')) OR "+
            "LOWER(p.description) LIKE LOWER(CONCAT('%' , :keyword, '%')) OR "+
            "LOWER(p.brand) LIKE LOWER(CONCAT('%' , :keyword, '%')) OR "+
            "LOWER(p.category) LIKE LOWER(CONCAT('%' , :keyword, '%'))")
    List<Products> getProductByName(String keyword);
}
