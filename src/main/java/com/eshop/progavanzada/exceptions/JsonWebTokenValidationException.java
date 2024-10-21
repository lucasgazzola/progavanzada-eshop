package com.eshop.progavanzada.exceptions;

public class JsonWebTokenValidationException extends RuntimeException {
  public JsonWebTokenValidationException(String mensaje) {
    super(mensaje);
  }
}
