package com.eshop.progavanzada.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eshop.progavanzada.constants.Consts;
import com.eshop.progavanzada.dtos.SignUpDTO;
import com.eshop.progavanzada.exceptions.BadRequestException;
import com.eshop.progavanzada.models.User;
import com.eshop.progavanzada.repositories.UserRepository;

@Service
public class AuthService implements UserDetailsService {
  final int MAX_PASSWORD_LENGTH = 24;
  final int MIN_PASSWORD_LENGTH = 8;

  @Autowired
  UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    var user = repository.findByUsername(username);
    return user;
  }

  public User signUp(SignUpDTO data) throws BadRequestException {
    if (data.getNombre() == null || data.getNombre().isEmpty()) {
      throw new BadRequestException("El nombre es requerido");
    }
    if (data.getPassword() == null || data.getPassword().isEmpty()) {
      throw new BadRequestException("La contraseña es requerida");
    }
    if (data.getPassword().length() < Consts.MIN_PASSWORD_LENGTH) {
      throw new BadRequestException(
          "La contraseña debe tener al menos " + Consts.MIN_PASSWORD_LENGTH + " caracteres");
    }
    if (data.getPassword().length() > Consts.MAX_PASSWORD_LENGTH) {
      throw new BadRequestException(
          "La contraseña no puede tener más de " + Consts.MAX_PASSWORD_LENGTH + " caracteres");
    }
    if (data.getUsername() == null || data.getUsername().isEmpty()) {
      throw new BadRequestException("El email es requerido");
    }
    if (!Consts.EMAIL_REGEX_PATTERN.matcher(data.getUsername()).matches()) {
      throw new BadRequestException("El email no es válido");
    }
    if (repository.findByUsername(data.getUsername()) != null) {
      throw new BadRequestException("Ya existe el email");
    }

    String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
    User newUser = new User(data.getNombre(), data.getUsername(), encryptedPassword, data.getRol());
    return repository.save(newUser);
  }
}
