package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Validation;

public class User extends NewUser
{
  private final long userId;

  private final OffsetDateTime issued;

  public User(
    long userId,
    String userEmail,
    String apiKey,
    OffsetDateTime issued
  ) {
    super(userEmail, apiKey);
    this.userId = Validation.oneMinimum(userId);
    this.issued = Validation.nonNull(issued);
  }

  public User(
    long userId,
    OffsetDateTime issued,
    NewUser from
  ) {
    this(
      userId,
      from.getUserName(),
      from.getApiKey(),
      issued
    );
  }

  public long getUserId() {
    return userId;
  }

  public OffsetDateTime getIssued() {
    return issued;
  }

  @Override
  public String toString() {
    return "User{" +
      "userId=" + userId +
      ", userName='" + getUserName() +
      "', apiKey='********'" +
      ", issued=" + issued + '}';
  }
}
