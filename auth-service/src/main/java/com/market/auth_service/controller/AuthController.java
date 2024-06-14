package com.market.auth_service.controller;

import com.market.auth_service.dto.AuthRequest;
import com.market.auth_service.entity.UserCredential;
import com.market.auth_service.repository.UserCredentialRepository;
import com.market.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserCredentialRepository repository;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user){
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
                Optional<UserCredential> user = repository.findByEmail(authRequest.getEmail());
                Long userId = user.map(UserCredential::getId).orElse(0L);
            return service.generateToken(authRequest.getEmail(), userId);
        }else{
            throw new RuntimeException("Invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        service.validateToken(token);
        return "Token valid";
    }
}
