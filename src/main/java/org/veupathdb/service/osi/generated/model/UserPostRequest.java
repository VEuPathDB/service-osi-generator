package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = UserPostRequestImpl.class
)
public interface UserPostRequest {
  @JsonProperty("username")
  String getUsername();

  @JsonProperty("username")
  void setUsername(String username);
}
