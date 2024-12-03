package com.eshop.progavanzada.Unit.Services.Productos;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.eshop.progavanzada.services.marcas.MarcaService;
import com.eshop.progavanzada.services.productos.ProductoService;

@SpringBootTest
public class ProductoServiceTest {

  @Mock
  private MarcaService marcaService;

  @InjectMocks
  private ProductoService productoService;

}
