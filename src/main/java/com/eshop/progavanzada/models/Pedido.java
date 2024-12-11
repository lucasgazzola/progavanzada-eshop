package com.eshop.progavanzada.models;

import java.util.List;

import com.eshop.progavanzada.enums.EstadoPedido;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  // Relación con Usuario
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private EstadoPedido estado;

  // Agregación
  public void agregarDetallePedido(DetallePedido detallePedido) {
    this.detallesPedido.add(detallePedido);
  }

  public void eliminarDetallePedido(DetallePedido detallePedido) {
    this.detallesPedido.remove(detallePedido);
  }
}
