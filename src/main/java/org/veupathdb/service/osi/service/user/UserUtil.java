package org.veupathdb.service.osi.service.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Date;

import org.veupathdb.service.osi.generated.model.NewUserResponse;
import org.veupathdb.service.osi.generated.model.NewUserResponseImpl;
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

  /**
   * @see #userToNewUserResponse(User)
   */
  public static NewUserResponse userToNewRes(final User user) {
    return getInstance().userToNewUserResponse(user);
  }

  public User createUser(final ResultSet rs) throws Exception {
    return new User(
      rs.getLong(Schema.Auth.Users.USER_ID),
      rs.getString(Schema.Auth.Users.COLUMN_USER_EMAIL),
      rs.getString(Schema.Auth.Users.COLUMN_API_KEY),
      rs.getObject(Schema.Auth.Users.COLUMN_ISSUED, OffsetDateTime.class)
    );
  }

  /**
   * Converts the given {@code User} instance into an instance of
   * {@code NewUserResponse} suitable for returning to an HTTP client.
   *
   * @param user user record to convert
   *
   * @return client safe response
   */
  public NewUserResponse userToNewUserResponse(final User user) {
    var out = new NewUserResponseImpl();

    out.setUserId(user.getUserId());
    out.setUsername(user.getUserName());
    out.setApiKey(user.getApiKey());
    out.setIssued(Date.from(user.getIssued().toInstant()));

    return out;
  }
}
