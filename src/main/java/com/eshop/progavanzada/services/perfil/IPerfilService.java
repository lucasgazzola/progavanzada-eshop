package com.eshop.progavanzada.services.perfil;

import java.util.List;

import com.eshop.progavanzada.dtos.perfil.PerfilDTO;
import com.eshop.progavanzada.dtos.perfil.UpdatePerfilDTO;

public interface IPerfilService {
  List<PerfilDTO> listar(boolean incluirEliminados);

  PerfilDTO buscarPorId(Integer id);

  PerfilDTO crear(PerfilDTO perfilDTO);

  PerfilDTO actualizar(UpdatePerfilDTO perfilDTO);

  void eliminar(Integer id);
}
