package com.eshop.progavanzada.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
/**
 * Esta clase sirve como un gestor de excepciones para los controladores en
 * general.
 *
 * Aqui se pueden administrar aquellos eventos causados por el controlador en un
 * momento dado, como lo son las excepciones detectadas a nivel controlador (500
 * Internal Server Error, 400 Bad Request, etcetera)
 */
public class CustomExceptionHandler {

  /**
   * Funcion helper que construye el body de response para un error de validacion
   * (Bad Request 400)
   *
   * @param status
   * @param message
   * @param request
   * @return
   */
  private Map<String, Object> buildBadRequestResponseBody(HttpStatus status, List<String> message,
      WebRequest request) {
    // Crear el body original de Springboot, agregando una propiedad nueva donde
    // esten los errores presentes.
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    body.put("details", message);
    body.put("path", request.getDescription(false).replace("uri=", ""));

    return body;
  }

  // Gestionar errores de validacion unicamente.
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
    // Crear una lista para los errores de validacion.
    List<String> errors = new ArrayList<>();

    // Iterar por cada uno.
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors())
      errors.add(fieldError.getDefaultMessage());

    // Crear el body original de Springboot, agregando una propiedad nueva donde
    // esten los errores presentes.
    Map<String, Object> body = this.buildBadRequestResponseBody(HttpStatus.BAD_REQUEST, errors, request);

    // Retornar la respuesta.
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataConflictException.class)
  public ResponseEntity<Object> handleDataConflictException(DataConflictException ex, WebRequest request) {
    // Crear una lista con el mensaje de error
    List<String> errors = new ArrayList<>();
    errors.add(ex.getMessage());

    // Construir la respuesta usando el helper existente
    Map<String, Object> body = this.buildBadRequestResponseBody(HttpStatus.CONFLICT, errors, request);

    // Retornar la respuesta con status 409 Conflict
    return new ResponseEntity<>(body, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
    List<String> errors = new ArrayList<>();
    errors.add(ex.getMessage());

    Map<String, Object> body = this.buildBadRequestResponseBody(HttpStatus.BAD_REQUEST, errors, request);
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
    List<String> errors = new ArrayList<>();
    errors.add(ex.getMessage());

    Map<String, Object> body = this.buildBadRequestResponseBody(HttpStatus.BAD_REQUEST, errors, request);
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }
}