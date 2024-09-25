package com.eshop.progavanzada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.progavanzada.models.Marca;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {

  List<Marca> findByEliminado(boolean esEliminado);

  Marca findByNombre(String nombre);

  boolean existsByNombre(String nombre);
}