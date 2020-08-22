package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonDeserialize(
    as = NewUserResponseImpl.class
)
public interface NewUserResponse {
  @JsonProperty("userId")
  int getUserId();

  @JsonProperty("userId")
  void setUserId(int userId);

  @JsonProperty("userName")
  String getUserName();

  @JsonProperty("userName")
  void setUserName(String userName);

  @JsonProperty("apiKey")
  String getApiKey();

  @JsonProperty("apiKey")
  void setApiKey(String apiKey);

  @JsonProperty("issued")
  Date getIssued();

  @JsonProperty("issued")
  void setIssued(Date issued);
}
