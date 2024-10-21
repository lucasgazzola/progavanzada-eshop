package com.eshop.progavanzada.dtos;

import com.eshop.progavanzada.constants.Consts;
import com.eshop.progavanzada.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpDTO {

  @NotNull(message = "El nombre es obligatorio")
  @NotEmpty(message = "El nombre no puede estar vacío")
  private String nombre;

  @NotNull(message = "El email es obligatorio")
  @Email(message = "El email no es válido")
  @Pattern(regexp = Consts.EMAIL_REGEX, message = "El email no es válido")
  private String username;

  @NotNull(message = "La contraseña es obligatoria")
  @NotEmpty(message = "La contraseña no puede estar vacía")
  @Size(min = 8, max = 24, message = "La contraseña debe tener entre 8 y 24 caracteres")
  private String password;

  @NotNull(message = "El rol es obligatorio")
  private UserRole rol;

}
