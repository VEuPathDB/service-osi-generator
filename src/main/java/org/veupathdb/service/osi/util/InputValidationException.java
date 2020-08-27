package org.veupathdb.service.osi.util;

public class InputValidationException extends IllegalArgumentException
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
