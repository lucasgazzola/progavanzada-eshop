package com.eshop.progavanzada.dtos.perfil;

import com.eshop.progavanzada.models.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PerfilDTO {

  @Positive(message = "El id debe ser positivo")
  private Integer id;

  @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres.")
  @NotNull(message = "El nombre es obligatorio")
  @NotEmpty(message = "El nombre no puede estar vacío")
  private String nombre;

  private String direccion;

  private String telefono;

  private User user;

  public boolean isEmpty() {
    return this.nombre == null && this.direccion == null && this.telefono == null;
  }
}
