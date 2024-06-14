package com.microservice.product.controller;

import com.microservice.product.entities.Product;
import com.microservice.product.service.IProductService;
import com.microservice.product.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crud-product")
public class ProductPrivateController {

    @Autowired
    private IProductService productService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.extractUserId(token);
        product.setUserId(userId);
        productService.save(product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody Product product, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long tokenUserId = jwtUtil.extractUserId(token);
        Product updateProduct = productService.updateProduct(product, productId, tokenUserId);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long tokenUserId = jwtUtil.extractUserId(token);
        Product product = productService.findById(id);
        productService.deleteProduct(id, tokenUserId);
        return ResponseEntity.ok("Producto eliminado: \n ID: " + product.getUserId() +" \n NAME: "+product.getName());
    }

}
