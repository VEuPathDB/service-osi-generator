package test;

import java.time.OffsetDateTime;

public class UserRecord
{
  private long user_id;

  private String user_name;

  private String api_key;

  private OffsetDateTime issued;

  public long getUserId() {
    return user_id;
  }

  public UserRecord setUserId(long user_id) {
    this.user_id = user_id;
    return this;
  }

  public String getUserName() {
    return user_name;
  }

  public UserRecord setUserName(String user_name) {
    this.user_name = user_name;
    return this;
  }

  public String getApiKey() {
    return api_key;
  }

  public UserRecord setApiKey(String api_key) {
    this.api_key = api_key;
    return this;
  }

  public OffsetDateTime getIssued() {
    return issued;
  }

  public UserRecord setIssued(OffsetDateTime issued) {
    this.issued = issued;
    return this;
  }
}
