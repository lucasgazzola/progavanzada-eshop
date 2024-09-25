package com.eshop.progavanzada.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MarcaDTO {

  @Positive(message = "El id debe ser positivo")
  private Integer id;

  @NotNull(message = "El nombre es obligatorio")
  @NotEmpty(message = "El nombre no puede estar vacío")
  private String nombre;

  private String descripcion;
  private boolean eliminado;
}
