package com.microservice.user.client;

import com.microservice.user.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-product", url = "localhost:8090")
public interface ProductClient {

    @GetMapping("/search-by-product/{idUser}")
    List<ProductDTO> findAllProductByUser(@PathVariable Long idUser);
}
