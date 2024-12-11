package com.eshop.progavanzada.mappers.perfil;

import com.eshop.progavanzada.dtos.perfil.PerfilDTO;
import com.eshop.progavanzada.models.Perfil;

public class PerfilMapper {
  public static PerfilDTO toDTO(Perfil perfil) {
    PerfilDTO dto = new PerfilDTO();
    dto.setId(perfil.getId());
    dto.setUser(perfil.getUser());
    dto.setNombre(perfil.getNombre());
    dto.setDireccion(perfil.getDireccion());
    dto.setTelefono(perfil.getTelefono());
    return dto;
  }

  public static Perfil toModel(PerfilDTO dto) {
    Perfil perfil = new Perfil();
    perfil.setId(dto.getId());
    perfil.setUser(dto.getUser());
    perfil.setNombre(dto.getNombre());
    perfil.setDireccion(dto.getDireccion());
    perfil.setTelefono(dto.getTelefono());
    return perfil;
  }
}
