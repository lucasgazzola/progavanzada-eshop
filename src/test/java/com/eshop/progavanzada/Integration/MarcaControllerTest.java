package com.eshop.progavanzada.Integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MarcaControllerTest {
  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).apply(springSecurity()).build();
  }

  @Test
  public void testGetAllMarcas_Exito() throws Exception {
    this.mockMvc.perform(get("/api/marcas")).andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @Transactional
  @WithMockUser(username = "user", roles = { "USER" })
  public void testPostMarcaRolIncorrecto() throws Exception {
    mockMvc.perform(post("/api/marcas") // Asegúrate de que sea un POST
        .contentType(MediaType.APPLICATION_JSON) // Si es necesario, define el tipo de contenido
        .content("{\"nombre\": \"Marca Test\"}")) // Incluye un cuerpo si es necesario
        .andExpect(status().isForbidden()); // Debería devolver 403 Forbidden
  }

  @Test
  @Transactional
  @WithMockUser(username = "admin", roles = { "ADMIN" })
  public void testPostMarcaRolCorrecto_Exito() throws Exception {
    mockMvc.perform(post("/api/marcas") // Asegúrate de que sea un POST
        .contentType(MediaType.APPLICATION_JSON) // Si es necesario, define el tipo de contenido
        .content("{\"nombre\": \"Marca Test\"}")) // Incluye un cuerpo si es necesario
        .andExpect(status().isCreated()); // Debería devolver 403 Forbidden
  }
}