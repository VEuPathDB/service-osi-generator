package org.veupathdb.service.osi.service.user;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.Date;

import org.veupathdb.service.osi.db.Schema;
import org.veupathdb.service.osi.generated.model.NewUserResponse;
import org.veupathdb.service.osi.generated.model.NewUserResponseImpl;
import org.veupathdb.service.osi.model.db.NewUser;
import org.veupathdb.service.osi.model.db.User;

public class UserUtil
{
  private static final UserUtil instance = new UserUtil();

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static UserUtil getInstance() {
    return instance;
  }

  /**
   * @see #createUser(ResultSet)
   */
  public static User newUser(final ResultSet rs) throws Exception {
    return getInstance().createUser(rs);
  }

  /**
   * @see #createUser(ResultSet, NewUser)
   */
  public static User newUser(final ResultSet rs, final NewUser user)
  throws Exception{
    return getInstance().createUser(rs, user);
  }

  /**
   * @see #userToNewUserResponse(User)
   */
  public static NewUserResponse userToNewRes(final User user) {
    return getInstance().userToNewUserResponse(user);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public User createUser(final ResultSet rs) throws Exception {
    return new User(
      rs.getLong(Schema.Auth.Users.USER_ID),
      rs.getString(Schema.Auth.Users.USER_NAME),
      rs.getString(Schema.Auth.Users.API_KEY),
      rs.getObject(Schema.Auth.Users.ISSUED, OffsetDateTime.class)
    );
  }

  public User createUser(final ResultSet rs, final NewUser user)
  throws Exception {
    return new User(
      rs.getLong(Schema.Auth.Users.USER_ID),
      user.getUserName(),
      user.getApiKey(),
      rs.getObject(Schema.Auth.Users.ISSUED, OffsetDateTime.class)
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
