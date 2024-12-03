package com.eshop.progavanzada.dtos.categoria;

import lombok.Data;

@Data
public class CategoriaDTO {
  private Integer id;
  private String nombre;
  private String descripcion;
  private Boolean eliminado;
}
