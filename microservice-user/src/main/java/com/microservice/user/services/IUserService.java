package com.microservice.user.services;

import com.microservice.user.dto.ProductDTO;
import com.microservice.user.entities.User;
import com.microservice.user.error.NoAuthException;

import java.util.List;

public interface IUserService {

    List<User> findAll() throws NoAuthException;

    User findById(Long id);

    void save(User user);

    List<ProductDTO> findProductsByIdUser(Long idUser);

    //PRODUCT IN USER
    void saveProduct(ProductDTO productDTO);
}
