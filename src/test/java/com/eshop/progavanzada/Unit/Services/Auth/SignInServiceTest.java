package com.eshop.progavanzada.Unit.Services.Auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.eshop.progavanzada.config.auth.TokenProvider;
import com.eshop.progavanzada.dtos.auth.JwtDTO;
import com.eshop.progavanzada.dtos.auth.SignInDTO;
import com.eshop.progavanzada.dtos.auth.CreateUserDTO;
import com.eshop.progavanzada.enums.UserRole;
import com.eshop.progavanzada.exceptions.BadRequestException;
import com.eshop.progavanzada.models.User;
import com.eshop.progavanzada.repositories.UserRepository;
import com.eshop.progavanzada.services.auth.AuthService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SignInServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private TokenProvider tokenProvider;

  @InjectMocks
  private AuthService authService;

  private User user;

  private Authentication authUser;

  @BeforeEach
  void setup() {
    // Crear un nuevo usuario antes de cada prueba
    CreateUserDTO signUpDTO = new CreateUserDTO();
    signUpDTO.setUsername("john.doe@example.com");
    signUpDTO.setPassword("12345678");
    signUpDTO.setRol(UserRole.USER);

    // Crear un usuario simulado
    this.user = new User(signUpDTO.getUsername(),
        signUpDTO.getPassword(), signUpDTO.getRol());

    this.authUser = new UsernamePasswordAuthenticationToken(user,
        null);

    // Simular el comportamiento del repositorio
    when(userRepository.findByUsername("john.doe@example.com")).thenReturn(null);
    when(tokenProvider.generateAccessToken(user)).thenReturn("fake-jwt-token");

    authService.signUp(signUpDTO);
  }

  @DisplayName("Logging de usuario exitoso")
  @Test
  void testLoggearUsuario_Exito() {
    // Simular la autenticación exitosa
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authUser);

    // Crear el DTO para iniciar sesión
    SignInDTO signInDTO = new SignInDTO();
    signInDTO.setUsername("john.doe@example.com");
    signInDTO.setPassword("12345678");

    // Llamar al servicio para loguear el usuario
    JwtDTO jwt = authService.signIn(signInDTO);

    // Verificar que el JWT no es nulo
    assertEquals(jwt.accessToken(), "fake-jwt-token");
  }

  @DisplayName("Logging de usuario no exitoso por contraseña incorrecta")
  @Test
  void testLoggearUsuario() {
    // Simular la autenticación fallida
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new BadCredentialsException("Usuario o contraseña incorrectos"));

    // Crear el DTO para iniciar sesión
    SignInDTO signInDTO = new SignInDTO();
    signInDTO.setUsername("john.doe@example.com");
    signInDTO.setPassword("bad-password");

    // Llamar al servicio para loguear el usuario
    assertThrows(BadRequestException.class, () -> this.authService.signIn(signInDTO));
  }
}
