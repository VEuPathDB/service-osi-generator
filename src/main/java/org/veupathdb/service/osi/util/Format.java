package org.veupathdb.service.osi.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Mockable static serialization format provider.
 */
public class Format
{
  private static Format instance;

  private final ObjectMapper json = new ObjectMapper();

  public ObjectMapper getJson() {
    return json;
  }

  public static Format getInstance() {
    return instance;
  }

  public static ObjectMapper Json() {
    if (instance == null)
      instance = new Format();

    return getInstance().getJson();
  }
}
