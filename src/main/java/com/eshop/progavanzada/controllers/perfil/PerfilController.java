package com.eshop.progavanzada.controllers.perfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eshop.progavanzada.dtos.perfil.PerfilDTO;
import com.eshop.progavanzada.dtos.perfil.UpdatePerfilDTO;
import com.eshop.progavanzada.services.perfil.IPerfilService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/perfiles")
public class PerfilController {

  @Autowired
  private IPerfilService service;

  @GetMapping
  public ResponseEntity<List<PerfilDTO>> getAll() {
    List<PerfilDTO> perfiles = this.service.listar(false);
    return ResponseEntity.ok(perfiles);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PerfilDTO> getPerfilById(@PathVariable Integer id) {
    PerfilDTO dto = this.service.buscarPorId(id);
    return ResponseEntity.ok(dto);
  }

  @PostMapping
  public ResponseEntity<PerfilDTO> createPerfil(@Valid @RequestBody PerfilDTO perfilDTO) {
    PerfilDTO dto = this.service.crear(perfilDTO);
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PerfilDTO> updatePerfil(@PathVariable Integer id,
      @Valid @RequestBody UpdatePerfilDTO perfilDTO) {
    PerfilDTO dto = this.service.actualizar(perfilDTO);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
    this.service.eliminar(id);
    return ResponseEntity.ok().build();
  }

}
