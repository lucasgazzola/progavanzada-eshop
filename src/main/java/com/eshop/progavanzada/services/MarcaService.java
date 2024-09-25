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
  public List<MarcaDTO> listarMarcas() {
    List<Marca> marcas = this.repository.findByEliminado(false);
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
    List<Marca> marcas = this.repository.findByEliminado(false);

    // Eliminamos espacios en Blanco
    marcaDTO.setNombre(marcaDTO.getNombre().trim());
    if (marcaDTO.getDescripcion() != null) {
      if (!marcaDTO.getDescripcion().isEmpty()) {
        marcaDTO.setDescripcion(marcaDTO.getDescripcion().trim());
      } else {
        marcaDTO.setDescripcion(null);
      }
    }

    if (marcas.stream().anyMatch(m -> m.getNombre().toLowerCase().equals(marcaDTO.getNombre().toLowerCase()))) {
      throw new DataConflictException(
          "La marca con nombre '" + marcaDTO.getNombre() + "' ya existe");
    }

    // Validar si la marca ya existe
    if (this.repository.existsByNombre(marcaDTO.getNombre())) {
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
    if (marcaDTO.isEmpty())
      throw new BadRequestException("No se han especificado campos a actualizar.");

    Marca marca = MarcaMapper.toEntity(this.buscarPorId(id));

    // Actualizar los campos.
    if (marcaDTO.getDescripcion() != null)
      marca.setDescripcion(marcaDTO.getDescripcion().trim());
    if (marcaDTO.getNombre() != null)
      marca.setNombre(marcaDTO.getNombre().trim());

    // Guardar los cambios en la base de datos.
    marca = this.repository.save(marca);

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
  public void recuperarMarca(Integer id) {
    // Buscamos todas las marcas eliminadas
    List<Marca> marcas = this.repository.findByEliminado(true);

    // Buscamos la marca con el id
    // Si la marca no existe, lanza una excepción
    Marca marca = marcas.stream().filter(m -> m.getId().equals(id)).findFirst()
        .orElseThrow(() -> new NotFoundException("La marca con id '" + id + "' no existe"));

    // Recuperamos la marca
    marca.recuperarLogico();
    this.repository.save(marca);
  }
}