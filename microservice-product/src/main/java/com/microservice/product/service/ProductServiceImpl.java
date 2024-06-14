package com.microservice.product.service;

import com.microservice.product.entities.Product;
import com.microservice.product.error.NoAuthException;
import com.microservice.product.persistence.IProductRepository;
import com.microservice.product.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> findByIdUser(Long idUser) {
        return productRepository.findAllByUserId(idUser);
    }

    @Override
    public Product updateProduct(Product updatedProduct, Long id, Long tokenUserId) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(existingProduct -> {
            if (!existingProduct.getUserId().equals(tokenUserId)) {
                try {
                    throw new NoAuthException("401 - User not authorized");
                } catch (NoAuthException e) {
                    throw new RuntimeException(e);
                }
            }
            if (updatedProduct.getName() != null) {
                existingProduct.setName(updatedProduct.getName());
            }
            if (updatedProduct.getDescription() != null) {
                existingProduct.setDescription(updatedProduct.getDescription());
            }
            if (updatedProduct.getPrice() != 0) { // Asegúrate de que el precio no sea cero si debe actualizarse
                existingProduct.setPrice(updatedProduct.getPrice());
            }
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public void deleteProduct(Long id, Long userId) {
        Optional<Product> product = productRepository.findById(id);
        product.map(existingProduct -> {
            if (existingProduct.getUserId().equals(userId)) {
                productRepository.deleteById(id);
            } else {
                try {
                    throw new NoAuthException("401 - User not authorized");
                } catch (NoAuthException e) {
                    throw new RuntimeException(e);
                }
            }
            return "OK";
        }).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
}
