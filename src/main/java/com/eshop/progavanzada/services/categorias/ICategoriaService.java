package com.eshop.progavanzada.services.categorias;

import java.util.List;

import com.eshop.progavanzada.dtos.categoria.CategoriaDTO;

public interface ICategoriaService {
  List<CategoriaDTO> listar();
}
