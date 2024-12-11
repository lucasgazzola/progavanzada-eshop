package com.eshop.progavanzada.services.perfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.progavanzada.dtos.perfil.PerfilDTO;
import com.eshop.progavanzada.dtos.perfil.UpdatePerfilDTO;
import com.eshop.progavanzada.exceptions.BadRequestException;
import com.eshop.progavanzada.mappers.perfil.PerfilMapper;
import com.eshop.progavanzada.models.Perfil;
import com.eshop.progavanzada.models.User;
import com.eshop.progavanzada.repositories.PerfilRepository;
import com.eshop.progavanzada.repositories.UserRepository;

//TODO: El perfil puede ser actualizado o leido por el usuario que lo creo o por un administrador
@Service
public class PerfilService implements IPerfilService {
  @Autowired
  private PerfilRepository perfilRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public PerfilDTO buscarPorUsuarioId(Integer usuarioId) {
    User user = userRepository.findById(usuarioId).orElse(null);
    if (user == null) {
      throw new BadRequestException("El usuario con id " + usuarioId + " no existe");
    }
    Perfil perfil = this.perfilRepository.findByUser(user);
    if (perfil == null) {
      throw new BadRequestException("El perfil con usuario id " + usuarioId + " no existe");
    }
    return PerfilMapper.toDTO(perfil);
  }

  @Override
  public PerfilDTO buscarPorId(Integer id) {
    Perfil perfil = this.perfilRepository.findById(id).orElse(null);
    if (perfil == null) {
      throw new BadRequestException("El perfil con id " + id + " no existe");
    }
    return PerfilMapper.toDTO(perfil);
  }

  @Override
  public PerfilDTO crear(PerfilDTO perfilDTO) {
    Perfil perfil = PerfilMapper.toModel(perfilDTO);
    return PerfilMapper.toDTO(this.perfilRepository.save(perfil));
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
    return PerfilMapper.toDTO(this.perfilRepository.save(perfil));
  }
}
