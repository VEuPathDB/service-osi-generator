package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonDeserialize(
    as = NewUserResponseImpl.class
)
public interface NewUserResponse {
  @JsonProperty("userId")
  Long getUserId();

  @JsonProperty("userId")
  void setUserId(Long userId);

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
