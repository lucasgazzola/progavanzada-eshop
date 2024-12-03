package com.eshop.progavanzada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.progavanzada.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
