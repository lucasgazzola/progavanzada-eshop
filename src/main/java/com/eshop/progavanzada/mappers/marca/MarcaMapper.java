package com.eshop.progavanzada.mappers.marca;

import org.springframework.stereotype.Component;

import com.eshop.progavanzada.dtos.marcas.MarcaDTO;
import com.eshop.progavanzada.models.Marca;

@Component
public class MarcaMapper {

  public static MarcaDTO toDTO(Marca marca) {
    MarcaDTO marcaDTO = new MarcaDTO();
    marcaDTO.setId(marca.getId());
    marcaDTO.setNombre(marca.getNombre());
    marcaDTO.setDescripcion(marca.getDescripcion());
    marcaDTO.setEliminado(marca.isEliminado());
    return marcaDTO;
  }

  public static Marca toModel(MarcaDTO marcaDTO) {
    Marca marca = new Marca();
    marca.setId(marcaDTO.getId());
    marca.setDescripcion(marcaDTO.getDescripcion());
    marca.setNombre(marcaDTO.getNombre());

    // Si el campo eliminado no es nulo, lo setteamos
    // De lo contrario, el campo eliminado es falso por defecto
    if (marcaDTO.getEliminado() == null) {
      marca.setEliminado(false);
    } else {
      marca.setEliminado(marcaDTO.getEliminado());
    }
    return marca;
  }
}