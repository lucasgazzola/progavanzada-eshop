package com.eshop.progavanzada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.progavanzada.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
