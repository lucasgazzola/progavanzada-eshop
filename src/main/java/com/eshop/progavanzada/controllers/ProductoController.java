package com.eshop.progavanzada.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eshop.progavanzada.services.IProductoService;
import com.eshop.progavanzada.models.Producto;
import com.eshop.progavanzada.dtos.ProductoDTO;
import com.eshop.progavanzada.mappers.ProductoMapper;
import com.eshop.progavanzada.exceptions.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("api/productos")
public class ProductoController {

  @Autowired
  private IProductoService productoService;

  @GetMapping
  public List<ProductoDTO> getAll() {
    return productoService.listar();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductoDTO> getProducto(@PathVariable Integer id) {
    ProductoDTO productoDTO = productoService.buscarPorId(id);
    if (productoDTO == null) {
      throw new NotFoundException("El producto no existe con id: " + id);
    }
    return ResponseEntity.ok(productoDTO);
  }

  @PostMapping
  public ProductoDTO guardar(@Validated @RequestBody ProductoDTO productoDTO) {

    return productoService.guardar(productoDTO);
  }

  @PutMapping
  public ProductoDTO actualizar(@RequestBody ProductoDTO productoDTO) {
    return productoService.guardar(productoDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
    ProductoDTO productoDTO = productoService.buscarPorId(id);
    if (productoDTO == null) {
      throw new NotFoundException("El id recibido no existe: " + id);
    }
    Producto producto = ProductoMapper.toEntity(productoDTO);
    producto.eliminarLogico();
    productoService.eliminar(id);
    return ResponseEntity.ok().build();
  }
}