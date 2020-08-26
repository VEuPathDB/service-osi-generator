package org.veupathdb.service.osi.util;


import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.gusdb.fgputil.functional.Either;

public class Params
{
  public static Either < String, Integer > stringOrInt(String value) {
    try {
      return Either.right(Integer.parseInt(value));
    } catch (Exception __) {
      return Either.left(value);
    }
  }

  public static OffsetDateTime nullableTimestamp(Long value) {
    if (value == null)
      return null;

    return OffsetDateTime.ofInstant(Instant.ofEpochSecond(value),
      ZoneId.systemDefault());
  }

  public static String orStr(String val, String or) {
    return or(val == null || val.isBlank() ? null : val, or);
  }

  public static < T > T or(T val, T or) {
    return val == null ? or : val;
  }
}
