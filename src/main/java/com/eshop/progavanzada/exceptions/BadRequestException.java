package com.eshop.progavanzada.exceptions;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String mensaje) {
    super(mensaje);
  }
}
