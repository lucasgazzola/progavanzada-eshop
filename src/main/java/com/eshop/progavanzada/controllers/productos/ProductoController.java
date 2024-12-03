package com.eshop.progavanzada.controllers.productos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eshop.progavanzada.dtos.productos.ProductoDTO;
import com.eshop.progavanzada.dtos.productos.UpdateProductoDTO;
import com.eshop.progavanzada.services.productos.IProductoService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("api/productos")
public class ProductoController {

  @Autowired
  private IProductoService service;

  @GetMapping
  public ResponseEntity<List<ProductoDTO>> getAll(
      @RequestParam(value = "incluirEliminados", required = false, defaultValue = "false") boolean incluirEliminados) {
    List<ProductoDTO> productos = this.service.listarProductos(incluirEliminados);
    return ResponseEntity.ok(productos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductoDTO> getProducto(@PathVariable Integer id) {
    ProductoDTO dto = this.service.buscarPorId(id);
    return ResponseEntity.ok(dto);
  }

  @PostMapping
  public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
    ProductoDTO dto = this.service.crearProducto(productoDTO);
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Integer id,
      @Valid @RequestBody UpdateProductoDTO productoDTO) {
    ProductoDTO dto = this.service.actualizarProducto(id, productoDTO);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
    this.service.eliminarProducto(id);
    return ResponseEntity.ok().build();
  }
}