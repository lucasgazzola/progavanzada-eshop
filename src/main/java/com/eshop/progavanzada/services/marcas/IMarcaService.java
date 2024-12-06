package com.eshop.progavanzada.services.marcas;

import jakarta.persistence.ElementCollection;

import java.util.List;

import com.eshop.progavanzada.dtos.marcas.MarcaDTO;
import com.eshop.progavanzada.dtos.marcas.UpdateMarcaDTO;
import com.eshop.progavanzada.models.Marca;

public interface IMarcaService {

  @ElementCollection(targetClass = Integer.class)
  public List<MarcaDTO> listar(boolean incluirEliminados);

  public MarcaDTO buscarPorId(Integer id);

  public MarcaDTO crear(MarcaDTO marcaDTO);

  public MarcaDTO actualizar(UpdateMarcaDTO marcaDTO);

  public void eliminar(Integer id);

  public void recuperarEliminado(Marca marca);
}
