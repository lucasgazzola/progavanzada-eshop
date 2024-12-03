package com.eshop.progavanzada.Unit.Services.Marcas;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.eshop.progavanzada.models.Marca;
import com.eshop.progavanzada.repositories.MarcaRepository;
import com.eshop.progavanzada.services.marcas.MarcaService;
import com.eshop.progavanzada.exceptions.NotFoundException;

@SpringBootTest
public class EliminarServiceTest {
  @Mock
  private MarcaRepository marcaRepository;

  @InjectMocks
  private MarcaService marcaService;

  @DisplayName("Eliminar marca con éxito")
  @Test
  public void testDeleteMarca_Exito() {
    Marca marca = new Marca();
    marca.setId(1);
    marca.setNombre("Test Marca");
    marca.setEliminado(true);
    marca.setDescripcion(null);

    when(marcaRepository.findById(1)).thenReturn(Optional.of(marca));

    assertDoesNotThrow(() -> marcaService.eliminar(marca.getId()));
  }

  @DisplayName("Eliminar marca sin éxito por Id incorrecto")
  @Test
  public void testDeleteMarca_IdInexistente() {
    Marca marca = new Marca();
    marca.setId(1);
    marca.setNombre("Test Marca");
    marca.setEliminado(true);
    marca.setDescripcion(null);

    when(marcaRepository.findById(1)).thenThrow(NotFoundException.class);

    assertThrows(NotFoundException.class, () -> marcaService.eliminar(marca.getId()));
  }
}
