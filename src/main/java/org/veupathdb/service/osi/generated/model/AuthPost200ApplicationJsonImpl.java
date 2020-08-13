package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("apiKey")
public class AuthPost200ApplicationJsonImpl implements AuthPost200ApplicationJson {
  @JsonProperty("apiKey")
  private String apiKey;

  @JsonProperty("apiKey")
  public String getApiKey() {
    return this.apiKey;
  }

  @JsonProperty("apiKey")
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }
}
