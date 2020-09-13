package test.auth;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.JsonNode;
import test.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.TestUtil.Json;

public class AuthUtil
{
  public static final String API_PATH = "/auth";

  public static final String
    API_KEY  = "apiKey",
    ISSUED   = "issued",
    USER_ID  = "userId",
    USERNAME = "username";

  public static JsonNode newUserRequest(final String name) {
    return Json.createObjectNode()
      .put(USERNAME, name);
  }

  private static final String
    INSERT_USER = "INSERT INTO auth.users (user_name, api_key) VALUES (?, ?) RETURNING user_id;";
  public static long createUser(final String name, final String key) throws Exception {
    try (
      var con = DbUtil.getServiceDataSource().getConnection();
      var ps  = con.prepareStatement(INSERT_USER)
    ) {
      ps.setString(1, name);
      ps.setString(2, key);

      try (var rs = ps.executeQuery()) {
        rs.next();
        return rs.getLong(1);
      }
    }
  }

  private static final String SELECT = "SELECT * FROM auth.users WHERE user_id = ?;";

  public static UserRecord getUser(final long userId) throws Exception {
    try (
      var con = DbUtil.getServiceDataSource().getConnection();
      var ps  = con.prepareStatement(SELECT);
    ) {
      ps.setLong(1, userId);

      try (var rs = ps.executeQuery()) {
        rs.next();

        var meta  = rs.getMetaData();
        var count = meta.getColumnCount();
        var out   = new UserRecord();

        for (var i = 1; i <= count; i++) {
          var name = meta.getColumnName(i);

          var field = UserRecord.class.getDeclaredField(name);
          field.setAccessible(true);

          var type = field.getType();
          if (type.equals(Long.class) || type.equals(long.class))
            field.set(out, rs.getLong(i));
          else if (type.equals(String.class))
            field.set(out, rs.getString(i));
          else if (type.equals(OffsetDateTime.class))
            field.set(out, rs.getObject(i, OffsetDateTime.class));
          else
            throw new IllegalStateException("Unrecognized type for field " + name);
        }

        return out;
      }
    }
  }

  public static void verifyUserType(final JsonNode value) {
    assertTrue(value.isObject(), "Response must be a JSON object");

    Assert.Json.contains(value, USER_ID);
    Assert.Json.isIntegral(USER_ID, value.get(USER_ID));
    Assert.Json.isGreaterThan(USER_ID, value.get(USER_ID), 0L);

    Assert.Json.contains(value, USERNAME);
    Assert.Json.isString(USERNAME, value.get(USERNAME));
    Assert.Json.stringNotEmpty(USERNAME, value.get(USERNAME));

    Assert.Json.contains(value, API_KEY);
    Assert.Json.isString(API_KEY, value.get(API_KEY));
    Assert.Json.stringNotEmpty(API_KEY, value.get(API_KEY));
    Assert.Json.stringMinLength(API_KEY, value.get(API_KEY), 32);

    Assert.Json.contains(value, ISSUED);
    Assert.Json.isString(ISSUED, value.get(ISSUED));
    Assert.Json.isIsoDate(ISSUED, value.get(ISSUED));
  }
}
