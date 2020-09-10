package test.organisms;

import java.time.*;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import test.OrganismResponse;
import test.TestBase;
import test.TestUtil;
import test.auth.AuthUtil;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("GET /organisms/{id}")
public class GetOrganismTest extends TestBase
{
  private static final String API_PATH = OrganismUtil.API_PATH + "/{id}";

  private String username;
  private String password;
  private long userId;

  private String orgName;
  private String orgTemp;
  private long orgId;

  @BeforeEach
  protected void setUp() throws Exception {
    super.setUp();

    username = TestUtil.randStr();
    password = TestUtil.randStr();
    userId   = AuthUtil.createUser(username, password);

    orgName = TestUtil.randStr();
    orgTemp = TestUtil.randStr().substring(0, 30) + "%d";
    orgId   = OrganismUtil.createOrganism(orgName, orgTemp, userId);
  }

  @Nested
  @DisplayName("given an invalid organism name")
  class InvalidName
  {
    @Test
    @DisplayName("returns a 404 error")
    void test1() {
      given()
        .header("Authorization", authHeader(username, password))
      .when()
        .get(makeUrl(API_PATH), TestUtil.randStr())
      .then()
        .contentType(ContentType.JSON)
        .statusCode(404);
    }
  }

  @Nested
  @DisplayName("given a valid organism name")
  class ValidName
  {
    @Nested
    @DisplayName("and invalid user credentials")
    class BadUser
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr()))
        .when()
          .get(makeUrl(API_PATH), orgName)
        .then()
          .contentType(ContentType.JSON)
          .statusCode(401);
      }
    }

    @Nested
    @DisplayName("and no user credentials")
    class NoUser
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        when()
          .get(makeUrl(API_PATH), orgName)
        .then()
          .contentType(ContentType.JSON)
          .statusCode(401);
      }
    }

    @Nested
    @DisplayName("and valid user credentials")
    class GoodUser
    {
      @Test
      @DisplayName("returns the requested record")
      void test1() {
        var res = given()
          .header("Authorization", authHeader(username, password))
        .when()
          .get(makeUrl(API_PATH), orgName);

        res.then()
          .contentType(ContentType.JSON)
          .statusCode(200);

        var body = res.as(OrganismResponse.class);

        assertEquals(orgId, body.getOrganismId());
        assertEquals(orgName, body.getOrganismName());
        assertEquals(orgTemp, body.getTemplate());
        assertEquals(1L, body.getGeneIntStart());
        assertEquals(1L, body.getGeneIntCurrent());
        assertEquals(1L, body.getTranscriptIntStart());
        assertEquals(1L, body.getTranscriptIntCurrent());
        assertNotNull(body.getCreatedOn());
        assertEquals(OffsetDateTime.now().getYear(), body.getCreatedOn().getYear());
        assertEquals(OffsetDateTime.now().getMonthValue(), body.getCreatedOn().getMonth().getValue());
        assertEquals(OffsetDateTime.now().getDayOfMonth(), body.getCreatedOn().getDayOfMonth());
        assertEquals(OffsetDateTime.now().getHour(), body.getCreatedOn().getHour());
        assertEquals(userId, body.getCreatedBy());
      }
    }
  }

  @Nested
  @DisplayName("given an invalid organism id")
  class InvalidId
  {
    @Test
    @DisplayName("returns a 404 error")
    void test1() {
      given()
        .header("Authorization", authHeader(username, password))
        .when()
        .get(makeUrl(API_PATH), 0)
        .then()
        .contentType(ContentType.JSON)
        .statusCode(404);
    }
  }

  @Nested
  @DisplayName("given a valid organism id")
  class ValidId
  {
    @Nested
    @DisplayName("and invalid user credentials")
    class BadUser
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr()))
        .when()
          .get(makeUrl(API_PATH), orgId)
        .then()
          .contentType(ContentType.JSON)
          .statusCode(401);
      }
    }

    @Nested
    @DisplayName("and no user credentials")
    class NoUser
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        when()
          .get(makeUrl(API_PATH), orgId)
        .then()
          .contentType(ContentType.JSON)
        .statusCode(401);
      }
    }

    @Nested
    @DisplayName("and valid user credentials")
    class GoodUser
    {
      @Test
      @DisplayName("returns the requested record")
      void test1() {
        var res = given()
          .header("Authorization", authHeader(username, password))
          .when()
          .get(makeUrl(API_PATH), orgId);

        res.then()
          .contentType(ContentType.JSON)
          .statusCode(200);

        var body = res.as(OrganismResponse.class);

        assertEquals(orgId, body.getOrganismId());
        assertEquals(orgName, body.getOrganismName());
        assertEquals(orgTemp, body.getTemplate());
        assertEquals(1L, body.getGeneIntStart());
        assertEquals(1L, body.getGeneIntCurrent());
        assertEquals(1L, body.getTranscriptIntStart());
        assertEquals(1L, body.getTranscriptIntCurrent());
        assertNotNull(body.getCreatedOn());
        assertEquals(OffsetDateTime.now().getYear(), body.getCreatedOn().getYear());
        assertEquals(OffsetDateTime.now().getMonthValue(), body.getCreatedOn().getMonth().getValue());
        assertEquals(OffsetDateTime.now().getDayOfMonth(), body.getCreatedOn().getDayOfMonth());
        assertEquals(OffsetDateTime.now().getHour(), body.getCreatedOn().getHour());
        assertEquals(userId, body.getCreatedBy());
      }
    }
  }
}
