package com.eshop.progavanzada.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class VarianteProducto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String talla; // o atributo relacionado (color, etc.)
  private String color;

  @OneToOne
  @JoinColumn(name = "stock_id")
  private Stock stock;

  @ManyToOne
  @JoinColumn(name = "producto_id")
  private Producto producto;
}
