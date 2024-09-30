package com.eshop.progavanzada.mappers;

import org.springframework.stereotype.Component;

import com.eshop.progavanzada.dtos.MarcaDTO;
import com.eshop.progavanzada.dtos.ProductoDTO;
import com.eshop.progavanzada.models.Producto;

@Component
public class ProductoMapper {

  public static ProductoDTO toDTO(Producto producto) {
    ProductoDTO productoDTO = new ProductoDTO();
    productoDTO.setId(producto.getId());
    productoDTO.setNombre(producto.getNombre());
    productoDTO.setDescripcion(producto.getDescripcion());
    productoDTO.setEliminado(producto.isEliminado());
    productoDTO.setPrecio(producto.getPrecio());
    // Convertir Marca a MarcaDTO
    MarcaDTO marcaDTO = new MarcaDTO();
    marcaDTO.setId(producto.getMarca().getId());
    marcaDTO.setNombre(producto.getMarca().getNombre());
    marcaDTO.setDescripcion(producto.getMarca().getDescripcion());
    marcaDTO.setEliminado(producto.getMarca().isEliminado());
    productoDTO.setMarca(marcaDTO); // Asignar MarcaDTO al ProductoDTO
    return productoDTO;
  }

  public static Producto toEntity(ProductoDTO productoDTO) {
    Producto producto = new Producto();
    producto.setId(productoDTO.getId());
    producto.setDescripcion(productoDTO.getDescripcion());
    producto.setNombre(productoDTO.getNombre());
    if (productoDTO.getEliminado() == null) {
      producto.setEliminado(false);
    } else {
      producto.setEliminado(productoDTO.getEliminado());
    }
    producto.setPrecio(productoDTO.getPrecio());
    producto.setMarca(MarcaMapper.toEntity(productoDTO.getMarca()));
    return producto;
  }
}