package com.eshop.progavanzada.services.perfil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.progavanzada.dtos.perfil.PerfilDTO;
import com.eshop.progavanzada.dtos.perfil.UpdatePerfilDTO;
import com.eshop.progavanzada.exceptions.BadRequestException;
import com.eshop.progavanzada.mappers.perfil.PerfilMapper;
import com.eshop.progavanzada.models.Perfil;
import com.eshop.progavanzada.repositories.PerfilRepository;

@Service
public class PerfilService implements IPerfilService {
  @Autowired
  private PerfilRepository repository;

  @Override
  public List<PerfilDTO> listar(boolean incluirEliminados) {
    List<Perfil> perfiles = this.repository.findAll();
    if (incluirEliminados) {
      perfiles = this.repository.findAll();
    } else {
      perfiles = this.repository.findByEliminado(false);
    }
    return perfiles.stream().map(PerfilMapper::toDTO).toList();
  }

  @Override
  public PerfilDTO buscarPorId(Integer id) {
    Perfil perfil = this.repository.findById(id).orElse(null);
    if (perfil == null) {
      throw new BadRequestException("El perfil con id " + id + " no existe");
    }
    return PerfilMapper.toDTO(perfil);
  }

  @Override
  public PerfilDTO crear(PerfilDTO perfilDTO) {
    Perfil perfil = PerfilMapper.toModel(perfilDTO);
    return PerfilMapper.toDTO(this.repository.save(perfil));
  }

  @Override
  public PerfilDTO actualizar(UpdatePerfilDTO updatePerfilDTO) {
    if (updatePerfilDTO.getId() == null || updatePerfilDTO.getId() <= 0) {
      throw new BadRequestException("El id del perfil es obligatorio");
    }
    Perfil perfil = PerfilMapper.toModel(this.buscarPorId(updatePerfilDTO.getId()));
    if (updatePerfilDTO.getNombre() != null) {
      perfil.setNombre(updatePerfilDTO.getNombre());
    }
    if (updatePerfilDTO.getDireccion() != null) {
      perfil.setDireccion(updatePerfilDTO.getDireccion());
    }
    if (updatePerfilDTO.getTelefono() != null) {
      perfil.setTelefono(updatePerfilDTO.getTelefono());
    }
    if (updatePerfilDTO.getEliminado() != null) {
      if (updatePerfilDTO.getEliminado()) {
        perfil.eliminarLogico();
      } else {
        perfil.recuperarLogico();
      }
    }
    return PerfilMapper.toDTO(this.repository.save(perfil));
  }

  @Override
  public void eliminar(Integer id) {
    Perfil perfil = this.repository.findById(id).orElse(null);
    if (perfil == null) {
      throw new BadRequestException("El perfil no existe");
    }
    perfil.setEliminado(true);
    this.repository.save(perfil);
  }
}
