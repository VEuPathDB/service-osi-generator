package org.veupathdb.service.osi.util;

public class InputValidationException extends RuntimeException
{
  private final Object value;

  public InputValidationException(Object value, String message) {
    super(message);
    this.value = value;
  }

  public Object getValue() {
    return value;
  }
}
