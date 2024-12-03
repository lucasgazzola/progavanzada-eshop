package com.eshop.progavanzada.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Categoria {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String nombre;
  private String descripcion;

  private Boolean eliminado = false;

  @OneToMany(mappedBy = "categoria")
  private List<Subcategoria> subcategorias;

  public void eliminarLogico() {
    this.setEliminado(true);
  }

  public void recuperarLogico() {
    this.setEliminado(false);
  }
}
