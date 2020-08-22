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
    "userName",
    "apiKey",
    "issued"
})
public class NewUserResponseImpl implements NewUserResponse {
  @JsonProperty("userId")
  private int userId;

  @JsonProperty("userName")
  private String userName;

  @JsonProperty("apiKey")
  private String apiKey;

  @JsonProperty("issued")
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
  )
  @JsonDeserialize(
      using = TimestampDeserializer.class
  )
  private Date issued;

  @JsonProperty("userId")
  public int getUserId() {
    return this.userId;
  }

  @JsonProperty("userId")
  public void setUserId(int userId) {
    this.userId = userId;
  }

  @JsonProperty("userName")
  public String getUserName() {
    return this.userName;
  }

  @JsonProperty("userName")
  public void setUserName(String userName) {
    this.userName = userName;
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
