package com.eshop.progavanzada.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.progavanzada.dtos.MarcaDTO;
import com.eshop.progavanzada.dtos.UpdateMarcaDTO;
import com.eshop.progavanzada.exceptions.BadRequestException;
import com.eshop.progavanzada.exceptions.DataConflictException;
import com.eshop.progavanzada.exceptions.NotFoundException;
import com.eshop.progavanzada.mappers.MarcaMapper;
import com.eshop.progavanzada.models.Marca;
import com.eshop.progavanzada.repositories.MarcaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService implements IMarcaService {
  @Autowired
  private MarcaRepository repository;

  @Override
  public List<MarcaDTO> listarMarcas(boolean incluirEliminados) {
    List<Marca> marcas;
    if (incluirEliminados) {
      marcas = this.repository.findAll();
    } else {
      marcas = this.repository.findByEliminado(false);
    }
    return marcas.stream().map(MarcaMapper::toDTO).toList();
  }

  @Override
  public MarcaDTO buscarPorId(Integer id) {
    Optional<Marca> marca = this.repository.findById(id);

    if (marca.isEmpty()) {
      throw new NotFoundException("La marca con id '" + id + "' no existe");
    }

    return MarcaMapper.toDTO(marca.get());
  }

  @Override
  public MarcaDTO crearMarca(MarcaDTO marcaDTO) {
    // Buscamos todas las marcas no eliminadas
    List<Marca> marcas = this.repository.findAll();

    // Eliminamos espacios en blanco
    // Reemplazamos múltiples espacios intermedios por uno solo
    marcaDTO.setNombre(marcaDTO.getNombre().trim().replaceAll("\\s+", " "));

    if (marcaDTO.getNombre().equals("")) {
      throw new BadRequestException("El nombre no puede estar vacío");
    }
    if (marcaDTO.getDescripcion() != null) {
      if (!marcaDTO.getDescripcion().isEmpty()) {
        marcaDTO.setDescripcion(marcaDTO.getDescripcion().trim());
      } else {
        marcaDTO.setDescripcion(null);
      }
    }

    Marca marcaRepetida = marcas.stream()
        .filter(m -> m.getNombre().toLowerCase().equals(marcaDTO.getNombre().toLowerCase())).findFirst().orElse(null);

    // Si la marca no existe, la creamos
    if (marcaRepetida != null) {
      throw new DataConflictException(
          "La marca con nombre '" + marcaDTO.getNombre() + "' ya existe");
    }

    Marca marca = MarcaMapper.toEntity(marcaDTO);
    this.repository.save(marca);
    return MarcaMapper.toDTO(marca);
  }

  @Override
  public MarcaDTO actualizarMarca(Integer id, UpdateMarcaDTO marcaDTO) {
    // Si todos los campos son nulos, lanza una excepción
    // if (marcaDTO.isEmpty() && marcaDTO.isEliminado())
    // throw new BadRequestException("No se han especificado campos a actualizar.");

    Marca marca = MarcaMapper.toEntity(this.buscarPorId(id));

    // Actualizar los campos.
    if (marcaDTO.getDescripcion() != null)
      marca.setDescripcion(marcaDTO.getDescripcion().trim());
    if (marcaDTO.getNombre() != null)
      marca.setNombre(marcaDTO.getNombre().trim());
    if (marcaDTO.isEliminado()) {
      marca.eliminarLogico();
    } else {
      marca.recuperarLogico();
    }

    List<Marca> marcas = this.repository.findAll();
    Marca marcaRepetida = marcas.stream()
        .filter(m -> m.getNombre().toLowerCase().equals(marcaDTO.getNombre().toLowerCase())).findFirst().orElse(null);
    if (marcaRepetida != null && !marcaRepetida.getId().equals(marca.getId())) {
      throw new DataConflictException(
          "La marca con nombre '" + marcaDTO.getNombre() + "' ya existe");
    }
    // Guardar los cambios en la base de datos.
    this.repository.save(marca);

    // Utilizar MarcaMapper para mapear la marca a DTO.
    return MarcaMapper.toDTO(marca);
  }

  @Override
  public void eliminarMarca(Integer id) {
    MarcaDTO marcaDTO = this.buscarPorId(id);
    if (marcaDTO == null) {
      throw new NotFoundException("El id recibido no existe: " + id);

    }
    Marca marca = MarcaMapper.toEntity(marcaDTO);
    marca.eliminarLogico();
    this.repository.save(marca);
  }

  @Override
  public void recuperarMarca(Marca marca) {
    marca.recuperarLogico();
    this.repository.save(marca);
  }
}