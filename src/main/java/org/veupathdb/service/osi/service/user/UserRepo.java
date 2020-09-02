package org.veupathdb.service.osi.service.user;

import java.util.Optional;

import io.vulpine.lib.query.util.basic.BasicPreparedReadQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.veupathdb.service.osi.db.SQL;
import org.veupathdb.service.osi.model.db.NewUser;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.util.QueryUtil;
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
  public static User insertNewUser(final NewUser user) throws Exception {
    log.trace("UserRepo#insertNewUser(NewUser)");

    return new BasicPreparedReadQuery <>(
      SQL.Insert.Auth.NEW_USER,
      DbMan::connection,
      QueryUtil.must(rs -> UserUtil.newUser(rs, user)),
      ps -> {
        ps.setString(1, user.getUserName());
        ps.setString(2, user.getApiKey());
      }
    ).execute().getValue();
  }

  /**
   * Looks up a user record by email alone.
   * <p>
   * WARNING: This method should not be used for authentication in any way, this
   * is for secondary internal tasks only.
   *
   * @param name User email address to search by
   *
   * @return An option containing either a user record or nothing if no such
   * record was found.
   */
  public static Optional < User > selectUser(final String name)
  throws Exception {
    log.trace("UserRepo#selectUser(String)");

    return new BasicPreparedReadQuery<>(
      SQL.Select.Auth.Users.BY_NAME,
      DbMan::connection,
      QueryUtil.option(UserUtil::newUser),
      QueryUtil.singleString(name)
    ).execute().getValue();
  }

  public static Optional < User > selectUser(final long userId)
  throws Exception {
    log.trace("UserRepo#selectUser(long)");

    return new BasicPreparedReadQuery<>(
      SQL.Select.Auth.Users.BY_ID,
      DbMan::connection,
      QueryUtil.option(UserUtil::newUser),
      QueryUtil.singleId(userId)
    ).execute().getValue();
  }
}
