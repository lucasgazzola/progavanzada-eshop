package com.eshop.progavanzada.services.perfil;

import com.eshop.progavanzada.dtos.perfil.PerfilDTO;
import com.eshop.progavanzada.dtos.perfil.UpdatePerfilDTO;

public interface IPerfilService {

  PerfilDTO buscarPorUsuarioId(Integer usuarioId);

  PerfilDTO buscarPorId(Integer id);

  PerfilDTO crear(PerfilDTO perfilDTO);

  PerfilDTO actualizar(UpdatePerfilDTO perfilDTO);
}
