package com.eshop.progavanzada.services.categorias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.progavanzada.dtos.categoria.CategoriaDTO;
import com.eshop.progavanzada.mappers.CategoriaMapper;
import com.eshop.progavanzada.models.Categoria;
import com.eshop.progavanzada.repositories.CategoriaRepository;

@Service
public class CategoriaService implements ICategoriaService {
  @Autowired
  private CategoriaRepository repository;

  @Override
  public List<CategoriaDTO> listar() {
    List<Categoria> categorias = this.repository.findAll();
    return categorias.stream().map(CategoriaMapper::toDTO).toList();
  }
}
