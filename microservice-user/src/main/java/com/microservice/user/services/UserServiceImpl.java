package com.microservice.user.services;

import com.microservice.user.client.ProductClient;
import com.microservice.user.dto.ProductDTO;
import com.microservice.user.entities.User;
import com.microservice.user.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductClient productClient;

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
    public List<ProductDTO> findProductsByIdUser(Long idUser) {
        //Obtenemos el usuarios
        User user = userRepository.findById(idUser).orElseThrow();

        //Obtenemos los productos del usuario
        return productClient.findAllProductByUser(idUser);
    }
}
