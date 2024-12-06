package com.eshop.progavanzada.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.eshop.progavanzada.config.auth.TokenProvider;
import com.eshop.progavanzada.constants.Consts;
import com.eshop.progavanzada.dtos.auth.JwtDTO;
import com.eshop.progavanzada.dtos.auth.SignInDTO;
import com.eshop.progavanzada.dtos.auth.CreateUserDTO;
import com.eshop.progavanzada.exceptions.BadRequestException;
import com.eshop.progavanzada.models.User;
import com.eshop.progavanzada.repositories.UserRepository;

@Component
public class AuthService implements UserDetailsService {
  final int MAX_PASSWORD_LENGTH = 24;
  final int MIN_PASSWORD_LENGTH = 8;

  @Autowired
  UserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenProvider tokenProvider;

  private Authentication authUser;

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserDetails user = userRepository.findByUsername(username);
    return user;
  }

  public User signUp(CreateUserDTO data) throws BadRequestException {
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
    if (userRepository.findByUsername(data.getUsername()) != null) {
      throw new BadRequestException("Ya existe el email");
    }

    String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
    User newUser = new User(data.getUsername(), encryptedPassword, data.getRol());
    return userRepository.save(newUser);
  }

  public JwtDTO signIn(SignInDTO data) {

    try {
      this.authUser = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword()));
    } catch (BadCredentialsException e) {
      throw new BadRequestException("Usuario o contraseña incorrectos");
    }

    String accessToken = tokenProvider.generateAccessToken((User) this.authUser.getPrincipal());
    return new JwtDTO(accessToken);
  }
}