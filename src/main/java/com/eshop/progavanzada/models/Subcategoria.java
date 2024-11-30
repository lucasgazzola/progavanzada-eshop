package com.eshop.progavanzada.models;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
  private int id;

  private String nombre;

  private String descripcion;

  @ManyToOne
  @JoinColumn(name = "categoriaId", nullable = false)
  private Categoria categoria;

  @CreatedDate
  private Instant createdDate;

  @LastModifiedDate
  private Instant updatedDate;

  private boolean eliminado = false;

  public void eliminarLogico() {
    this.setEliminado(true);
  }
}
