package com.eshop.progavanzada.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateMarcaDTO {
  @Size(min = 2, max = 32, message = "La denominación debe tener entre 2 y 32 caracteres.")
  private String nombre;

  @Size(min = 1, max = 128, message = "La descripción debe tener entre 1 y 128 caracteres.")
  private String descripcion;

  public boolean isEmpty() {
    return this.nombre == null && this.descripcion == null;
  }
}
