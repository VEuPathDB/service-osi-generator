package org.veupathdb.service.osi.util;

public class Validation
{
  public static int oneMinimum(int val) {
    if (val < 1)
      throw new RuntimeException("Given value must be >= 1");
    return val;
  }

  public static String nonEmpty(String val) {
    if (val == null || val.isBlank())
      throw new RuntimeException("Given value must be non-empty");
    return val;
  }
}
