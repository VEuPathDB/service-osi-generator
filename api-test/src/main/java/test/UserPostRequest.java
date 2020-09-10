package test;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class UserPostRequest
{
  public static final String
    JSON_KEY_USERNAME = "username";

  private String username;

  @JsonGetter(JSON_KEY_USERNAME)
  public String getUsername() {
    return username;
  }

  @JsonSetter(JSON_KEY_USERNAME)
  public UserPostRequest setUsername(String username) {
    this.username = username;
    return this;
  }
}
