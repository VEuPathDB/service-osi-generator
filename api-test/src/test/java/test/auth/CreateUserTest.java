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

@DisplayName("POST /auth")
public class CreateUserTest extends TestBase
{
  @Nested
  @DisplayName("When given an acceptable request")
  class HappyPath
  {
    @Nested
    @DisplayName("with no pre-existing users")
    class Branch1
    {
      @Test
      @DisplayName("returns a new user record")
      void test1() {
        var name = TestUtil.randStr();

        var res = given()
          .header("Authorization", adminAuthHeader())
          .contentType(ContentType.JSON)
          .with()
          .body(AuthUtil.newUserRequest(name))
          .when()
          .post(makeUrl(AuthUtil.API_PATH));

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(JsonNode.class);

        AuthUtil.verifyUserType(body);

        assertEquals(name, body.get(AuthUtil.USERNAME).asText());
      }
    }

    @Nested
    @DisplayName("with pre-existing users")
    class Branch2
    {
      @Test
      @DisplayName("returns a new user record")
      void test1() throws Exception {
        AuthUtil.createUser(TestUtil.randStr(), TestUtil.randStr());

        var name = TestUtil.randStr();

        var res = given()
          .header("Authorization", adminAuthHeader())
          .contentType(ContentType.JSON)
          .with()
          .body(AuthUtil.newUserRequest(name))
          .when()
          .post(makeUrl(AuthUtil.API_PATH));

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(JsonNode.class);

        AuthUtil.verifyUserType(body);

        assertEquals(name, body.get(AuthUtil.USERNAME).asText());
      }
    }
  }

  @Nested
  @DisplayName("When given a null request body")
  class Null
  {
    @Test
    @DisplayName("returns a 400 error")
    void test1() {
      given()
        .header("Authorization", adminAuthHeader())
        .contentType(ContentType.JSON)
        .with()
        .body(NullNode.getInstance())
        .when()
        .post(makeUrl(AuthUtil.API_PATH))
        .then()
        .statusCode(400)
        .contentType(ContentType.JSON);
    }
  }

  @Nested
  @DisplayName("When given no credentials")
  class NoAuth
  {
    @Test
    @DisplayName("returns a 401 error")
    void test1() {
      given()
        .contentType(ContentType.JSON)
        .with()
        .body(AuthUtil.newUserRequest("some user name"))
        .when()
        .post(makeUrl(AuthUtil.API_PATH))
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

      AuthUtil.createUser(name, key);

      given()
        .header("Authorization", authHeader(name, key))
        .contentType(ContentType.JSON)
        .body(AuthUtil.newUserRequest("some user name"))
        .when()
        .post(makeUrl(AuthUtil.API_PATH))
        .then()
        .statusCode(401)
        .contentType(ContentType.JSON);
    }
  }

  @Nested
  @DisplayName("When given a bad request")
  class Bad {
    @Nested
    @DisplayName("with a null name field")
    class Nest1
    {
      @Test
      @DisplayName("returns a 422 error")
      void test1() {
        var res1 = given()
          .header("Authorization", adminAuthHeader())
          .contentType(ContentType.JSON)
          .with()
          .body(AuthUtil.newUserRequest(null))
          .when()
          .post(makeUrl(AuthUtil.API_PATH));
        res1.then()
          .statusCode(422)
          .contentType(ContentType.JSON);
        var body = res1.as(JsonNode.class);

        Assert.Json.contains(body, "byKey");
        Assert.Json.isObject("byKey", body.get("byKey"));
        Assert.Json.contains("byKey", body.get("byKey"), AuthUtil.USERNAME);
        Assert.Json.isArray("byKey." + AuthUtil.USERNAME, body.get("byKey").get(AuthUtil.USERNAME));
      }
    }

    @Nested
    @DisplayName("with an empty name field")
    class Nest2
    {
      @Test
      @DisplayName("returns a 422 error")
      void test1() {
        var res1 = given()
          .header("Authorization", adminAuthHeader())
          .contentType(ContentType.JSON)
          .with()
          .body(AuthUtil.newUserRequest(""))
          .when()
          .post(makeUrl(AuthUtil.API_PATH));
        res1.then()
          .statusCode(422)
          .contentType(ContentType.JSON);
        var body = res1.as(JsonNode.class);

        Assert.Json.contains(body, "byKey");
        Assert.Json.isObject("byKey", body.get("byKey"));
        Assert.Json.contains("byKey", body.get("byKey"), AuthUtil.USERNAME);
        Assert.Json.isArray("byKey." + AuthUtil.USERNAME, body.get("byKey").get(AuthUtil.USERNAME));
      }
    }

    @Nested
    @DisplayName("with a blank name field")
    class Nest3
    {
      @Test
      @DisplayName("returns a 422 error")
      void test1() {
        var res1 = given()
          .header("Authorization", adminAuthHeader())
          .contentType(ContentType.JSON)
          .with()
          .body(AuthUtil.newUserRequest("      "))
          .when()
          .post(makeUrl(AuthUtil.API_PATH));
        res1.then()
          .statusCode(422)
          .contentType(ContentType.JSON);
        var body = res1.as(JsonNode.class);

        Assert.Json.contains(body, "byKey");
        Assert.Json.isObject("byKey", body.get("byKey"));
        Assert.Json.contains("byKey", body.get("byKey"), AuthUtil.USERNAME);
        Assert.Json.isArray("byKey." + AuthUtil.USERNAME, body.get("byKey").get(AuthUtil.USERNAME));
      }
    }
  }
}
