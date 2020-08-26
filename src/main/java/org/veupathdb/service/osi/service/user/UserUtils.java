package org.veupathdb.service.osi.service.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.repo.Schema;

public class UserUtils
{
  public static User newUser(ResultSet rs) throws SQLException {
    return new User(
      rs.getInt(Schema.Auth.Users.USER_ID),
      rs.getString(Schema.Auth.Users.COLUMN_USER_EMAIL),
      rs.getString(Schema.Auth.Users.COLUMN_API_KEY),
      rs.getObject(Schema.Auth.Users.COLUMN_ISSUED, OffsetDateTime.class)
    );
  }
}
