package test;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CollectionPostRequest
{
  public static final String
    JSON_KEY_NAME = "name";

  private String name;

  @JsonGetter(JSON_KEY_NAME)
  public String getName() {
    return name;
  }

  @JsonSetter(JSON_KEY_NAME)
  public CollectionPostRequest setName(String name) {
    this.name = name;
    return this;
  }
}
