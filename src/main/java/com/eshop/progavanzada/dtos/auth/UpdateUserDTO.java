package com.eshop.progavanzada.dtos.auth;

import com.eshop.progavanzada.constants.Consts;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDTO {
  @NotNull
  private Integer id;

  @Pattern(regexp = Consts.EMAIL_REGEX, message = "El email no es válido")
  private String email;

  @NotEmpty(message = "La contraseña no puede estar vacía")
  @Size(min = 8, max = 24, message = "La contraseña debe tener entre 8 y 24 caracteres")
  private String password;

  private String rol;

  public boolean isEmpty() {
    return this.email == null && this.password == null && this.rol == null;
  }
}
