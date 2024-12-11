package com.eshop.progavanzada.dtos.auth;

import com.eshop.progavanzada.enums.UserRole;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {
  @NotNull(message = "El id es obligatorio.")
  @NotEmpty(message = "El id no puede estar vacío.")
  private Integer id;

  private String email;
  private String password;
  private UserRole rol;
}
