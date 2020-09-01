package org.veupathdb.service.osi.service.user;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.veupathdb.service.osi.db.SQL;
import org.veupathdb.service.osi.db.Schema;
import org.veupathdb.service.osi.model.db.NewUser;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.util.Validation;

/**
 * User table queries.
 */
public class UserRepo
{
  private static final Logger log = LogManager.getLogger(UserRepo.class);

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
    log.trace("UserRepo#insertNewUser({})", user);
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Insert.Auth.NEW_USER)
    ) {
      ps.setString(1, user.getUserName());
      ps.setString(2, user.getApiKey());

      try (var rs = ps.executeQuery()) {
        if (!rs.next())
          throw new IllegalStateException("User insert query did not return an ID.");

        return new User(
          rs.getInt(Schema.Auth.Users.USER_ID),
          rs.getObject(Schema.Auth.Users.ISSUED, OffsetDateTime.class),
          user
        );
      }
    }
  }

  /**
   * Looks up a user record by email alone.
   * <p>
   * WARNING: This method should not be used for authentication in any way, this
   * is for secondary internal tasks only.
   *
   * @param email User email address to search by
   *
   * @return An option containing either a user record or nothing if no such
   * record was found.
   */
  public static Optional < User > selectUser(String email) throws Exception {
    log.trace("UserRepo#selectUser({})", email);
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Auth.Users.BY_EMAIL)
    ) {
      ps.setString(1, Validation.nonEmpty(email));

      try (var rs = ps.executeQuery()) {
        if (!rs.next())
          return Optional.empty();

        return Optional.of(UserUtil.newUser(rs));
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
  public static Optional < User > selectUser(String email, String token)
  throws Exception {
    log.trace("UserRepo#selectUser({}, ********)", email);
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Auth.Users.BY_TOKEN)
    ) {
      ps.setString(1, Validation.nonEmpty(email));
      ps.setString(2, Validation.nonEmpty(token));

      try (var rs = ps.executeQuery()) {
        if (!rs.next())
          return Optional.empty();

        return Optional.of(UserUtil.newUser(rs));
      }
    }
  }

  public static Optional < User > selectUser(long userId) throws Exception {
    log.trace("UserRepo#selectUser({})", userId);
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Auth.Users.BY_ID)
    ) {
      ps.setLong(1, userId);

      try (var rs = ps.executeQuery()) {
        if (!rs.next())
          return Optional.empty();

        return Optional.of(UserUtil.newUser(rs));
      }
    }
  }
}
