package com.eshop.progavanzada.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.progavanzada.models.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
  List<Perfil> findByEliminado(boolean esEliminado);
}