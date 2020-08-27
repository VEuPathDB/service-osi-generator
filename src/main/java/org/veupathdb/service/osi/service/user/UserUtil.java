package org.veupathdb.service.osi.service.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.repo.Schema;

public class UserUtil
{
  private static final UserUtil instance = new UserUtil();

  public static UserUtil getInstance() {
    return instance;
  }

  public static User newUser(ResultSet rs) throws Exception {
    return getInstance().createUser(rs);
  }

  public User createUser(final ResultSet rs) throws Exception {
    return new User(
      rs.getLong(Schema.Auth.Users.USER_ID),
      rs.getString(Schema.Auth.Users.COLUMN_USER_EMAIL),
      rs.getString(Schema.Auth.Users.COLUMN_API_KEY),
      rs.getObject(Schema.Auth.Users.COLUMN_ISSUED, OffsetDateTime.class)
    );
  }
}
