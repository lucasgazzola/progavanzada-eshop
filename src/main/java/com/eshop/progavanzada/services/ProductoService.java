package com.eshop.progavanzada.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.progavanzada.repositories.MarcaRepository;
import com.eshop.progavanzada.repositories.ProductoRepository;
import com.eshop.progavanzada.models.Marca;
import com.eshop.progavanzada.models.Producto;
import com.eshop.progavanzada.dtos.ProductoDTO;
import com.eshop.progavanzada.dtos.UpdateProductoDTO;
import com.eshop.progavanzada.mappers.ProductoMapper;
import com.eshop.progavanzada.exceptions.BadRequestException;
import com.eshop.progavanzada.exceptions.NotFoundException;

import java.util.List;

@Service
public class ProductoService implements IProductoService {
  @Autowired
  private ProductoRepository repository;

  @Autowired
  private MarcaRepository marcaRepository;

  @Override
  public List<ProductoDTO> listarProductos(boolean incluirEliminados) {
    List<Producto> productos;

    if (incluirEliminados) {
      productos = this.repository.findAll();
    } else {
      productos = this.repository.findByEliminado(false);
    }

    return productos.stream().map(ProductoMapper::toDTO).toList();
  }

  @Override
  public ProductoDTO buscarPorId(Integer id) {
    Producto producto = this.repository.findById(id)
        .orElse(null);

    if (producto == null) {
      throw new NotFoundException("Producto con id " + id + " no encontrado");
    }

    return ProductoMapper.toDTO(producto);
  }

  @Override
  public ProductoDTO crearProducto(ProductoDTO productoDTO) {
    // Eliminamos los espacios en blanco
    // Reemplazamos múltiples espacios intermedios por uno solo
    productoDTO.setNombre(productoDTO.getNombre().trim().replaceAll("\\s+", " "));

    if (productoDTO.getNombre().equals("")) {
      throw new BadRequestException("El nombre no puede estar vacio");
    }
    if (productoDTO.getDescripcion() != null) {
      if (!productoDTO.getDescripcion().isEmpty()) {
        productoDTO.setDescripcion(productoDTO.getDescripcion().trim());
      } else {
        productoDTO.setDescripcion(null);
      }
    }

    if (productoDTO.getMarca().getId() == null) {
      throw new BadRequestException("La marca no puede estar vacia");
    }
    Marca marca = this.marcaRepository.findById(productoDTO.getMarca().getId()).orElse(null);
    if (marca == null) {
      throw new NotFoundException("La marca con id " + productoDTO.getMarca().getId() + " no existe");
    }

    // Mapeamos el DTO a una instancia de Entity.
    Producto producto = ProductoMapper.toEntity(productoDTO);

    // Agregamos la marca a la instancia de Entity.
    producto.setMarca(marca);

    Producto nuevoProducto = this.repository.save(producto);
    return ProductoMapper.toDTO(nuevoProducto);
  }

  @Override
  public ProductoDTO actualizarProducto(Integer id, UpdateProductoDTO productoDTO) {
    // Si todos los campos son nulos, lanza una excepción
    if (productoDTO.isEmpty()) {
      throw new BadRequestException("No se han especificado campos a actualizar.");
    }

    Producto producto = ProductoMapper.toEntity(this.buscarPorId(id));

    if (producto == null) {
      throw new NotFoundException("El producto con id '" + id + "' no existe");
    }

    // Actualizar los campos.
    if (productoDTO.getDescripcion() != null)
      producto.setDescripcion(productoDTO.getDescripcion().trim());
    if (productoDTO.getNombre() != null)
      producto.setNombre(productoDTO.getNombre().trim());
    if (productoDTO.getPrecio() != null) {
      producto.setPrecio(productoDTO.getPrecio());
    }
    if (productoDTO.getMarca() != null) {
      Marca marca = this.marcaRepository.findById(productoDTO.getMarca().getId())
          .orElseThrow(
              () -> new NotFoundException("La marca con id '" + productoDTO.getMarca().getId() + "' no existe"));
      producto.setMarca(marca);
    }
    if (productoDTO.getEliminado() != null) {
      if (productoDTO.getEliminado()) {
        producto.eliminarLogico();
      } else {
        producto.recuperarLogico();
      }
    }
    // Guardar los cambios en la base de datos.
    this.repository.save(producto);

    // Utilizar ProductoMapper para mapear el producto a DTO.
    return ProductoMapper.toDTO(producto);
  }

  @Override
  public void eliminarProducto(Integer id) {
    ProductoDTO productoDTO = this.buscarPorId(id);
    if (productoDTO == null) {
      throw new NotFoundException("El id " + id + " recibido no existe");

    }
    Producto producto = ProductoMapper.toEntity(productoDTO);
    producto.eliminarLogico();
    this.repository.save(producto);
  }

  @Override
  public void recuperarProducto(Producto producto) {
    producto.recuperarLogico();
    this.repository.save(producto);
  }
}