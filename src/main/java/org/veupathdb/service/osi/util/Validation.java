package org.veupathdb.service.osi.util;

public class Validation
{
  public static int setMinimum(int val, int min) {
    if (val < min)
      throw new InputValidationException(val, "Given value must be >= " + min);

    return val;
  }

  public static long setMinimum(long val, long min) {
    if (val < min)
      throw new InputValidationException(val, "Given value must be >= " + min);

    return val;
  }

  public static int oneMinimum(int val) {
    return setMinimum(val, 1);
  }

  public static long oneMinimum(long val) {
    return setMinimum(val, 1L);
  }

  public static int zeroMinimum(int val) {
    return setMinimum(val, 0);
  }

  public static String nonEmpty(String val) {
    nonNull(val);

    if (val.isBlank())
      throw new InputValidationException(val, "Given value must be non-empty");

    return val;
  }

  public static < T > T nonNull(T val) {
    if (val == null)
      throw new InputValidationException(val, "Given value must be non-null.");
    return val;
  }
}
