package com.microservice.product.persistence;

import com.microservice.product.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAllByUserId(Long idUser);
}
