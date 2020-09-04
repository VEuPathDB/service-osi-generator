package test.auth;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.http.ContentType;
import test.Assert;
import test.TestBase;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.TestUtil.*;

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
      var con = TestBase.dataSource.getConnection();
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
