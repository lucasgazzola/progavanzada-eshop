package com.eshop.progavanzada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.progavanzada.models.Producto;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

  List<Producto> findByEliminado(boolean esEliminado);

}