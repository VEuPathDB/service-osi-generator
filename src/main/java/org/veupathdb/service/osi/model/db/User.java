package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;
import java.util.Objects;

import org.veupathdb.service.osi.util.Validation;

public class User
{
  private int userId;
  private final String userEmail;
  private final String apiKey;
  private OffsetDateTime issued;

  public User(String userEmail, String apiKey) {
    this.userEmail = Validation.nonEmpty(userEmail);
    this.apiKey    = Validation.nonEmpty(apiKey);
  }

  public int getUserId() {
    return userId;
  }

  public User setUserId(int userId) {
    this.userId = Validation.oneMinimum(userId);
    return this;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public String getApiKey() {
    return apiKey;
  }

  public OffsetDateTime getIssued() {
    return issued;
  }

  public User setIssued(OffsetDateTime time) {
    this.issued = Objects.requireNonNull(time);
    return this;
  }
}
