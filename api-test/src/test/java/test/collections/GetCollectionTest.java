package test.collections;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import test.AuthTestBase;
import test.CollectionResponse;
import test.TestUtil;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("GET /idSetCollections/{id}")
public class GetCollectionTest extends AuthTestBase
{
  public static final String API_PATH = CollectionUtil.API_PATH + "/{id}";

  private static final String API_URL = makeUrl(API_PATH);

  private String collectionName;
  private long   collectionId;

  @Override
  @BeforeEach
  protected void setUp() throws Exception {
    super.setUp();

    collectionName = TestUtil.randStr();
    collectionId   = CollectionUtil.createCollection(collectionName, user.getUserId());
  }

  @Nested
  @DisplayName("by id")
  class ById
  {
    @Nested
    @DisplayName("with an invalid value")
    class Invalid
    {
      @Test
      @DisplayName("returns a 404 error")
      void test1() {
        given()
          .header("Authorization", authHeader())
          .when()
          .get(API_URL, 0)
          .then()
          .statusCode(404)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("with a valid value")
    class Valid
    {
      @Nested
      @DisplayName("and invalid user credentials")
      class BadCreds
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given()
            .header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr()))
            .when()
            .get(API_URL, collectionId)
            .then()
            .statusCode(401)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("and user admin credentials")
      class AdminCreds
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given()
            .header("Authorization", adminAuthHeader())
            .when()
            .get(API_URL, collectionId)
            .then()
            .statusCode(401)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("and no user credentials")
      class NoCreds
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          when()
            .get(API_URL, collectionId)
            .then()
            .statusCode(401)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("and valid user credentials")
      class GoodCreds
      {
        @Test
        @DisplayName("returns the requested ID set collection")
        void test1() {
          var res = given()
            .header("Authorization", authHeader())
            .when()
            .get(API_URL, collectionId);

          res.then()
            .statusCode(200)
            .contentType(ContentType.JSON);

          var body = res.as(CollectionResponse.class);

          assertEquals(collectionId, body.getCollectionId());
          assertEquals(collectionName, body.getName());
          assertEquals(user.getUserId(), body.getCreatedBy());
        }
      }
    }
  }
}