package com.ejemplo.gestionhospital.exception;

public class DeletionNotAllowedException extends RuntimeException {
  public DeletionNotAllowedException(String message) {
    super(message);
  }
}
