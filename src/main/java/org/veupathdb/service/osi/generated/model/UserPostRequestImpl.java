package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("username")
public class UserPostRequestImpl implements UserPostRequest {
  @JsonProperty("username")
  private String username;

  @JsonProperty("username")
  public String getUsername() {
    return this.username;
  }

  @JsonProperty("username")
  public void setUsername(String username) {
    this.username = username;
  }
}
