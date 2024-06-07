package com.microservice.user.controller;

import com.microservice.user.entities.User;
import com.microservice.user.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void  saveUSer(@RequestBody User user){
        userService.save(user);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("search-products/{idUser}")
    public ResponseEntity<?> findProductsByIdUser(@PathVariable Long idUser){
        return ResponseEntity.ok(userService.findProductsByIdUser(idUser));
    }
}
