package test.collections;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.node.NullNode;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import test.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("POST /idSetCollections")
public class CreateCollectionTest extends AuthTestBase
{
  private static final String API_URL = makeUrl(CollectionUtil.API_PATH);

  @Override
  @BeforeEach
  protected void setUp() throws Exception {
    super.setUp();
  }

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
          .body(new CollectionPostRequest().setName(name))
          .when()
          .post(API_URL)
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
      @DisplayName("returns a 401 error")
      void test1() {
        var name = TestUtil.randStr();

        given()
          .contentType(ContentType.JSON)
          .header("Authorization", authHeader("some user", "some api key"))
          .body(new CollectionPostRequest().setName(name))
          .when()
          .post(API_URL)
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
      @DisplayName("returns the created record")
      void test1() {
        var name = TestUtil.randStr();

        var res = given()
          .contentType(ContentType.JSON)
          .header("Authorization", authHeader())
          .body(new CollectionPostRequest().setName(name))
        .when()
          .post(API_URL);

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(CollectionResponse.class);
        var now  = OffsetDateTime.now();

        assertTrue(0 < body.getCollectionId());
        assertEquals(name, body.getName());
        assertEquals(user.getUserId(), body.getCreatedBy());
        assertEquals(now.getYear(), body.getCreatedOn().getYear());
        assertEquals(now.getMonth(), body.getCreatedOn().getMonth());
        assertEquals(now.getDayOfMonth(), body.getCreatedOn().getDayOfMonth());
        assertEquals(now.getHour(), body.getCreatedOn().getHour());
        assertTrue(Math.abs(now.getMinute() - body.getCreatedOn().getMinute()) < 3);
        assertEquals(0, body.getIdSets().length);
      }
    }
  }

  @Nested
  @DisplayName("given an invalid request body")
  class Invalid
  {
    @Nested
    @DisplayName("due to a name value that is too short")
    class Name1 {
      @Test
      @DisplayName("returns a 422 error")
      void test1() {
        var res = given().
          contentType(ContentType.JSON).
          header("Authorization", authHeader()).
          body(new CollectionPostRequest().setName("12")).
        when().
          post(API_URL);

        res.then().
          statusCode(422).
          contentType(ContentType.JSON);

        var body = res.as(Error422Response.class);

        assertNotNull(body);

        var errs = body.getErrors();

        assertNotNull(errs);
        assertNotNull(errs.getByKey());
        assertTrue(errs.getByKey().containsKey(CollectionPostRequest.JSON_KEY_NAME));
        assertNotNull(errs.getByKey().get(CollectionPostRequest.JSON_KEY_NAME));
        assertNotEquals(0, errs.getByKey().get(CollectionPostRequest.JSON_KEY_NAME).length);
        assertFalse(errs.getByKey().get(CollectionPostRequest.JSON_KEY_NAME)[0].isBlank());
      }
    }

    @Nested
    @DisplayName("due to a name value that is null")
    class Name2 {
      @Test
      @DisplayName("returns a 422 error")
      void test1() {
        var res = given().
          contentType(ContentType.JSON).
          header("Authorization", authHeader()).
          body(new CollectionPostRequest().setName(null)).
          when().
          post(API_URL);

        res.then().
          statusCode(422).
          contentType(ContentType.JSON);

        var body = res.as(Error422Response.class);

        assertNotNull(body);

        var errs = body.getErrors();

        assertNotNull(errs);
        assertNotNull(errs.getByKey());
        assertTrue(errs.getByKey().containsKey(CollectionPostRequest.JSON_KEY_NAME));
        assertNotNull(errs.getByKey().get(CollectionPostRequest.JSON_KEY_NAME));
        assertNotEquals(0, errs.getByKey().get(CollectionPostRequest.JSON_KEY_NAME).length);
        assertFalse(errs.getByKey().get(CollectionPostRequest.JSON_KEY_NAME)[0].isBlank());
      }
    }

    @Nested
    @DisplayName("due to a name value that is blank")
    class Name3 {
      @Test
      @DisplayName("returns a 422 error")
      void test1() {
        var res = given().
          contentType(ContentType.JSON).
          header("Authorization", authHeader()).
          body(new CollectionPostRequest().setName("          ")).
          when().
          post(API_URL);

        res.then().
          statusCode(422).
          contentType(ContentType.JSON);

        var body = res.as(Error422Response.class);

        assertNotNull(body);

        var errs = body.getErrors();

        assertNotNull(errs);
        assertNotNull(errs.getByKey());
        assertTrue(errs.getByKey().containsKey(CollectionPostRequest.JSON_KEY_NAME));
        assertNotNull(errs.getByKey().get(CollectionPostRequest.JSON_KEY_NAME));
        assertNotEquals(0, errs.getByKey().get(CollectionPostRequest.JSON_KEY_NAME).length);
        assertFalse(errs.getByKey().get(CollectionPostRequest.JSON_KEY_NAME)[0].isBlank());
      }
    }
  }

  @Nested
  @DisplayName("given a null request body")
  class Null
  {
    @Test
    @DisplayName("returns a 400 error")
    void test1() {
      given().
        contentType(ContentType.JSON).
        header("Authorization", authHeader()).
        body(NullNode.getInstance()).
      when().
        post(API_URL).
      then().
        statusCode(400).
        contentType(ContentType.JSON);
    }
  }
}
