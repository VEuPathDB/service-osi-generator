package org.veupathdb.service.osi.util;

public class Validation
{
  private static final Validation instance = new Validation();

  private static final String
    ERR_EMPTY = "Given value must be non-empty.",
    ERR_NULL  = "Given value must be non-null.",
    ERR_SHORT = "Given value must be at least %d characters in length.";

  public static Validation getInstance() {
    return instance;
  }

  public static String minLength(int i, String pass) {
    return null;
  }

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
    return getInstance().enforceNonEmpty(val);
  }

  public static < T > T nonNull(T val) {
    return getInstance().enforceNonNull(val);
  }

  public String enforceMinLength(final int min, final String value) {
    if (enforceNonEmpty(value).length() < min)
      throw new InputValidationException("*".repeat(value.length()), ERR_SHORT);
    return value;
  }

  public String enforceNonEmpty(final String value) {
    if (enforceNonNull(value).isBlank())
      throw new InputValidationException(value, ERR_EMPTY);

    return value;
  }

  public < R > R enforceNonNull(R value) {
    if (value == null)
      throw new InputValidationException(null, ERR_NULL);
    return value;
  }
}
