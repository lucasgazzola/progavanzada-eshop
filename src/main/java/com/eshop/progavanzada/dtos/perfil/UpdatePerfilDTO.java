package com.eshop.progavanzada.dtos.perfil;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdatePerfilDTO {
  @NotNull(message = "El id es obligatorio.")
  @NotEmpty(message = "El id no puede estar vacío.")
  @Positive(message = "El id debe ser positivo.")
  private Integer id;

  private String nombre;

  private String direccion;

  private String telefono;

  private Boolean eliminado;

  public boolean isEmpty() {
    return this.nombre == null && this.direccion == null && this.eliminado == null && this.telefono == null;
  }
}
