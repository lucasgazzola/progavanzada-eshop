package com.eshop.progavanzada.services;

import jakarta.persistence.ElementCollection;

import java.util.List;

import com.eshop.progavanzada.dtos.MarcaDTO;
import com.eshop.progavanzada.dtos.UpdateMarcaDTO;
import com.eshop.progavanzada.models.Marca;

public interface IMarcaService {

  @ElementCollection(targetClass = Integer.class)
  public List<MarcaDTO> listarMarcas(boolean incluirEliminados);

  public MarcaDTO buscarPorId(Integer id);

  public MarcaDTO crearMarca(MarcaDTO marcaDTO);

  public MarcaDTO actualizarMarca(Integer id, UpdateMarcaDTO marcaDTO);

  public void eliminarMarca(Integer id);

  public void recuperarMarca(Marca marca);
}
