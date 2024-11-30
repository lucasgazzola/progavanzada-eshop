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
public class Perfil {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String nombre;

  private String direccion;

  private String telefono;

  @OneToOne
  @JoinColumn(name = "userId")
  private User user;

  private boolean eliminado = false;

  public void eliminarLogico() {
    this.setEliminado(true);
  }

  public void recuperarLogico() {
    this.setEliminado(false);
  }
}
