package com.eshop.progavanzada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.progavanzada.models.Perfil;
import com.eshop.progavanzada.models.User;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
  Perfil findByUser(User user);
}