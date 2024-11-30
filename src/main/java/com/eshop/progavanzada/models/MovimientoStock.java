package com.eshop.progavanzada.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class MovimientoStock {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "variante_id")
  private VarianteProducto varianteProducto;

  private int cantidadAjustada;
  private String razon; // Ejemplo: "Ingreso", "Devolución", etc.
  private Date fechaMovimiento;
}