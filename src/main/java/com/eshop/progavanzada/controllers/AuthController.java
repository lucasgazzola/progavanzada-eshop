package com.eshop.progavanzada.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.progavanzada.config.auth.TokenProvider;
import com.eshop.progavanzada.dtos.JwtDTO;
import com.eshop.progavanzada.dtos.SignInDTO;
import com.eshop.progavanzada.dtos.SignUpDTO;
import com.eshop.progavanzada.models.User;
import com.eshop.progavanzada.services.AuthService;

import jakarta.validation.Valid;

// controllers/AuthController.java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private AuthService service;
  @Autowired
  private TokenProvider tokenService;

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDTO data) {
    this.service.signUp(data);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/signin")
  public ResponseEntity<JwtDTO> signIn(@RequestBody @Valid SignInDTO data) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
    var authUser = authenticationManager.authenticate(usernamePassword);
    var accessToken = tokenService.generateAccessToken((User) authUser.getPrincipal());
    return ResponseEntity.ok(new JwtDTO(accessToken));
  }
}
