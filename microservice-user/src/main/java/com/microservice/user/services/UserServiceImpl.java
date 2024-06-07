package com.microservice.user.services;

import com.microservice.user.entities.User;
import com.microservice.user.persistence.UserRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService{

    @Auto
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> findByIdProduct(Long idProduct) {
        return userRepository.findAllByProductId(idProduct);
    }
}
