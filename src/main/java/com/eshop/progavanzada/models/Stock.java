package com.eshop.progavanzada.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Stock {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer cantidad;

  private Integer cantidadMinima;

  @OneToOne
  @JoinColumn(name = "variante_producto_id")
  private VarianteProducto varianteProducto;

  public void agregarStock(Integer cantidad) {
    this.cantidad += cantidad;
  }

  public void quitarStock(Integer cantidad) {
    this.cantidad -= cantidad;
  }
}
