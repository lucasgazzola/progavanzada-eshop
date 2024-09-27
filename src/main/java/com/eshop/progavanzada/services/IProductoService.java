package com.eshop.progavanzada.services;

import jakarta.persistence.ElementCollection;

import java.util.List;

import com.eshop.progavanzada.dtos.ProductoDTO;
import com.eshop.progavanzada.dtos.UpdateProductoDTO;
import com.eshop.progavanzada.models.Producto;

public interface IProductoService {

  @ElementCollection(targetClass = Integer.class)
  public List<ProductoDTO> listarProductos();

  public ProductoDTO buscarPorId(Integer id);

  public ProductoDTO crearProducto(ProductoDTO marcaDTO);

  public ProductoDTO actualizarProducto(Integer id, UpdateProductoDTO marcaDTO);

  public void eliminarProducto(Integer id);

  public void recuperarProducto(Producto marca);
}
