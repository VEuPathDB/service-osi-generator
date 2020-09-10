package test.collections;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Comparator;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import test.Assert;
import test.CollectionResponse;
import test.TestBase;
import test.TestUtil;
import test.auth.AuthUtil;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("GET /idSetCollections")
public class SearchCollectionsTest extends TestBase
{
  private static final String API_URL = makeUrl(CollectionUtil.API_PATH);

  private static final String[] collectionNames = {
    "collection1",
    "collection2",
    "setCollection1",
    "setCollection2",
  };

  private static final OffsetDateTime[] collectionCreateds = {
    OffsetDateTime.of(2020, 10, 31, 23, 59, 59, 0, ZoneOffset.UTC),
    OffsetDateTime.of(2019, 10, 31, 23, 59, 59, 0, ZoneOffset.UTC),
    OffsetDateTime.of(2018, 10, 31, 23, 59, 59, 0, ZoneOffset.UTC),
    OffsetDateTime.of(2017, 10, 31, 23, 59, 59, 0, ZoneOffset.UTC),
  };

  private final long[] collectionIds = new long[collectionNames.length];

  private final String[][] users = new String[2][];

  private final long[] userIds = new long[users.length];

  @Override
  @BeforeEach
  protected void setUp() throws Exception {
    super.setUp();

    users[0] = new String[] {TestUtil.randStr(), TestUtil.randStr()};
    users[1] = new String[] {TestUtil.randStr(), TestUtil.randStr()};

    for (var i = 0; i < users.length; i++)
         userIds[i] = AuthUtil.createUser(users[i][0], users[i][1]);
    for (var i = 0; i < collectionNames.length; i++)
         collectionIds[i] = CollectionUtil.createCollection(
           collectionNames[i],
           userIds[i % 2],
           collectionCreateds[i]
         );
  }

  @Nested
  @DisplayName("With a name query")
  class Name
  {
    @Nested
    @DisplayName("containing a wildcard")
    class Wildcard
    {
      @Nested
      @DisplayName("on the left")
      class Left
      {
        @Test
        @DisplayName("returns values matching the pattern")
        void test1() {
          // Matching on the pattern *1 which should return collections 0 and 2.

          var res = given()
            .header("Authorization", authHeader(users[0][0], users[0][1]))
            .queryParam(CollectionUtil.QUERY_NAME, "*1")
            .when()
            .get(API_URL);

          res.then()
            .statusCode(200)
            .contentType(ContentType.JSON);

          var body = res.as(CollectionResponse[].class);

          assertEquals(2, body.length);

          // The order of the results isn't known, so we need to determine which
          // result we are comparing to each expected collection.
          var A = 0;
          var B = 0;

          if (body[0].getCollectionId() == collectionIds[0])
            B = 2;
          else
            A = 2;

          assertEquals(collectionIds[A], body[0].getCollectionId());
          assertEquals(collectionNames[A], body[0].getName());
          assertEquals(userIds[A % 2], body[0].getCreatedBy());
          Assert.datesSimilar(collectionCreateds[A], body[0].getCreatedOn());

          assertEquals(collectionIds[B], body[1].getCollectionId());
          assertEquals(collectionNames[B], body[1].getName());
          assertEquals(userIds[B % 2], body[1].getCreatedBy());
          Assert.datesSimilar(collectionCreateds[B], body[1].getCreatedOn());
        }

        @Test
        @DisplayName("returns nothing if no records match the pattern")
        void test2() {
          // Matching on the pattern *7 which should return nothing.

          var res = given()
            .header("Authorization", authHeader(users[0][0], users[0][1]))
            .queryParam(CollectionUtil.QUERY_NAME, "*7")
            .when()
            .get(API_URL);

          res.then()
            .statusCode(200)
            .contentType(ContentType.JSON);

          var body = res.as(CollectionResponse[].class);

          assertEquals(0, body.length);
        }
      }

      @Nested
      @DisplayName("on the right")
      class Right
      {
        @Test
        @DisplayName("returns values matching the pattern")
        void test1() {
          // Matching on the pattern set* which should return collections 2 and 3.

          var res = given()
            .header("Authorization", authHeader(users[0][0], users[0][1]))
            .queryParam(CollectionUtil.QUERY_NAME, "set*")
            .when()
            .get(API_URL);

          res.then()
            .statusCode(200)
            .contentType(ContentType.JSON);

          var body = res.as(CollectionResponse[].class);

          assertEquals(2, body.length);

          // The order of the results isn't known, so we need to determine which
          // result we are comparing to each expected collection.
          var A = 2;
          var B = 2;

          if (body[0].getCollectionId() == collectionIds[A])
            B = 3;
          else
            A = 3;

          assertEquals(collectionIds[A], body[0].getCollectionId());
          assertEquals(collectionNames[A], body[0].getName());
          assertEquals(userIds[A % 2], body[0].getCreatedBy());
          Assert.datesSimilar(collectionCreateds[A], body[0].getCreatedOn());

          assertEquals(collectionIds[B], body[1].getCollectionId());
          assertEquals(collectionNames[B], body[1].getName());
          assertEquals(userIds[B % 2], body[1].getCreatedBy());
          Assert.datesSimilar(collectionCreateds[B], body[1].getCreatedOn());
        }

        @Test
        @DisplayName("returns nothing if no records match the pattern")
        void test2() {
          // Matching on the pattern apples* which should return nothing.

          var res = given()
            .header("Authorization", authHeader(users[0][0], users[0][1]))
            .queryParam(CollectionUtil.QUERY_NAME, "apples*")
            .when()
            .get(API_URL);

          res.then()
            .statusCode(200)
            .contentType(ContentType.JSON);

          var body = res.as(CollectionResponse[].class);

          assertEquals(0, body.length);
        }
      }

      @Nested
      @DisplayName("in the middle")
      class Middle
      {
        @Test
        @DisplayName("returns values matching the pattern")
        void test1() {
          // Matching on the pattern c*1 which should return collection 0.

          var res = given()
            .header("Authorization", authHeader(users[0][0], users[0][1]))
            .queryParam(CollectionUtil.QUERY_NAME, "c*1")
            .when()
            .get(API_URL);

          res.then()
            .statusCode(200)
            .contentType(ContentType.JSON);

          var body = res.as(CollectionResponse[].class);

          assertEquals(1, body.length);

          assertEquals(collectionIds[0], body[0].getCollectionId());
          assertEquals(collectionNames[0], body[0].getName());
          assertEquals(userIds[0], body[0].getCreatedBy());
          Assert.datesSimilar(collectionCreateds[0], body[0].getCreatedOn());
        }

        @Test
        @DisplayName("returns nothing if no records match the pattern")
        void test2() {
          // Matching on the pattern a*b which should return nothing.

          var res = given()
            .header("Authorization", authHeader(users[0][0], users[0][1]))
            .queryParam(CollectionUtil.QUERY_NAME, "a*b")
            .when()
            .get(API_URL);

          res.then()
            .statusCode(200)
            .contentType(ContentType.JSON);

          var body = res.as(CollectionResponse[].class);

          assertEquals(0, body.length);
        }
      }
    }

    @Nested
    @DisplayName("containing multiple wildcards")
    class Wildcards
    {
      @Test
      @DisplayName("returns values matching the pattern")
      void test1() {
        // Matching on the pattern s*n* which should return collections 2 and 3.

        var res = given()
          .header("Authorization", authHeader(users[0][0], users[0][1]))
          .queryParam(CollectionUtil.QUERY_NAME, "s*n*")
          .when()
          .get(API_URL);

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(CollectionResponse[].class);

        assertEquals(2, body.length);

        // The order of the results isn't known, so we need to determine which
        // result we are comparing to each expected collection.
        var A = 2;
        var B = 2;

        if (body[0].getCollectionId() == collectionIds[A])
          B = 3;
        else
          A = 3;

        assertEquals(collectionIds[A], body[0].getCollectionId());
        assertEquals(collectionNames[A], body[0].getName());
        assertEquals(userIds[A % 2], body[0].getCreatedBy());
        Assert.datesSimilar(collectionCreateds[A], body[0].getCreatedOn());

        assertEquals(collectionIds[B], body[1].getCollectionId());
        assertEquals(collectionNames[B], body[1].getName());
        assertEquals(userIds[B % 2], body[1].getCreatedBy());
        Assert.datesSimilar(collectionCreateds[B], body[1].getCreatedOn());
      }

      @Test
      @DisplayName("returns nothing if no records match the pattern")
      void test2() {
        // Matching on the pattern s*s* which should return nothing.

        var res = given()
          .header("Authorization", authHeader(users[0][0], users[0][1]))
          .queryParam(CollectionUtil.QUERY_NAME, "s*s*")
          .when()
          .get(API_URL);

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(CollectionResponse[].class);

        assertEquals(0, body.length);
      }
    }

    @Nested
    @DisplayName("containing no wildcards")
    class NoWildcard
    {
      @Test
      @DisplayName("returns values matching the pattern")
      void test1() {
        var res = given()
          .header("Authorization", authHeader(users[0][0], users[0][1]))
          .queryParam(CollectionUtil.QUERY_NAME, collectionNames[3])
          .when()
          .get(API_URL);

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(CollectionResponse[].class);

        assertEquals(1, body.length);

        compare(3, 0, body);
      }

      @Test
      @DisplayName("returns nothing if no records match the pattern")
      void test2() {
        var res = given()
          .header("Authorization", authHeader(users[0][0], users[0][1]))
          .queryParam(CollectionUtil.QUERY_NAME, collectionNames[0] + "1")
          .when()
          .get(API_URL);

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(CollectionResponse[].class);

        assertEquals(0, body.length);
      }
    }
  }

  @Nested
  @DisplayName("With a created after query")
  class CreatedAfter
  {
    @Test
    @DisplayName("returns values matching the pattern")
    void test1() {
      // should match 0, 1, and 2

      var res = given()
        .header("Authorization", authHeader(users[0][0], users[0][1]))
        .queryParam(
          CollectionUtil.QUERY_AFTER,
          OffsetDateTime.of(2018, 9, 18, 3, 3, 3, 3, ZoneOffset.UTC).toEpochSecond()
        )
        .when()
        .get(API_URL);

      res.then()
        .statusCode(200)
        .contentType(ContentType.JSON);

      var body = res.as(CollectionResponse[].class);

      assertEquals(3, body.length);

      Arrays.sort(body, Comparator.comparing(CollectionResponse::getCollectionId));

      compare(0, 0, body);
      compare(1, 1, body);
      compare(2, 2, body);
    }

    @Test
    @DisplayName("returns nothing if no records match the pattern")
    void test2() {
      var res = given()
        .header("Authorization", authHeader(users[0][0], users[0][1]))
        .queryParam(
          CollectionUtil.QUERY_AFTER,
          OffsetDateTime.of(2021, 9, 18, 3, 3, 3, 3, ZoneOffset.UTC).toEpochSecond()
        )
        .when()
        .get(API_URL);

      res.then()
        .statusCode(200)
        .contentType(ContentType.JSON);

      var body = res.as(CollectionResponse[].class);

      assertEquals(0, body.length);
    }
  }

  @Nested
  @DisplayName("With a created before query")
  class CreatedBefore
  {
    @Test
    @DisplayName("returns values matching the pattern")
    void test1() {
      // should match 1, 2, and 3

      var res = given()
        .header("Authorization", authHeader(users[0][0], users[0][1]))
        .queryParam(
          CollectionUtil.QUERY_BEFORE,
          OffsetDateTime.of(2020, 9, 18, 3, 3, 3, 3, ZoneOffset.UTC).toEpochSecond()
        )
        .when()
        .get(API_URL);

      res.then()
        .statusCode(200)
        .contentType(ContentType.JSON);

      var body = res.as(CollectionResponse[].class);

      assertEquals(3, body.length);

      Arrays.sort(body, Comparator.comparing(CollectionResponse::getCollectionId));

      compare(1, 0, body);
      compare(2, 1, body);
      compare(3, 2, body);
    }

    @Test
    @DisplayName("returns nothing if no records match the pattern")
    void test2() {
      var res = given()
        .header("Authorization", authHeader(users[0][0], users[0][1]))
        .queryParam(
          CollectionUtil.QUERY_BEFORE,
          OffsetDateTime.of(2015, 9, 18, 3, 3, 3, 3, ZoneOffset.UTC).toEpochSecond()
        )
        .when()
        .get(API_URL);

      res.then()
        .statusCode(200)
        .contentType(ContentType.JSON);

      var body = res.as(CollectionResponse[].class);

      assertEquals(0, body.length);
    }
  }

  @Nested
  @DisplayName("With a user query")
  class User
  {
    @Nested
    @DisplayName("using the user's name")
    class Name
    {
      @Test
      @DisplayName("returns only values created by the user with the given name")
      void test1() {
        // should match 0, 2

        var res = given()
          .header("Authorization", authHeader(users[0][0], users[0][1]))
          .queryParam(CollectionUtil.QUERY_USER, users[0][0])
          .when()
          .get(API_URL);

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(CollectionResponse[].class);

        assertEquals(2, body.length);

        Arrays.sort(body, Comparator.comparing(CollectionResponse::getCollectionId));

        compare(0, 0, body);
        compare(2, 1, body);
      }

      @Test
      @DisplayName("returns nothing if no records were created by the given user")
      void test2() {
        var res = given()
          .header("Authorization", authHeader(users[0][0], users[0][1]))
          .queryParam(CollectionUtil.QUERY_USER, TestUtil.randStr())
          .when()
          .get(API_URL);

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(CollectionResponse[].class);

        assertEquals(0, body.length);
      }
    }

    @Nested
    @DisplayName("using the user's id")
    class ID
    {
      @Test
      @DisplayName("returns only values created by the user with the given id")
      void test1() {
        // should match 1, 3

        var res = given()
          .header("Authorization", authHeader(users[0][0], users[0][1]))
          .queryParam(CollectionUtil.QUERY_USER, userIds[1])
          .when()
          .get(API_URL);

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(CollectionResponse[].class);

        assertEquals(2, body.length);

        Arrays.sort(body, Comparator.comparing(CollectionResponse::getCollectionId));

        compare(1, 0, body);
        compare(3, 1, body);
      }

      @Test
      @DisplayName("returns nothing if no records were created by the given user")
      void test2() {
        var res = given()
          .header("Authorization", authHeader(users[0][0], users[0][1]))
          .queryParam(CollectionUtil.QUERY_USER, TestUtil.randStr())
          .when()
          .get(API_URL);

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(CollectionResponse[].class);

        assertEquals(0, body.length);
      }
    }
  }

  void compare(final int local, final int remote, final CollectionResponse[] vals) {
    assertEquals(collectionIds[local],      vals[remote].getCollectionId());
    assertEquals(collectionNames[local],    vals[remote].getName());
    assertEquals(userIds[local % 2],        vals[remote].getCreatedBy());
    assertEquals(collectionCreateds[local], vals[remote].getCreatedOn());
  }
}
