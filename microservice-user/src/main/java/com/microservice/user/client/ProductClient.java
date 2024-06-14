package com.microservice.user.client;

import com.microservice.user.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-product", url = "localhost:8090")
public interface ProductClient {

    @GetMapping("/api/product/search-by-user/{idUser}")
    List<ProductDTO> findAllProductByUser(@PathVariable Long idUser);

    @PostMapping("/api/product/create")
    ResponseEntity<?> save(@RequestBody ProductDTO productDTO);
}
