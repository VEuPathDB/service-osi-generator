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
    ERR_LTHAN = "Value must be greater than %d.";

  private final Logger log = LogProvider.logger(getClass());

  private static Map < String, List < String > > newErrMap() {
    return new HashMap <>();
  }

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

  public boolean isNotNull(
    final String key,
    final Object value,
    final Map < String, List < String > > errs
  ) {
    if (value != null)
      return true;

    errs.computeIfAbsent(key, k -> new ArrayList <>()).add(ERR_NULL);
    return false;
  }

  public void isNotEmpty(final String key, final String value) {
    var out = new HashMap< String, List < String > >();
    if (!isNotEmpty(key, value, out))
      throw new UnprocessableEntityException(out);
  }

  public boolean isNotEmpty(
    final String key,
    final String value,
    final Map < String, List < String > > errs
  ) {
    if (!isNotNull(key, value, errs))
      return false;

    if (!value.isBlank())
      return true;

    errs.computeIfAbsent(key, k -> new ArrayList <>()).add(ERR_EMPTY);
    return false;
  }

  public boolean isGreaterThan(
    final String key,
    final long value,
    final long compareTo,
    final Map < String, List < String > > errs
  ) {
    if (value > compareTo)
      return true;

    errs.computeIfAbsent(key, this::makeErrList).add(
      String.format(ERR_LTHAN, compareTo));
    return false;
  }

  List < String > makeErrList(Object ignored) {
    return new ArrayList <>();
  }
}
