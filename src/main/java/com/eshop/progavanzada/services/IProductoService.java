package com.eshop.progavanzada.services;

import jakarta.persistence.ElementCollection;

import java.util.List;

import com.eshop.progavanzada.dtos.ProductoDTO;

public interface IProductoService {

  @ElementCollection(targetClass = Integer.class)
  public List<ProductoDTO> listar();

  public ProductoDTO buscarPorId(Integer id);

  public ProductoDTO guardar(ProductoDTO productoDTO);

  public void eliminar(Integer id);
}
