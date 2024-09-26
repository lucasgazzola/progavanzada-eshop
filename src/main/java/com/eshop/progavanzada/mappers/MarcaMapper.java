package com.eshop.progavanzada.mappers;

import org.springframework.stereotype.Component;

import com.eshop.progavanzada.dtos.MarcaDTO;
import com.eshop.progavanzada.models.Marca;

@Component
public class MarcaMapper {

  public static MarcaDTO toDTO(Marca marca) {
    MarcaDTO marcaDTO = new MarcaDTO();
    marcaDTO.setId(marca.getId());
    marcaDTO.setNombre(marca.getNombre());
    marcaDTO.setDescripcion(marca.getDescripcion());
    // marcaDTO.setEliminado(marca.isEliminado());
    return marcaDTO;
  }

  public static Marca toEntity(MarcaDTO marcaDTO) {
    Marca marca = new Marca();
    marca.setId(marcaDTO.getId());
    marca.setDescripcion(marcaDTO.getDescripcion());
    marca.setNombre(marcaDTO.getNombre());
    // marca.setEliminado(marcaDTO.isEliminado());
    return marca;
  }
}