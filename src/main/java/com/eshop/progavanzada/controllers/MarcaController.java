package com.eshop.progavanzada.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eshop.progavanzada.dtos.MarcaDTO;
import com.eshop.progavanzada.dtos.UpdateMarcaDTO;
import com.eshop.progavanzada.services.IMarcaService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("api/marcas")
public class MarcaController {

  @Autowired
  private IMarcaService service;

  @GetMapping
  public ResponseEntity<List<MarcaDTO>> getAll(
      @RequestParam(value = "incluirEliminados", required = false, defaultValue = "false") boolean incluirEliminados) {
    List<MarcaDTO> marcas = this.service.listarMarcas(incluirEliminados);
    return ResponseEntity.ok(marcas);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MarcaDTO> getMarcaById(@PathVariable Integer id) {
    MarcaDTO dto = this.service.buscarPorId(id);
    return ResponseEntity.ok(dto);
  }

  @PostMapping
  public ResponseEntity<MarcaDTO> createMarca(@Valid @RequestBody MarcaDTO marcaDTO) {
    MarcaDTO dto = this.service.crearMarca(marcaDTO);
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MarcaDTO> updateMarca(@PathVariable Integer id, @Valid @RequestBody UpdateMarcaDTO marcaDTO) {
    MarcaDTO dto = this.service.actualizarMarca(id, marcaDTO);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
    this.service.eliminarMarca(id);
    return ResponseEntity.ok().build();
  }

}