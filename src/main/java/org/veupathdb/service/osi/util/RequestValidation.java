package org.veupathdb.service.osi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.errors.UnprocessableEntityException;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;

public class RequestValidation
{
  private static final RequestValidation instance = new RequestValidation();

  private static final String
    ERR_EMPTY = "Value cannot be blank.",
    ERR_NULL  = "Value cannot be null.",
    ERR_LTHAN = "Value must be greater than %d.",
    ERR_SHORT = "Value must be at least %d characters in length.";

  private final Logger log = LogProvider.logger(getClass());

  private static Map < String, List < String > > newErrMap() {
    return new HashMap <>();
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static RequestValidation getInstance() {
    return instance;
  }

  public static boolean notNull(
    final String key,
    final Object val,
    final Map < String, List < String > > errs
  ) {
    return getInstance().isNotNull(key, val, errs);
  }

  public static void notEmpty(final String key, final String val) {
    getInstance().isNotEmpty(key, val);
  }

  public static boolean notEmpty(
    final String key,
    final String val,
    final Map < String, List < String > > errs
  ) {
    return getInstance().isNotEmpty(key, val, errs);
  }

  public static boolean minLength(
    final String key,
    final String val,
    final int len,
    final Map < String, List < String > > errs
  ) {
    return getInstance().hasMinLength(key, val, len, errs);
  }

  public static boolean greaterThan(
    final String key,
    final Long val,
    final long min,
    final Map < String, List < String > > errs
  ) {
    return getInstance().isGreaterThan(key, val, min, errs);
  }

  public static boolean atLeast(
    final String key,
    final Long val,
    final long min,
    final Map < String, List < String > > errs
  ) {
    return getInstance().isAtLeast(key, val, min, errs);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public boolean isAtLeast(
    final String key,
    final Long value,
    final long min,
    final Map < String, List < String > > errs
  ) {
    log.trace("RequestValidation#isAtLeast(String, Long, long, Map)");

    return isGreaterThan(key, value, min - 1, errs);
  }

  public boolean isGreaterThan(
    final String key,
    final Long value,
    final long min,
    final Map < String, List < String > > errs
  ) {
    log.trace("RequestValidation#isGreaterThan(String, Long, long, Map)");

    if (!isNotNull(key, value, errs))
      return false;

    if (value > min)
      return true;

    errs.computeIfAbsent(key, this::makeErrList).add(String.format(ERR_LTHAN, min));
    return false;
  }

  public boolean isNotNull(
    final String key,
    final Object value,
    final Map < String, List < String > > errs
  ) {
    log.trace("RequestValidation#isNotNull(String, String, Map)");

    if (value != null)
      return true;

    errs.computeIfAbsent(key, k -> new ArrayList <>()).add(ERR_NULL);
    return false;
  }

  public void isNotEmpty(final String key, final String value) {
    log.trace("RequestValidation#isNotEmpty(String, String)");

    var out = new HashMap < String, List < String > >();
    if (!isNotEmpty(key, value, out))
      throw new UnprocessableEntityException(out);
  }

  public boolean isNotEmpty(
    final String key,
    final String value,
    final Map < String, List < String > > errs
  ) {
    log.trace("RequestValidation#isNotEmpty(String, String, Map)");

    if (!isNotNull(key, value, errs))
      return false;

    if (!value.isBlank())
      return true;

    errs.computeIfAbsent(key, k -> new ArrayList <>()).add(ERR_EMPTY);
    return false;
  }

  public boolean hasMinLength(
    final String key,
    final String value,
    final int len,
    final Map < String, List < String > > errs
  ) {
    log.trace("RequestValidation#hasMinLength(String, String, int, Map)");

    if (!isNotEmpty(key, value, errs))
      return false;

    if (value.length() < len) {
      errs.computeIfAbsent(key, this::makeErrList).add(String.format(ERR_SHORT, len));
      return false;
    }

    return true;
  }

  List < String > makeErrList(Object ignored) {
    log.trace("RequestValidation#makeErrList(Object)");

    return new ArrayList <>();
  }
}
