package org.veupathdb.service.osi.repo;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.veupathdb.service.osi.model.db.NewUser;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.service.DbMan;

/**
 * User table queries.
 */
public class UserRepo
{
  /**
   * Inserts a new user record based on the given user instance.
   * <p>
   * If a user already exists with the email and api-key attached to the given
   * user instance this method will throw and SQL Exception for unique key
   * violation.
   *
   * @param user NewUser instance to insert into the users table.
   *
   * @return A full User instance including the newly assigned userId and issued
   * values.
   */
  public static User insertNewUser(NewUser user) throws Exception {
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Insert.Auth.NEW_USER)
    ) {
      ps.setString(1, user.getUserEmail());
      ps.setString(2, user.getApiKey());

      try (var rs = ps.executeQuery()) {
        if (!rs.next())
          throw new IllegalStateException("User insert query did not return an ID.");

        return new User(
          rs.getInt(Schema.Auth.Users.USER_ID),
          rs.getObject(Schema.Auth.Users.COLUMN_ISSUED, OffsetDateTime.class),
          user
        );
      }
    }
  }

  /**
   * Attempts to retrieve a user record based on the given email address and
   * api key.
   * <p>
   * If no user record was found matching the given values this method returns
   * an empty option.
   *
   * @param email Email address with which to look up a user record
   * @param token API Key with which to look up a user record
   *
   * @return An option containing the user record if such a user was found, or
   *         nothing if no such user exists.
   */
  public static Optional < User > selectByToken(String email, String token)
  throws Exception {
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Auth.Users.BY_TOKEN)
    ) {
      ps.setString(1, email);
      ps.setString(2, token);

      try (var rs = ps.executeQuery()) {
        if (!rs.next())
          return Optional.empty();

        return Optional.of(Utils.userFromRs(rs));
      }
    }
  }
}
