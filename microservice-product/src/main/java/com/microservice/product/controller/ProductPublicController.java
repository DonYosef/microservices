package com.microservice.product.controller;

import com.microservice.product.entities.Product;
import com.microservice.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/product")
@RestController
public class ProductPublicController {

    @Autowired
    private IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<?> findAllProduct(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/search-by-user/{idUser}")
    public ResponseEntity<?> findByIdUser(@PathVariable Long idUser){
        return ResponseEntity.ok(productService.findByIdUser(idUser));
    }
}
