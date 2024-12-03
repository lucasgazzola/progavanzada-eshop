package com.eshop.progavanzada.services.marcas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.progavanzada.dtos.marcas.MarcaDTO;
import com.eshop.progavanzada.dtos.marcas.UpdateMarcaDTO;
import com.eshop.progavanzada.exceptions.BadRequestException;
import com.eshop.progavanzada.exceptions.DataConflictException;
import com.eshop.progavanzada.exceptions.NotFoundException;
import com.eshop.progavanzada.mappers.MarcaMapper;
import com.eshop.progavanzada.models.Marca;
import com.eshop.progavanzada.repositories.MarcaRepository;

import java.util.List;

@Service
public class MarcaService implements IMarcaService {
  @Autowired
  private MarcaRepository repository;

  @Override
  public List<MarcaDTO> listar(boolean incluirEliminados) {
    // Buscamos todas las marcas o solo las no eliminadas
    // Dependiendo de si incluirEliminados es true o false
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
    // Buscamos la marca por id
    // Si el id de la marca no existe, lanza una excepción
    Marca marca = this.repository.findById(id).orElse(null);
    if (marca == null) {
      throw new NotFoundException("La marca con id '" + id + "' no existe");
    }
    return MarcaMapper.toDTO(marca);
  }

  @Override
  public MarcaDTO crear(MarcaDTO marcaDTO) {
    // Buscamos todas las marcas
    List<Marca> marcas = this.repository.findAll();
    if (marcaDTO.getNombre() == null) {
      throw new BadRequestException("El nombre no puede ser nulo");
    }

    if (marcaDTO.getDescripcion() == null) {
      marcaDTO.setDescripcion("");
    }

    // Eliminamos espacios en blanco
    // Reemplazamos múltiples espacios intermedios por uno solo
    marcaDTO.setNombre(marcaDTO.getNombre().trim().replaceAll("\\s+", " "));

    // Si luego de eliminar los espacios en blanco, el nombre está vacío, lanza una
    // excepción
    if (marcaDTO.getNombre().equals("")) {
      throw new BadRequestException("El nombre no puede estar vacío");
    }

    // Si la descripción no es nula ni vacia, le quitamos los espaciones en blanco
    // De lo contrario, la descripción es nula
    if (marcaDTO.getDescripcion() != null) {
      if (!marcaDTO.getDescripcion().isEmpty()) {
        marcaDTO.setDescripcion(marcaDTO.getDescripcion().trim());
      } else {
        marcaDTO.setDescripcion(null);
      }
    }

    // Comparamos la marca a agregar con todas las existentes
    // La comparación se realiza con ambos nombres en minúsculas
    Marca marcaRepetida = marcas.stream()
        .filter(m -> m.getNombre().toLowerCase().equals(marcaDTO.getNombre().toLowerCase())).findFirst().orElse(null);

    // Si la marca ya existe, lanzamos una excepción
    if (marcaRepetida != null) {
      throw new DataConflictException(
          "La marca con nombre '" + marcaDTO.getNombre() + "' ya existe");
    }

    // Si no hay marca repetida, creamos la marca
    // Agregamos la marca a la lista
    Marca marca = MarcaMapper.toEntity(marcaDTO);
    this.repository.save(marca);
    return MarcaMapper.toDTO(marca);
  }

  @Override
  public MarcaDTO actualizar(Integer id, UpdateMarcaDTO marcaDTO) {
    // Si todos los campos del cuerpo de la petición son nulos, lanza una excepción
    if (marcaDTO.isEmpty())
      throw new BadRequestException("No se han especificado campos a actualizar.");

    // Buscamos la marca por id
    Marca marca = MarcaMapper.toEntity(this.buscarPorId(id));

    // Actualizamos los campos de la marca con el id especificado
    if (marcaDTO.getDescripcion() != null)
      marca.setDescripcion(marcaDTO.getDescripcion().trim());
    if (marcaDTO.getNombre() != null)
      marca.setNombre(marcaDTO.getNombre().trim());

    // Si el campo eliminado no es nulo, nos fijamos si es verdadero o falso
    // Y actualizamos el estado de la marca
    if (marcaDTO.getEliminado() != null) {
      if (marcaDTO.getEliminado()) {
        marca.eliminarLogico();
      } else {
        marca.recuperarLogico();
      }
    }

    // Buscamos todas las marcas
    List<Marca> marcas = this.repository.findAll();

    // Comparamos la marca a agregar con todas las existentes
    // Si en la marca no se actualiza el nombre, no hay que hacer la comprobación
    Marca marcaRepetida = marcaDTO.getNombre() == null ? null
        : marcas.stream()
            .filter(m -> m.getNombre().toLowerCase().equals(marcaDTO.getNombre().toLowerCase())).findFirst()
            .orElse(null);

    // Si la marca ya existe y no es la misma que la que queremos actualizar, lanza
    // una excepción
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
  public void eliminar(Integer id) {
    // Buscamos la marca por id y si no existe, lanza una excepción
    MarcaDTO marcaDTO = this.buscarPorId(id);
    if (marcaDTO == null) {
      throw new NotFoundException("El id recibido no existe: " + id);

    }

    // Si el id existe, eliminamos la marca de manera lógica
    // Luego, guardamos los cambios
    Marca marca = MarcaMapper.toEntity(marcaDTO);
    marca.eliminarLogico();
    this.repository.save(marca);
  }

  @Override
  public void recuperarEliminado(Marca marca) {
    // Recuperamos la marca setteando su estado eliminado a verdadero
    // Luego, guardamos los cambios
    marca.recuperarLogico();
    this.repository.save(marca);
  }
}