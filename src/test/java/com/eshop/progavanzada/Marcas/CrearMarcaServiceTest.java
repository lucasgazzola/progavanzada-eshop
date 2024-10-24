package com.eshop.progavanzada.Marcas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.eshop.progavanzada.dtos.MarcaDTO;
import com.eshop.progavanzada.exceptions.BadRequestException;
import com.eshop.progavanzada.exceptions.DataConflictException;
import com.eshop.progavanzada.mappers.MarcaMapper;
import com.eshop.progavanzada.models.Marca;
import com.eshop.progavanzada.repositories.MarcaRepository;
import com.eshop.progavanzada.services.MarcaService;

@SpringBootTest
public class CrearMarcaServiceTest {
  @Mock
  private MarcaRepository marcaRepository;

  @InjectMocks
  private MarcaService marcaService;

  @DisplayName("Crear nueva marca con éxito")
  @Test
  public void testCreateMarca_Exito() {
    // Arrange
    Marca nuevaMarca = new Marca();
    nuevaMarca.setNombre("Test Marca");
    nuevaMarca.setEliminado(false);
    nuevaMarca.setId(1);
    nuevaMarca.setDescripcion(null);

    MarcaDTO marcaDTO = MarcaMapper.toDTO(nuevaMarca);

    MarcaDTO createdMarca = marcaService.crearMarca(marcaDTO);

    assertNotNull(createdMarca);
  }

  @DisplayName("Crear marca sin éxito por estar repetida")
  @Test
  public void testCreateMarca_Repetida() {
    // Setup
    List<Marca> marcas = new ArrayList<>();
    when(marcaRepository.findAll()).thenReturn(marcas);

    Marca nuevaMarca = new Marca();
    nuevaMarca.setNombre("Test Marca");
    nuevaMarca.setDescripcion(null);

    marcas.add(nuevaMarca);
    MarcaDTO marcaDTO = MarcaMapper.toDTO(nuevaMarca);

    // Intento de creación de marca repetida
    assertThrows(DataConflictException.class, () -> {
      marcaService.crearMarca(marcaDTO);
    });
  }

  @DisplayName("Crear marca sin éxito por estar repetida (ignorando mayúsculas)")
  @Test
  public void testCreateMarca_Exito_CaseInsensitive() {
    // Setup
    List<Marca> marcas = new ArrayList<>();
    when(marcaRepository.findAll()).thenReturn(marcas);

    Marca marcaOriginal = new Marca();
    marcaOriginal.setNombre("Test Marca");
    marcaOriginal.setDescripcion(null);
    marcas.add(marcaOriginal);

    Marca nuevaMarca = new Marca();
    nuevaMarca.setNombre("test marca");
    MarcaDTO marcaDTO = MarcaMapper.toDTO(nuevaMarca);

    // Intento de creación de marca repetida (ignorando mayúsculas)
    assertThrows(DataConflictException.class, () -> {
      marcaService.crearMarca(marcaDTO);
    });
  }

  @DisplayName("Crear marca sin éxito por no contener nombre")
  @Test
  public void testCreateMarca_NoNombre() {
    // Setup
    Marca nuevaMarca = new Marca();
    nuevaMarca.setDescripcion(null);
    MarcaDTO marcaDTO = MarcaMapper.toDTO(nuevaMarca);

    // Intento de creación de marca sin nombre
    assertThrows(BadRequestException.class, () -> {
      marcaService.crearMarca(marcaDTO);
    });
  }

  @DisplayName("Crear marca con éxito sin descripción")
  @Test
  public void testCreateMarca_Exito_NoDescripcion() {
    // Setup
    Marca nuevaMarca = new Marca();
    nuevaMarca.setNombre("Test Marca");
    MarcaDTO marcaDTO = MarcaMapper.toDTO(nuevaMarca);

    MarcaDTO createdMarca = marcaService.crearMarca(marcaDTO);

    // Creación de marca sin descripción
    assertEquals(createdMarca, marcaDTO);
  }

  @DisplayName("Crear marca eliminando espacios intermedios")
  @Test
  public void testCreateMarca_Exito_EliminandoEspaciosIntermedios() {
    // Setup
    Marca nuevaMarca = new Marca();
    nuevaMarca.setNombre("Test       Marca");
    MarcaDTO marcaDTO = MarcaMapper.toDTO(nuevaMarca);

    MarcaDTO createdMarca = marcaService.crearMarca(marcaDTO);

    // Creación de marca sin descripción
    assertEquals(createdMarca.getNombre(), "Test Marca");
  }

  @DisplayName("Crear marca eliminando espacios iniciales y finales")
  @Test
  public void testCreateMarca_Exito_EliminandoEspaciosExtra() {
    // Setup
    Marca nuevaMarca = new Marca();
    nuevaMarca.setNombre("     Test   Marca    ");
    MarcaDTO marcaDTO = MarcaMapper.toDTO(nuevaMarca);

    MarcaDTO createdMarca = marcaService.crearMarca(marcaDTO);

    // Creación de marca sin descripción
    assertEquals(createdMarca.getNombre(), "Test Marca");
  }
}
