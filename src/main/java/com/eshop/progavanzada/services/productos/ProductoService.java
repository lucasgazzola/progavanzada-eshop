package com.eshop.progavanzada.services.productos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.progavanzada.repositories.MarcaRepository;
import com.eshop.progavanzada.repositories.ProductoRepository;
import com.eshop.progavanzada.models.Marca;
import com.eshop.progavanzada.models.Producto;
import com.eshop.progavanzada.mappers.ProductoMapper;
import com.eshop.progavanzada.dtos.productos.ProductoDTO;
import com.eshop.progavanzada.dtos.productos.UpdateProductoDTO;
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
    // Buscamos todas los productos o solo los no eliminados
    // Dependiendo de si incluirEliminados es true o false
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
    // Buscamos el producto por id
    // Si el id del producto no existe, lanza una excepción
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

    // Si luego de eliminar los espacios en blanco, el nombre está vacío
    // Lanza una excepción
    if (productoDTO.getNombre().equals("")) {
      throw new BadRequestException("El nombre no puede estar vacio");
    }

    // Si la descripción no es nula ni vacia, le quitamos los espaciones en blanco
    // De lo contrario, la descripción es nula
    if (productoDTO.getDescripcion() != null) {
      if (!productoDTO.getDescripcion().isEmpty()) {
        productoDTO.setDescripcion(productoDTO.getDescripcion().trim());
      } else {
        productoDTO.setDescripcion(null);
      }
    }

    // Si el id de la marca del producto es nula o no existe en absoluto
    // Lanza una excepción
    if (productoDTO.getMarca() == null) {
      throw new BadRequestException("La marca no puede estar vacia");
    }
    if (productoDTO.getMarca().getId() == null) {
      throw new BadRequestException("La marca no puede estar vacia");
    }

    // Buscamos la marca por id
    // Si el id de la marca no existe, lanza una excepción
    Marca marca = this.marcaRepository.findById(productoDTO.getMarca().getId()).orElse(null);
    if (marca == null) {
      throw new NotFoundException("La marca con id " + productoDTO.getMarca().getId() + " no existe");
    }

    // Mapeamos el DTO a una instancia de Entity.
    Producto producto = ProductoMapper.toEntity(productoDTO);

    // Agregamos la marca a la instancia de Entity.
    producto.setMarca(marca);

    // Guardamos el producto en la base de datos.
    Producto nuevoProducto = this.repository.save(producto);
    return ProductoMapper.toDTO(nuevoProducto);
  }

  @Override
  public ProductoDTO actualizarProducto(Integer id, UpdateProductoDTO productoDTO) {
    // Si todos los campos son nulos, lanza una excepción
    if (productoDTO.isEmpty()) {
      throw new BadRequestException("No se han especificado campos a actualizar.");
    }

    // Buscamos el producto por id
    // Si el id del producto no existe, lanza una excepción
    Producto producto = ProductoMapper.toEntity(this.buscarPorId(id));
    if (producto == null) {
      throw new NotFoundException("El producto con id '" + id + "' no existe");
    }

    // Actualizar los campos del producto con el id especificado eliminando espacios
    // adicionales
    if (productoDTO.getDescripcion() != null)
      producto.setDescripcion(productoDTO.getDescripcion().trim());
    if (productoDTO.getNombre() != null)
      producto.setNombre(productoDTO.getNombre().trim());
    if (productoDTO.getPrecio() != null) {
      producto.setPrecio(productoDTO.getPrecio());
    }

    // Si el campo marca no es nulo
    // Buscamos la marca por id
    // Si el id de la marca no existe, lanza una excepción
    // De lo contrario actualizamos el campo marca
    if (productoDTO.getMarca() != null) {
      Marca marca = this.marcaRepository.findById(productoDTO.getMarca().getId())
          .orElseThrow(
              () -> new NotFoundException("La marca con id '" + productoDTO.getMarca().getId() + "' no existe"));
      producto.setMarca(marca);
    }

    // Si el campo eliminado no es nulo, nos fijamos si es verdadero o falso
    // Y actualizamos el estado del producto
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
    // Buscamos el producto por id
    // Si el id del producto no existe, lanza una excepción
    ProductoDTO productoDTO = this.buscarPorId(id);
    if (productoDTO == null) {
      throw new NotFoundException("El id " + id + " recibido no existe");

    }

    // Si el id existe, eliminamos el producto de manera lógica
    // Luego, guardamos los cambios
    Producto producto = ProductoMapper.toEntity(productoDTO);
    producto.eliminarLogico();
    this.repository.save(producto);
  }

  @Override
  public void recuperarProducto(Producto producto) {
    // Recuperamos el producto setteando su estado eliminado a verdadero
    // Luego, guardamos los cambios
    producto.recuperarLogico();
    this.repository.save(producto);
  }
}