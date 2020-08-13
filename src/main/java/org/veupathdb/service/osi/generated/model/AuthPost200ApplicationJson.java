package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = AuthPost200ApplicationJsonImpl.class
)
public interface AuthPost200ApplicationJson {
  @JsonProperty("apiKey")
  String getApiKey();

  @JsonProperty("apiKey")
  void setApiKey(String apiKey);
}
