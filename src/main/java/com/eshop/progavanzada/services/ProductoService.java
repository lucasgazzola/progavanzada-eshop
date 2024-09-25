package com.eshop.progavanzada.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.progavanzada.repositories.MarcaRepository;
import com.eshop.progavanzada.repositories.ProductoRepository;
import com.eshop.progavanzada.models.Marca;
import com.eshop.progavanzada.models.Producto;
import com.eshop.progavanzada.dtos.ProductoDTO;
import com.eshop.progavanzada.mappers.ProductoMapper;
import com.eshop.progavanzada.exceptions.NotFoundException;

import java.util.List;

@Service
public class ProductoService implements IProductoService {
  @Autowired
  private ProductoRepository productoRepository;
  @Autowired
  private MarcaRepository marcaRepository;

  @Override
  public List<ProductoDTO> listar() {
    List<Producto> productos = productoRepository.findByEliminado(false);
    return productos.stream().map(ProductoMapper::toDTO).toList();
  }

  @Override
  public ProductoDTO buscarPorId(Integer id) {
    Producto producto = productoRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Producto no encontrada con id: " + id));
    return ProductoMapper.toDTO(producto);
  }

  @Override
  public ProductoDTO guardar(ProductoDTO productoDTO) {
    Marca marca = marcaRepository.findById(productoDTO.getMarcaId())
        .orElseThrow(
            () -> new NotFoundException("Marca no encontrada con id: " + productoDTO.getMarcaId()));
    Producto producto = ProductoMapper.toEntity(productoDTO);
    producto.setMarca(marca);
    Producto nuevoProducto = productoRepository.save(producto);
    return ProductoMapper.toDTO(nuevoProducto);
  }

  @Override
  public void eliminar(Integer id) {
    Producto producto = productoRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Producto no encontrada con id: " + id));
    producto.eliminarLogico();
    productoRepository.save(producto);
  }
}