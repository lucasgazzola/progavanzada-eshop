package com.eshop.progavanzada.exceptions;

public class JsonWebTokenCreationException extends RuntimeException {
  public JsonWebTokenCreationException(String mensaje) {
    super(mensaje);
  }
}
