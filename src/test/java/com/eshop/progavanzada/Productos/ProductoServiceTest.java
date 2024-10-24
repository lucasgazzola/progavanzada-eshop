package com.eshop.progavanzada.Productos;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.eshop.progavanzada.services.MarcaService;
import com.eshop.progavanzada.services.ProductoService;

@SpringBootTest
public class ProductoServiceTest {

  @Mock
  private MarcaService marcaService;

  @InjectMocks
  private ProductoService productoService;

}
