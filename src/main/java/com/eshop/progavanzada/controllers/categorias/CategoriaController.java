package com.eshop.progavanzada.controllers.categorias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.progavanzada.dtos.categoria.CategoriaDTO;
import com.eshop.progavanzada.services.categorias.ICategoriaService;

@RestController
@RequestMapping("api/categorias")
public class CategoriaController {
  @Autowired
  private ICategoriaService service;

  @GetMapping
  public ResponseEntity<List<CategoriaDTO>> getAll() {
    List<CategoriaDTO> categorias = this.service.listar();
    return ResponseEntity.ok(categorias);
  }
}
