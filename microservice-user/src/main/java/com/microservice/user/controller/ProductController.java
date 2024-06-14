package com.microservice.user.controller;

import com.microservice.user.dto.ProductDTO;
import com.microservice.user.services.IUserService;
import com.microservice.user.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-product")
public class ProductController {



    @Autowired
    private IUserService service;


}
