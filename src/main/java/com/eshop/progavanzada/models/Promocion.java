package com.eshop.progavanzada.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Promocion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre; // Nombre descriptivo de la promoción
  private String tipoDescuento; // "PORCENTAJE" o "FIJO"
  private double valorDescuento; // Ejemplo: 20% o $10
  private Date fechaInicio;
  private Date fechaFin;

  @ManyToMany
  @JoinTable(name = "promocion_producto")
  private List<Producto> productos;
}
