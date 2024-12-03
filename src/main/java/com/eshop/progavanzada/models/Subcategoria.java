package com.eshop.progavanzada.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Subcategoria {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String nombre;

  private String descripcion;

  @ManyToOne
  @JoinColumn(name = "categoriaId", nullable = false)
  private Categoria categoria;

  private Boolean eliminado = false;

  public void eliminarLogico() {
    this.setEliminado(true);
  }
}
