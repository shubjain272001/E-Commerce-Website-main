package com.shubham.ecommerce.Controller;

import com.shubham.ecommerce.Entity.Products;
import com.shubham.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {


    @Autowired
    ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Products>> getProducts(){
        return new ResponseEntity<>(service.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Products> getProduct(@PathVariable int id){

        Products prod = service.getProductById(id);

        if(prod != null){
            return new ResponseEntity<>(prod,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id){

        Products prod2 = service.getProductById(id);
        byte[] imageFile = prod2.getImageData();
        return ResponseEntity.ok().contentType(MediaType.valueOf(prod2.getImageType())).body(imageFile);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Products product,@RequestPart MultipartFile imageFile){
        try{
            Products prod1 = service.addProduct(product,imageFile);
            return new ResponseEntity<>(prod1,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Products product,@RequestPart MultipartFile imageFile) {
        Products prod =null;
        try{
            prod = service.updateProduct(id, product,imageFile);
        }catch(Exception e){
            return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
        }
        if(prod!=null){
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Products prod = service.getProductById(id);
        if(prod!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Product to be Deleted Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Products>> getProductByName(@RequestParam String keyword){
        List<Products> prods = service.getProductByName(keyword);
        return new ResponseEntity<>(prods,HttpStatus.OK);
    }
}
