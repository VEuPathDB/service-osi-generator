package org.veupathdb.service.osi.util;

import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.WebApplicationException;

import io.vulpine.lib.jcfi.CheckedFunction;

public final class Errors
{
  private static Errors instance;

  private Errors() {
  }

  public < T, R > R errorToRuntime(T value, CheckedFunction < T, R > fn) {
    try {
      return fn.apply(value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static Errors getInstance() {
    if (instance == null)
      instance = new Errors();
    return instance;
  }

  public static < T, R > R toRuntime(T val, CheckedFunction < T, R > fn) {
    return getInstance().errorToRuntime(val, fn);
  }

  public static RuntimeException wrapErr(Exception e) {
    if (e instanceof WebApplicationException)
      return (RuntimeException) e;
    return new InternalServerErrorException(e);
  }
}
