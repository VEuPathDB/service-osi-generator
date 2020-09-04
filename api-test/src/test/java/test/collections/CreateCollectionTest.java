package test.collections;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import test.TestBase;
import test.TestUtil;
import test.auth.AuthUtil;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("POST /idSetCollections")
public class CreateCollectionTest extends TestBase
{
  @Nested
  @DisplayName("given a valid request body")
  class Valid
  {
    @Nested
    @DisplayName("and no user credentials")
    class NoCreds
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        var name = TestUtil.randStr();

        given()
          .contentType(ContentType.JSON)
          .body(CollectionUtil.createRequest(name))
          .when()
          .post(makeUrl(CollectionUtil.API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("and invalid user credentials")
    class BadCreds
    {
      @Test
      @Disabled
      @DisplayName("returns a 401 error")
      void test1() {
        var name = TestUtil.randStr();

        given()
          .contentType(ContentType.JSON)
          .header("Authorization", authHeader("some user", "some api key"))
          .body(CollectionUtil.createRequest(name))
          .when()
          .post(makeUrl(CollectionUtil.API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("and valid user credentials")
    class GoodCreds
    {
      private String userName;
      private String userPass;
      private long   userId;

      @BeforeEach
      void setUp() throws Exception {
        userName = TestUtil.randStr();
        userPass = TestUtil.randStr();
        userId   = AuthUtil.createUser(userName, userPass);
      }

      @Test
      @DisplayName("returns the created record")
      void test1() {
        var name = TestUtil.randStr();

        var res = given()
          .contentType(ContentType.JSON)
          .header("Authorization", authHeader(userName, userPass))
          .body(CollectionUtil.createRequest(name))
          .when()
          .post(makeUrl(CollectionUtil.API_PATH));
        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);
        var body = res.as(JsonNode.class);

        CollectionUtil.validateCollection(body);
        assertEquals(name, body.get(CollectionUtil.KEY_NAME).textValue());
        assertEquals(userId, body.get(CollectionUtil.KEY_CREATED_BY).asLong());
      }
    }
  }

  @Nested
  @DisplayName("given an invalid request body")
  class Invalid
  {
    @Test
    @Disabled
    @DisplayName("returns a 422 error")
    void test1() {
    }
  }

  @Nested
  @DisplayName("given a null request body")
  class Null
  {
    @Test
    @Disabled
    @DisplayName("returns a 400 error")
    void test1() {
    }
  }
}
