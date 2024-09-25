package com.eshop.progavanzada.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString

public class Marca {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String nombre;

  private String descripcion;

  private boolean eliminado = false;

  public void eliminarLogico() {
    this.setEliminado(true);
  }

  public void recuperarLogico() {
    this.setEliminado(false);
  }
}