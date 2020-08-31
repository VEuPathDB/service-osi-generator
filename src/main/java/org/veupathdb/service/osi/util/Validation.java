package org.veupathdb.service.osi.util;

public class Validation
{
  @SuppressWarnings("FieldMayBeFinal")
  private static Validation instance = new Validation();

  private static final String
    ERR_EMPTY = "Given value must be non-empty.",
    ERR_NULL  = "Given value must be non-null.",
    ERR_SHORT = "Given value must be at least %d characters in length.",
    ERR_MIN   = "Given value must be >= %d";

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static Validation getInstance() {
    return instance;
  }

  public static String minLength(final int min, final String pass) {
    return getInstance().enforceMinLength(min, pass);
  }

  /**
   * @see #enforceOneMinimum(long)
   */
  public static long oneMinimum(final long val) {
    return getInstance().enforceOneMinimum(val);
  }

  /**
   * @see #enforceZeroMinimum(int)
   */
  public static int zeroMinimum(final int val) {
    return getInstance().enforceZeroMinimum(val);
  }

  /**
   * @see #enforceNonEmpty(String)
   */
  public static String nonEmpty(final String val) {
    return getInstance().enforceNonEmpty(val);
  }

  /**
   * @see #enforceNonNull(Object)
   */
  public static < T > T nonNull(final T val) {
    return getInstance().enforceNonNull(val);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public int enforceZeroMinimum(final int value) {
    return enforceMinimum(0, value);
  }

  public int enforceMinimum(final int min, final int value) {
    if (value < min)
      throw new InputValidationException(value, String.format(ERR_MIN, min));

    return value;
  }

  public long enforceOneMinimum(final long value) {
    return enforceMinimum(1L, value);
  }

  public long enforceMinimum(final long min, final long value) {
    if (value < min)
      throw new InputValidationException(value, String.format(ERR_MIN, min));

    return value;
  }

  public String enforceMinLength(final int min, final String value) {
    if (enforceNonEmpty(value).length() < min) {
      var tmp = "*".repeat(value.length());
      throw new InputValidationException(tmp, String.format(ERR_SHORT, min));
    }

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
