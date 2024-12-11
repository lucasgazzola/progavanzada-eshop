package com.eshop.progavanzada.models;

import java.util.List;

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
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

  @ManyToOne
  @JoinColumn(name = "subcategoria_id")
  private Subcategoria subcategoria;

  private List<String> imagenes;

  @OneToMany(mappedBy = "producto")
  private List<VarianteProducto> variantesProducto;

  @ManyToMany
  @JoinTable(name = "promocion_producto")
  private List<Promocion> promociones;

  @ManyToOne
  @JoinColumn(name = "marca_id", nullable = false)
  private Marca marca;

  private boolean eliminado = false;

  public void eliminarLogico() {
    this.setEliminado(true);
  }

  public void recuperarLogico() {
    this.setEliminado(false);
  }
}