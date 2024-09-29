package com.eshop.progavanzada.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "empty" })
public class MarcaDTO {

  @Positive(message = "El id debe ser positivo")
  private Integer id;

  @Size(min = 2, max = 32, message = "El nombre debe tener entre 2 y 32 caracteres.")
  @NotNull(message = "El nombre es obligatorio")
  @NotEmpty(message = "El nombre no puede estar vacío")
  private String nombre;

  private Boolean eliminado;

  private String descripcion;

  // @JsonIgnore
  public boolean isEmpty() {
    return this.nombre == null && this.eliminado == null && this.descripcion == null;
  }

}
