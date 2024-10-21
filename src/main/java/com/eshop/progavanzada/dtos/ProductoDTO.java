package com.eshop.progavanzada.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "empty" })
public class ProductoDTO {
  @Positive(message = "El id debe ser positivo")
  private Integer id;

  @NotNull(message = "El nombre es obligatorio")
  @NotEmpty(message = "El nombre no puede estar vacío")
  private String nombre;
  private String descripcion;

  @PositiveOrZero(message = "El precio debe ser positivo o cero")
  private Double precio;
  private Boolean eliminado;
  private MarcaDTO marca; // Incluimos MarcaDTO en vez de solo marcaId

  public boolean isEmpty() {
    return this.id == null && this.nombre == null && this.descripcion == null && this.precio == null
        && this.eliminado == null && this.marca == null && this.marca == null;
  }
}
