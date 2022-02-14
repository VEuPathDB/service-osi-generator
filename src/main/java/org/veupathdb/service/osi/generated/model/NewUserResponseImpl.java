package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "userId",
    "username",
    "apiKey",
    "issued"
})
public class NewUserResponseImpl implements NewUserResponse {
  @JsonProperty("userId")
  private Long userId;

  @JsonProperty("username")
  private String username;

  @JsonProperty("apiKey")
  private String apiKey;

  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
  )
  @JsonDeserialize(
      using = TimestampDeserializer.class
  )
  @JsonProperty("issued")
  private Date issued;

  @JsonProperty("userId")
  public Long getUserId() {
    return this.userId;
  }

  @JsonProperty("userId")
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @JsonProperty("username")
  public String getUsername() {
    return this.username;
  }

  @JsonProperty("username")
  public void setUsername(String username) {
    this.username = username;
  }

  @JsonProperty("apiKey")
  public String getApiKey() {
    return this.apiKey;
  }

  @JsonProperty("apiKey")
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  @JsonProperty("issued")
  public Date getIssued() {
    return this.issued;
  }

  @JsonProperty("issued")
  public void setIssued(Date issued) {
    this.issued = issued;
  }
}
