package com.eshop.progavanzada.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.progavanzada.dtos.auth.SignInDTO;
import com.eshop.progavanzada.dtos.auth.CreateUserDTO;
import com.eshop.progavanzada.services.auth.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/auth")
public class AuthController {

  @Autowired
  private AuthService service;

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody @Valid CreateUserDTO data) {
    this.service.signUp(data);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body("{\"message\": \"Usuario creado exitosamente. Por favor, inicie sesión.\"}");

  }

  @PostMapping("/signin")
  public ResponseEntity<?> signIn(@RequestBody @Valid SignInDTO data, HttpServletResponse response) {
    this.service.signIn(data, response);
    return ResponseEntity.ok("{\"message\": \"Sesión iniciada exitosamente.\" }");
  }

  // @PostMapping("/signout")
  // public ResponseEntity<?> signOut(HttpServletResponse response) {
  // this.service.signOut(response);
  // return ResponseEntity.ok("{\"message\": \"Sesión cerrada exitosamente.\"}");
  // }

  @PostMapping("/validate-token")
  public ResponseEntity<?> validateToken(HttpServletRequest request) {
    this.service.validateToken(request);
    return ResponseEntity.ok().build();
  }
}
