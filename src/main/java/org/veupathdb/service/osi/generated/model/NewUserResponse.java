package org.veupathdb.service.osi.generated.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = NewUserResponseImpl.class
)
public interface NewUserResponse {
  @JsonProperty("userId")
  long getUserId();

  @JsonProperty("userId")
  void setUserId(long userId);

  @JsonProperty("username")
  String getUsername();

  @JsonProperty("username")
  void setUsername(String username);

  @JsonProperty("apiKey")
  String getApiKey();

  @JsonProperty("apiKey")
  void setApiKey(String apiKey);

  @JsonProperty("issued")
  Date getIssued();

  @JsonProperty("issued")
  void setIssued(Date issued);
}
