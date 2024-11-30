package com.eshop.progavanzada.models;

import java.util.List;

import com.eshop.progavanzada.enums.EstadoPedido;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Pedido {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String fecha;

  @OneToMany(mappedBy = "pedido")
  private List<DetallePedido> detallesPedido;

  private User user;

  private EstadoPedido estado;

  private boolean eliminado = false;

  // Agregación
  public void agregarDetallePedido(DetallePedido detallePedido) {
    this.detallesPedido.add(detallePedido);
  }

  public void eliminarDetallePedido(DetallePedido detallePedido) {
    this.detallesPedido.remove(detallePedido);
  }

  public void eliminarLogico() {
    this.setEliminado(true);
  }

  public void recuperarLogico() {
    this.setEliminado(false);
  }

}
