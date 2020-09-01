package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.veupathdb.service.osi.util.Field;
import org.veupathdb.service.osi.util.Format;
import org.veupathdb.service.osi.util.Validation;

public class User extends NewUser
{
  private final long userId;

  private final OffsetDateTime issued;

  public User(
    final long userId,
    final String userEmail,
    final String apiKey,
    final OffsetDateTime issued
  ) {
    super(userEmail, apiKey);
    this.userId = Validation.oneMinimum(userId);
    this.issued = Validation.nonNull(issued);
  }

  public User(
    final long userId,
    final OffsetDateTime issued,
    final NewUser from
  ) {
    super(from);
    this.userId = Validation.oneMinimum(userId);
    this.issued = Validation.nonNull(issued);
  }

  public long getUserId() {
    return userId;
  }

  public OffsetDateTime getIssued() {
    return issued;
  }

  @Override
  public String toString() {
    return Format.Json()
      .createObjectNode()
      .put(Field.User.ID, getUserId())
      .put(Field.User.USERNAME, getUserName())
      .put(Field.User.API_KEY, "********")
      .put(Field.User.ISSUED, getIssued()
        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
      .toString();
  }
}
