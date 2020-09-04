package test.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import test.Assert;
import test.TestBase;
import test.TestUtil;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("GET /auth/{user}")
public class GetUserTest extends TestBase
{
  private static final String TEST_PATH = AuthUtil.API_PATH + "/{id}";

  @Nested
  @DisplayName("When given an invalid user id")
  class BadId
  {
    @Test
    @DisplayName("returns a 404 error")
    void test1() throws Exception {
      var userId = AuthUtil.createUser(TestUtil.randStr(), TestUtil.randStr());

      given()
        .header("Authorization", adminAuthHeader())
        .contentType(ContentType.JSON)
        .with()
        .when()
        .get(makeUrl(TEST_PATH), userId + 1)
        .then()
        .statusCode(404)
        .contentType(ContentType.JSON);
    }
  }

  @Nested
  @DisplayName("When given an invalid user name")
  class BadName
  {
    @Test
    @DisplayName("returns a 404 error")
    void test1() throws Exception {
      AuthUtil.createUser(TestUtil.randStr(), TestUtil.randStr());

      given()
        .header("Authorization", adminAuthHeader())
        .contentType(ContentType.JSON)
        .with()
        .when()
        .get(makeUrl(TEST_PATH), TestUtil.randStr())
        .then()
        .statusCode(404)
        .contentType(ContentType.JSON);
    }
  }

  @Nested
  @DisplayName("When given no credentials")
  class BadAuth
  {
    @Test
    @DisplayName("returns a 401 error")
    void test1() throws Exception {
      AuthUtil.createUser(TestUtil.randStr(), TestUtil.randStr());

      given()
        .contentType(ContentType.JSON)
        .when()
        .get(makeUrl(TEST_PATH), 1)
        .then()
        .statusCode(401)
        .contentType(ContentType.JSON);
    }
  }

  @Nested
  @DisplayName("When given normal user credentials")
  class UserAuth
  {
    @Test
    @DisplayName("returns a 401 error")
    void test1() throws Exception {
      var name = TestUtil.randStr();
      var key  = TestUtil.randStr();

      AuthUtil.createUser(TestUtil.randStr(), TestUtil.randStr());

      given()
        .header("Authorization", authHeader(name, key))
        .contentType(ContentType.JSON)
        .when()
        .get(makeUrl(TEST_PATH), 1)
        .then()
        .statusCode(401)
        .contentType(ContentType.JSON);
    }
  }

  @Nested
  @DisplayName("When given a valid request")
  class HappyPath
  {
    @Nested
    @DisplayName("by user id")
    class Nest1
    {
      @Test
      @DisplayName("returns the requested user")
      void test1() throws Exception {
        var name = TestUtil.randStr();
        var key  = TestUtil.randStr();
        var id   = AuthUtil.createUser(name, key);

        var res = given()
          .header("Authorization", adminAuthHeader())
          .contentType(ContentType.JSON)
          .when()
          .get(makeUrl(TEST_PATH), id);
        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(JsonNode.class);

        AuthUtil.verifyUserType(body);

        System.out.println(body);
        System.out.println(name);
        System.out.println(key);

        assertEquals(id,   body.get(AuthUtil.USER_ID).asLong());
        assertEquals(name, body.get(AuthUtil.USERNAME).textValue());
        assertEquals(key,  body.get(AuthUtil.API_KEY).textValue());
      }
    }

    @Nested
    @DisplayName("by user name")
    class Nest2
    {
      @Test
      @DisplayName("returns the requested user")
      void test1() throws Exception {
        var name = TestUtil.randStr();
        var key  = TestUtil.randStr();
        var id   = AuthUtil.createUser(name, key);

        var res = given()
          .header("Authorization", adminAuthHeader())
          .contentType(ContentType.JSON)
          .when()
          .get(makeUrl(TEST_PATH), name);
        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(JsonNode.class);

        AuthUtil.verifyUserType(body);

        assertEquals(id, body.get(AuthUtil.USER_ID).asLong());
        assertEquals(name, body.get(AuthUtil.USERNAME).textValue());
        assertEquals(key, body.get(AuthUtil.API_KEY).textValue());
      }
    }
  }
}
