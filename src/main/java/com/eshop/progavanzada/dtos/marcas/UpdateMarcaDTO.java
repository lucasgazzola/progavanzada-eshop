package com.eshop.progavanzada.dtos.marcas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "empty" })

public class UpdateMarcaDTO {
  @Size(min = 2, max = 32, message = "El nombre debe tener entre 2 y 32 caracteres.")
  private String nombre;

  private String descripcion;

  private Boolean eliminado;

  public boolean isEmpty() {
    return this.nombre == null && this.descripcion == null && this.eliminado == null;
  }
}
