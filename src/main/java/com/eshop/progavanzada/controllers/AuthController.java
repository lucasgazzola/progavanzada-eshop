package com.eshop.progavanzada.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.progavanzada.dtos.JwtDTO;
import com.eshop.progavanzada.dtos.SignInDTO;
import com.eshop.progavanzada.dtos.SignUpDTO;
import com.eshop.progavanzada.services.AuthService;

import jakarta.validation.Valid;

// controllers/AuthController.java
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService service;

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDTO data) {
    this.service.signUp(data);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/signin")
  public ResponseEntity<JwtDTO> signIn(@RequestBody @Valid SignInDTO data) {
    JwtDTO jwt = this.service.signIn(data);
    return ResponseEntity.ok(jwt);
  }
}
