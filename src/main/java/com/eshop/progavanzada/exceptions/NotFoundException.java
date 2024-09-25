package com.eshop.progavanzada.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String mensaje) {
    super(mensaje);
  }
}
