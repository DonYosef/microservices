package com.microservice.user.services;

import com.microservice.user.dto.ProductDTO;
import com.microservice.user.entities.User;

import java.util.List;

public interface IUserService {

    List<User> findAll();

    User findById(Long id);

    void save(User user);

    List<ProductDTO> findProductsByIdUser(Long idUser);
}
