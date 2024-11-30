package com.eshop.progavanzada.Unit.Services.Auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.eshop.progavanzada.dtos.SignUpDTO;
import com.eshop.progavanzada.enums.UserRole;
import com.eshop.progavanzada.exceptions.BadRequestException;
import com.eshop.progavanzada.models.User;
import com.eshop.progavanzada.repositories.UserRepository;
import com.eshop.progavanzada.services.AuthService;

@SpringBootTest
public class SignUpServiceTest {

  // Simula el repositorio de usuarios para no acceder a la base de datos real
  @Mock
  private UserRepository userRepository;

  // Inyectamos el mock en AuthService
  @InjectMocks
  private AuthService authService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @DisplayName("Registrar usuario exitoso con la longitud mínima de la contraseña")
  @Test
  void testRegistrarUsuario_Exito_ExactMinLengthPassword() {
    // Generamos un número aleatorio para evitar conflictos con otros tests
    int randomNumber = (int) (Math.random() * 99999999);

    // Creamos un objeto SignUpDTO con los datos del usuario
    // Contraseña con longitud mínima
    SignUpDTO signUpDTO = new SignUpDTO();
    signUpDTO.setUsername("john.doe" + randomNumber + "@example.com");
    signUpDTO.setPassword("12345678");
    signUpDTO.setRol(UserRole.USER);

    // Creamos un objeto User con los datos del usuario
    User newUser = new User(signUpDTO.getUsername(),
        passwordEncoder.encode(signUpDTO.getPassword()),
        signUpDTO.getRol());

    // Simulamos el guardado del usuario en la base de datos
    when(userRepository.save(newUser)).thenReturn(newUser);

    User user = this.authService.signUp(signUpDTO);

    // Verificamos que los datos del usuario sean correctos
    assertEquals(user.getUsername(), signUpDTO.getUsername());
    assertTrue(passwordEncoder.matches(signUpDTO.getPassword(), user.getPassword()));
    assertEquals(user.getRol(), signUpDTO.getRol());
  }

  @DisplayName("Registro usuario exitoso con la longitud máxima de la contraseña")
  @Test
  void testRegistrarUsuario_Exito_ExactMaxLengthPassword() {
    int randomNumber = (int) (Math.random() * 99999999);

    SignUpDTO signUpDTO = new SignUpDTO();

    // Creamos un objeto SignUpDTO con los datos del usuario
    // Contraseña con longitud máxima
    signUpDTO.setUsername("john.doe" + randomNumber + "@example.com");
    signUpDTO.setPassword("123456789012345678901234");
    signUpDTO.setRol(UserRole.USER);

    User newUser = new User(signUpDTO.getUsername(),
        passwordEncoder.encode(signUpDTO.getPassword()),
        signUpDTO.getRol());

    when(userRepository.save(newUser)).thenReturn(newUser);

    User user = this.authService.signUp(signUpDTO);

    assertEquals(user.getUsername(), signUpDTO.getUsername());
    assertTrue(passwordEncoder.matches(signUpDTO.getPassword(), user.getPassword()));
    assertEquals(user.getRol(), signUpDTO.getRol());
  }

  @DisplayName("Registro usuario no exitoso por no contener email")
  @Test
  void testRegistrarUsuario_NoEmail() {
    // Creamos un objeto SignUpDTO con los datos del usuario
    // Sin email
    SignUpDTO signUpDTO = new SignUpDTO();
    signUpDTO.setPassword("password");
    signUpDTO.setRol(UserRole.USER);

    User newUser = new User(signUpDTO.getUsername(),
        passwordEncoder.encode(signUpDTO.getPassword()),
        signUpDTO.getRol());

    when(userRepository.save(newUser)).thenReturn(newUser);

    assertThrows(BadRequestException.class, () -> this.authService.signUp(signUpDTO));
  }

  @DisplayName("Registro usuario no exitoso por contener email con formato incorrecto")
  @Test
  void testRegistrarUsuario_BadEmail() {
    // Creamos un objeto SignUpDTO con los datos del usuario
    // Con email mal formateado
    SignUpDTO signUpDTO = new SignUpDTO();
    // Username debe ser un email válido
    signUpDTO.setUsername("john.doe");
    signUpDTO.setPassword("12345678");
    signUpDTO.setRol(UserRole.USER);

    User newUser = new User(signUpDTO.getUsername(),
        passwordEncoder.encode(signUpDTO.getPassword()),
        signUpDTO.getRol());

    when(userRepository.save(newUser)).thenReturn(newUser);

    // Esperamos un BadRequestException
    assertThrows(BadRequestException.class, () -> this.authService.signUp(signUpDTO));
  }

  @DisplayName("Registro usuario no exitoso por no contener nombre")
  @Test
  void testRegistrarUsuario_NoName() {
    int randomNumber = (int) (Math.random() * 99999999);

    // Creamos un objeto SignUpDTO con los datos del usuario
    // Sin nombre
    SignUpDTO signUpDTO = new SignUpDTO();
    signUpDTO.setUsername("john.doe" + randomNumber + "@example.com");
    signUpDTO.setPassword("password");
    signUpDTO.setRol(UserRole.USER);

    User newUser = new User(signUpDTO.getUsername(),
        passwordEncoder.encode(signUpDTO.getPassword()),
        signUpDTO.getRol());

    when(userRepository.save(newUser)).thenReturn(newUser);

    // Esperamos un BadRequestException
    assertThrows(BadRequestException.class, () -> this.authService.signUp(signUpDTO));
  }

  @DisplayName("Registro usuario no exitoso por no contener contraseña")
  @Test
  void testRegistrarUsuario_NoPassword() {
    int randomNumber = (int) (Math.random() * 99999999);

    // Creamos un objeto SignUpDTO con los datos del usuario
    // Sin contraseña
    SignUpDTO signUpDTO = new SignUpDTO();
    signUpDTO.setUsername("john.doe" + randomNumber + "@example.com");
    signUpDTO.setRol(UserRole.USER);

    User newUser = new User(signUpDTO.getUsername(), null, signUpDTO.getRol());

    when(userRepository.save(newUser)).thenReturn(newUser);

    // Esperamos un BadRequestException
    assertThrows(BadRequestException.class, () -> this.authService.signUp(signUpDTO));
  }

  @DisplayName("Registro usuario no exitoso por contraseña muy corta")
  @Test
  void testRegistrarUsuario_TooShortPassword() {
    int randomNumber = (int) (Math.random() * 99999999);

    // Creamos un objeto SignUpDTO con los datos del usuario
    // Contraseña con longitud menor a la mínima
    SignUpDTO signUpDTO = new SignUpDTO();
    signUpDTO.setUsername("john.doe" + randomNumber + "@example.com");
    signUpDTO.setPassword("1234567");
    signUpDTO.setRol(UserRole.USER);

    User newUser = new User(signUpDTO.getUsername(),
        passwordEncoder.encode(signUpDTO.getPassword()),
        signUpDTO.getRol());

    when(userRepository.save(newUser)).thenReturn(newUser);

    // Esperamos un BadRequestException
    assertThrows(BadRequestException.class, () -> this.authService.signUp(signUpDTO));
  }

  @DisplayName("Registro usuario no exitoso por contraseña muy larga")
  @Test
  void testRegistrarUsuario_TooLongPassword() {
    int randomNumber = (int) (Math.random() * 99999999);

    // Sin contraseña
    // Contraseña con longitud mayor a la máxima
    SignUpDTO signUpDTO = new SignUpDTO();
    signUpDTO.setUsername("john.doe" + randomNumber + "@example.com");
    signUpDTO.setPassword("1234567890123456789012345");
    signUpDTO.setRol(UserRole.USER);

    User newUser = new User(signUpDTO.getUsername(),
        passwordEncoder.encode(signUpDTO.getPassword()),
        signUpDTO.getRol());

    when(userRepository.save(newUser)).thenReturn(newUser);

    // Esperamos un BadRequestException
    assertThrows(BadRequestException.class, () -> this.authService.signUp(signUpDTO));
  }
}
