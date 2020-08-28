package org.veupathdb.service.osi.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Mockable static serialization format provider.
 */
public final class Format
{
  private static Format instance;

  private final ObjectMapper json;

  private Format() {
    this.json = new ObjectMapper();
  }

  public ObjectMapper getJson() {
    return json;
  }

  public static Format getInstance() {
    if (instance == null)
      instance = new Format();

    return instance;
  }

  public static ObjectMapper Json() {
    return getInstance().getJson();
  }
}
