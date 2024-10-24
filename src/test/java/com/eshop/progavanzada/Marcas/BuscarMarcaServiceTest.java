package com.eshop.progavanzada.Marcas;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.eshop.progavanzada.dtos.MarcaDTO;
import com.eshop.progavanzada.exceptions.NotFoundException;
import com.eshop.progavanzada.models.Marca;
import com.eshop.progavanzada.repositories.MarcaRepository;
import com.eshop.progavanzada.services.MarcaService;

@SpringBootTest
public class BuscarMarcaServiceTest {
  @Mock
  private MarcaRepository marcaRepository;

  @InjectMocks
  private MarcaService marcaService;

  @DisplayName("Buscar marca por id con éxito")
  @Test
  public void testBuscarMarcaPorId_Exito() {
    Marca nuevaMarca = new Marca();
    nuevaMarca.setNombre("Test Marca");
    nuevaMarca.setEliminado(false);
    nuevaMarca.setId(1);
    nuevaMarca.setDescripcion(null);

    when(marcaRepository.findById(1)).thenReturn(Optional.of(nuevaMarca));

    MarcaDTO marcaDTO = marcaService.buscarPorId(1);

    assertNotNull(marcaDTO);
  }

  @DisplayName("Buscar marca por id sin éxito por Id inexistente")
  @Test
  public void testBuscarMarcaPorId_IdInexistente() {
    Marca nuevaMarca = new Marca();
    nuevaMarca.setNombre("Test Marca");
    nuevaMarca.setEliminado(false);
    nuevaMarca.setId(1);
    nuevaMarca.setDescripcion(null);

    when(marcaRepository.findById(1)).thenThrow(NotFoundException.class);

    assertThrows(NotFoundException.class, () -> marcaService.buscarPorId(1));
  }
}
