package com.eshop.progavanzada.mappers;

import com.eshop.progavanzada.dtos.categoria.CategoriaDTO;
import com.eshop.progavanzada.models.Categoria;

public class CategoriaMapper {
  public static CategoriaDTO toDTO(Categoria categoria) {
    CategoriaDTO dto = new CategoriaDTO();
    dto.setId(categoria.getId());
    dto.setNombre(categoria.getNombre());
    dto.setDescripcion(categoria.getDescripcion());
    dto.setEliminado(categoria.getEliminado());
    return dto;
  }

  public static Categoria toModel(CategoriaDTO dto) {
    Categoria categoria = new Categoria();
    categoria.setId(dto.getId());
    categoria.setNombre(dto.getNombre());
    categoria.setDescripcion(dto.getDescripcion());
    categoria.setEliminado(dto.getEliminado());
    return categoria;
  }
}
