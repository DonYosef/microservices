package com.microservice.user.services;

import com.microservice.user.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUserService {

    List<User> findAll();

    User findById(Long id);

    void save(User user);

    List<User> findByIdProduct(Long idProduct);
}
