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
import com.eshop.progavanzada.dtos.auth.SignInDTO;
import com.eshop.progavanzada.dtos.auth.UpdateUserDTO;
import com.eshop.progavanzada.dtos.auth.UserDTO;
import com.eshop.progavanzada.dtos.auth.CreateUserDTO;
import com.eshop.progavanzada.dtos.auth.JwtDTO;
import com.eshop.progavanzada.exceptions.BadRequestException;
import com.eshop.progavanzada.mappers.user.UserMapper;
import com.eshop.progavanzada.models.Perfil;
import com.eshop.progavanzada.models.User;
import com.eshop.progavanzada.repositories.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthService implements UserDetailsService, IAuthServices {
  final int MAX_PASSWORD_LENGTH = 24;
  final int MIN_PASSWORD_LENGTH = 8;

  @Autowired
  UserRepository repository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenProvider tokenProvider;

  private Authentication authUser;

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserDetails user = repository.findByUsername(username);
    return user;
  }

  public UserDTO signUp(CreateUserDTO data) {
    if (repository.findByUsername(data.getEmail()) != null) {
      throw new BadRequestException("Ya existe el email");
    }
    String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
    User newUser = new User(data.getEmail(), encryptedPassword, data.getRol());

    Perfil perfil = new Perfil();
    perfil.setNombre(data.getNombre());
    newUser.setPerfil(perfil);
    return UserMapper.toDTO(repository.save(newUser));
  }

  public UserDTO updateUser(UpdateUserDTO updateUserDTO) {
    if (updateUserDTO.isEmpty()) {
      throw new BadRequestException("No se han enviado datos para actualizar");
    }
    User user = UserMapper.toModel(this.buscarPorId(updateUserDTO.getId()));
    return UserMapper.toDTO(this.repository.save(user));
  }

  public UserDTO buscarPorId(Integer id) {
    User user = repository.findById(id).orElse(null);
    if (user == null) {
      throw new BadRequestException("No se ha encontrado el usuario con id " + id);
    }
    return UserMapper.toDTO(user);
  }

  public JwtDTO signIn(SignInDTO data, HttpServletResponse response) {
    try {
      this.authUser = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword()));
    } catch (BadCredentialsException e) {
      throw new BadRequestException("Usuario o contraseña incorrectos");
    }

    String accessToken = tokenProvider.generateAccessToken((User) this.authUser.getPrincipal());

    Cookie cookie = new Cookie("accessToken", accessToken);
    cookie.setPath("/"); // La cookie es válida para todo el dominio
    cookie.setMaxAge(10800); // Duración de la cookie: 3 horas

    // Añade la cookie a la respuesta
    response.addCookie(cookie);
    return new JwtDTO(accessToken);
  }

  public String validateToken(HttpServletRequest request) {
    var cookies = request.getCookies();
    if (cookies == null) {
      throw new BadRequestException("No se ha enviado el token");
    }
    String token = this.getTokenFromCookies(cookies);
    return tokenProvider.validateToken(token);
  }

  public String getTokenFromCookies(Cookie[] cookies) {
    for (var cookie : cookies) {
      if (cookie.getName().equals("accessToken")) {
        return cookie.getValue();
      }
    }
    throw new BadRequestException("Token no encontrado");
  }
}