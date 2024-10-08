package com.eshop.progavanzada.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString

public class Producto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String nombre;

  private String descripcion;

  private Double precio;

  @ManyToOne
  @JoinColumn(name = "marcaId", nullable = false)
  private Marca marca;

  private boolean eliminado = false;

  public void eliminarLogico() {
    this.setEliminado(true);
  }

  public void recuperarLogico() {
    this.setEliminado(false);
  }
}