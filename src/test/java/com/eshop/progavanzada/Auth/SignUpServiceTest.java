package com.eshop.progavanzada.Auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.eshop.progavanzada.dtos.SignUpDTO;
import com.eshop.progavanzada.enums.UserRole;
import com.eshop.progavanzada.exceptions.BadRequestException;
import com.eshop.progavanzada.models.User;
import com.eshop.progavanzada.services.AuthService;

@SpringBootTest
public class SignUpServiceTest {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthService authService; // Inyectamos el mock en AuthService

  @Test
  void testRegistrarUsuario_Exito_ExactMinLengthPassword() {
    int randomNumber = (int) (Math.random() * 99999999);

    SignUpDTO signUpDTO = new SignUpDTO();

    signUpDTO.setNombre("John Doe");
    signUpDTO.setUsername("john.doe" + randomNumber + "@example.com");
    signUpDTO.setPassword("12345678");
    signUpDTO.setRol(UserRole.USER);

    User user = this.authService.signUp(signUpDTO);

    assertEquals(user.getUsername(), signUpDTO.getUsername());
    assertTrue(passwordEncoder.matches(signUpDTO.getPassword(), user.getPassword()));
    assertEquals(user.getRol(), signUpDTO.getRol());
  }

  @Test
  void testRegistrarUsuario_Exito_ExactMaxLengthPassword() {
    int randomNumber = (int) (Math.random() * 99999999);

    SignUpDTO signUpDTO = new SignUpDTO();

    signUpDTO.setNombre("John Doe");
    signUpDTO.setUsername("john.doe" + randomNumber + "@example.com");
    signUpDTO.setPassword("123456789012345678901234");
    signUpDTO.setRol(UserRole.USER);

    User user = this.authService.signUp(signUpDTO);

    assertEquals(user.getUsername(), signUpDTO.getUsername());
    assertTrue(passwordEncoder.matches(signUpDTO.getPassword(), user.getPassword()));
    assertEquals(user.getRol(), signUpDTO.getRol());
  }

  @Test
  void testRegistrarUsuario_NoEmail() {

    SignUpDTO signUpDTO = new SignUpDTO();

    signUpDTO.setNombre("John Doe");
    signUpDTO.setPassword("password");
    signUpDTO.setRol(UserRole.USER);

    assertThrows(BadRequestException.class, () -> this.authService.signUp(signUpDTO));
  }

  @Test
  void testRegistrarUsuario_BadEmail() {
    int randomNumber = (int) (Math.random() * 99999999);

    SignUpDTO signUpDTO = new SignUpDTO();

    signUpDTO.setNombre("John Doe");
    signUpDTO.setUsername("john.doe" + randomNumber);
    signUpDTO.setPassword("12345678");
    signUpDTO.setRol(UserRole.USER);

    assertThrows(BadRequestException.class, () -> this.authService.signUp(signUpDTO));
  }

  @Test
  void testRegistrarUsuario_NoName() {
    int randomNumber = (int) (Math.random() * 99999999);

    SignUpDTO signUpDTO = new SignUpDTO();
    signUpDTO.setUsername("john.doe" + randomNumber + "@example.com");
    signUpDTO.setPassword("password");
    signUpDTO.setRol(UserRole.USER);

    assertThrows(BadRequestException.class, () -> this.authService.signUp(signUpDTO));
  }

  @Test
  void testRegistrarUsuario_NoPassword() {
    int randomNumber = (int) (Math.random() * 99999999);

    SignUpDTO signUpDTO = new SignUpDTO();
    signUpDTO.setUsername("john.doe" + randomNumber + "@example.com");
    signUpDTO.setNombre("John Doe");
    signUpDTO.setRol(UserRole.USER);

    assertThrows(BadRequestException.class, () -> this.authService.signUp(signUpDTO));
  }

  @Test
  void testRegistrarUsuario_TooShortPassword() {
    int randomNumber = (int) (Math.random() * 99999999);

    SignUpDTO signUpDTO = new SignUpDTO();

    signUpDTO.setNombre("John Doe");
    signUpDTO.setUsername("john.doe" + randomNumber + "@example.com");
    signUpDTO.setPassword("1234567");
    signUpDTO.setRol(UserRole.USER);

    assertThrows(BadRequestException.class, () -> this.authService.signUp(signUpDTO));
  }

  @Test
  void testRegistrarUsuario_TooLongPassword() {
    int randomNumber = (int) (Math.random() * 99999999);

    SignUpDTO signUpDTO = new SignUpDTO();

    signUpDTO.setNombre("John Doe");
    signUpDTO.setUsername("john.doe" + randomNumber + "@example.com");
    signUpDTO.setPassword("1234567890123456789012345");
    signUpDTO.setRol(UserRole.USER);

    assertThrows(BadRequestException.class, () -> this.authService.signUp(signUpDTO));
  }

  // @Test
  // @Timeout(value = 500, unit = TimeUnit.MILLISECONDS) // El test fallará si
  // toma más de 500ms
  // void testCalcularIMC_ExitosoTimeout() {
  // // Arrange
  // double peso = 70.0;
  // double altura = 1.75;

  // // Act
  // double imc = imcService.calcularIMC(peso, altura);

  // // Assert
  // assertEquals(22.86, imc, 0.01);
  // }

  // @Test
  // void testCalcularIMC_DentroDelTiempoEsperado() {
  // // Arrange
  // double peso = 70.0;
  // double altura = 1.75;

  // // Medimos el tiempo antes de la ejecución
  // long startTime = System.nanoTime();

  // // Act
  // double imc = imcService.calcularIMC(peso, altura);

  // // Medimos el tiempo después de la ejecución
  // long endTime = System.nanoTime();

  // // Calculamos el tiempo en milisegundos
  // long duration = (endTime - startTime) / 1_000_000; // Convertir a
  // milisegundos

  // // Assert
  // assertTrue(duration < 500, "La ejecución del método tomó demasiado tiempo: "
  // + duration + " ms");
  // }

}
