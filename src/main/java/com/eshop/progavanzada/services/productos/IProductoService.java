package com.eshop.progavanzada.services.productos;

import jakarta.persistence.ElementCollection;

import java.util.List;

import com.eshop.progavanzada.dtos.productos.ProductoDTO;
import com.eshop.progavanzada.dtos.productos.UpdateProductoDTO;
import com.eshop.progavanzada.models.Producto;

public interface IProductoService {

  @ElementCollection(targetClass = Integer.class)
  public List<ProductoDTO> listarProductos(boolean incluirEliminados);

  public ProductoDTO buscarPorId(Integer id);

  public ProductoDTO crearProducto(ProductoDTO prodcutoDTO);

  public ProductoDTO actualizarProducto(Integer id, UpdateProductoDTO prodcutoDTO);

  public void eliminarProducto(Integer id);

  public void recuperarProducto(Producto marca);
}
