package com.eshop.progavanzada.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "empty" })
public class UpdateProductoDTO {
  @Size(min = 2, max = 32, message = "El nombre debe tener entre 2 y 32 caracteres.")

  private String nombre;
  private String descripcion;

  @PositiveOrZero(message = "El precio debe ser positivo o cero")
  private Double precio;

  private MarcaDTO marca;

  private Boolean eliminado;

  public boolean isEmpty() {
    return this.nombre == null && this.descripcion == null && this.precio == null && this.marca == null
        && this.eliminado == null;
  }
}
