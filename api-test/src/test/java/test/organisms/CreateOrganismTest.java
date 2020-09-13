package test.organisms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import test.AuthTestBase;
import test.OrganismPostRequest;
import test.TestUtil;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.TestUtil.Json;

@DisplayName("POST /organisms")
public class CreateOrganismTest extends AuthTestBase
{
  public static final String API_PATH = OrganismUtil.API_PATH;

  @Override
  @BeforeEach
  protected void setUp() throws Exception {
    super.setUp();
  }

  @Nested
  @DisplayName("given a null request body")
  class Null
  {
    @Test
    @DisplayName("returns a 400 error")
    void test1() throws Exception {
      given()
        .header("Authorization", authHeader())
        .contentType(ContentType.JSON)
        .body(NullNode.getInstance())
      .when()
        .post(makeUrl(API_PATH))
      .then()
        .statusCode(400)
        .contentType(ContentType.JSON);
    }
  }

  @Nested
  @DisplayName("given an invalid request body")
  class Invalid
  {
    @Nested
    @DisplayName("with a name that is")
    class BadName
    {
      @Nested
      @DisplayName("omitted")
      class T1
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {

          var bodyJson = Json.convertValue(
            new OrganismPostRequest()
              .setTemplate("someTemplate%d")
              .setGeneIntStart(1L)
              .setTranscriptIntStart(1L),
            ObjectNode.class);
          bodyJson.remove(OrganismPostRequest.KEY_ORG_NAME);

          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(bodyJson)
          .when()
            .post(makeUrl(API_PATH))
          .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("null")
      class T2
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(Json.convertValue(
              new OrganismPostRequest()
                .setTemplate("someTemplate%d")
                .setGeneIntStart(1L)
                .setTranscriptIntStart(1L),
              ObjectNode.class))
          .when()
            .post(makeUrl(API_PATH))
          .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("too short")
      class T3
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(Json.convertValue(
              new OrganismPostRequest()
                .setOrganismName("hi")
                .setTemplate("someTemplate%d")
                .setGeneIntStart(1L)
                .setTranscriptIntStart(1L),
              ObjectNode.class))
            .when()
            .post(makeUrl(API_PATH))
            .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("empty")
      class T4
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(Json.convertValue(
              new OrganismPostRequest()
                .setOrganismName("")
                .setTemplate("someTemplate%d")
                .setGeneIntStart(1L)
                .setTranscriptIntStart(1L),
              ObjectNode.class))
            .when()
            .post(makeUrl(API_PATH))
            .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("blank")
      class T5
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(Json.convertValue(
              new OrganismPostRequest()
                .setOrganismName("    ")
                .setTemplate("someTemplate%d")
                .setGeneIntStart(1L)
                .setTranscriptIntStart(1L),
              ObjectNode.class))
            .when()
            .post(makeUrl(API_PATH))
            .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }
    }

    @Nested
    @DisplayName("with a template that is")
    class BadTemplate
    {
      @Nested
      @DisplayName("omitted")
      class T1
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          var bodyJson = Json.convertValue(
            new OrganismPostRequest()
              .setOrganismName("someName")
              .setGeneIntStart(1L)
              .setTranscriptIntStart(1L),
            ObjectNode.class);
          bodyJson.remove(OrganismPostRequest.KEY_TEMPLATE);

          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(bodyJson)
          .when()
            .post(makeUrl(API_PATH))
          .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("null")
      class T2
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(Json.convertValue(
              new OrganismPostRequest()
                .setOrganismName("someName")
                .setGeneIntStart(1L)
                .setTranscriptIntStart(1L),
              ObjectNode.class))
          .when()
            .post(makeUrl(API_PATH))
          .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("empty")
      class T4
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(Json.convertValue(
              new OrganismPostRequest()
                .setOrganismName("someName")
                .setTemplate("")
                .setGeneIntStart(1L)
                .setTranscriptIntStart(1L),
              ObjectNode.class))
          .when()
            .post(makeUrl(API_PATH))
          .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("blank")
      class T5
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(Json.convertValue(
              new OrganismPostRequest()
                .setOrganismName("someName")
                .setTemplate("   ")
                .setGeneIntStart(1L)
                .setTranscriptIntStart(1L),
              ObjectNode.class))
            .when()
            .post(makeUrl(API_PATH))
            .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("not valid according to the template pattern requirements")
      class T6
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(Json.convertValue(
              new OrganismPostRequest()
                .setOrganismName("someName")
                .setTemplate("noPattern")
                .setGeneIntStart(1L)
                .setTranscriptIntStart(1L),
              ObjectNode.class))
            .when()
            .post(makeUrl(API_PATH))
            .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }
    }

    @Nested
    @DisplayName("with a gene counter starting value that is")
    class BadGeneCounter
    {
      @Nested
      @DisplayName("omitted")
      class T1
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          var bodyJson = Json.convertValue(
            new OrganismPostRequest()
              .setOrganismName("someName")
              .setTemplate("template%s")
              .setTranscriptIntStart(1L),
            ObjectNode.class);
          bodyJson.remove(OrganismPostRequest.KEY_GENE_START);

          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(bodyJson)
            .when()
            .post(makeUrl(API_PATH))
            .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("null")
      class T2
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(Json.convertValue(
              new OrganismPostRequest()
                .setOrganismName("someName")
                .setTemplate("template%s")
                .setTranscriptIntStart(1L),
              ObjectNode.class))
            .when()
            .post(makeUrl(API_PATH))
            .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("below 1")
      class T3
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(Json.convertValue(
              new OrganismPostRequest()
                .setOrganismName("someName")
                .setTemplate("template%d")
                .setGeneIntStart(0L)
                .setTranscriptIntStart(1L),
              ObjectNode.class))
            .when()
            .post(makeUrl(API_PATH))
            .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }
    }

    @Nested
    @DisplayName("with a transcript counter starting value that is")
    class BadTranscriptCounter
    {
      @Nested
      @DisplayName("omitted")
      class T1
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          var bodyJson = Json.convertValue(
            new OrganismPostRequest()
              .setOrganismName("someName")
              .setTemplate("template%s")
              .setGeneIntStart(1L),
            ObjectNode.class);
          bodyJson.remove(OrganismPostRequest.KEY_TRAN_START);

          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(bodyJson)
            .when()
            .post(makeUrl(API_PATH))
            .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("null")
      class T2
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(Json.convertValue(
              new OrganismPostRequest()
                .setOrganismName("someName")
                .setTemplate("template%s")
                .setGeneIntStart(1L),
              ObjectNode.class))
            .when()
            .post(makeUrl(API_PATH))
            .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("below 1")
      class T3
      {
        @Test
        @DisplayName("returns a 422 error")
        void test1() throws Exception {
          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(Json.convertValue(
              new OrganismPostRequest()
                .setOrganismName("someName")
                .setTemplate("template%d")
                .setGeneIntStart(1L)
                .setTranscriptIntStart(0L),
              ObjectNode.class))
            .when()
            .post(makeUrl(API_PATH))
            .then()
            .statusCode(422)
            .contentType(ContentType.JSON);
        }
      }
    }
  }

  @Nested
  @DisplayName("given a valid request body")
  class Valid
  {
    @Nested
    @DisplayName("and no user credentials")
    class T1
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() throws Exception {
        given()
          .contentType(ContentType.JSON)
          .body(Json.convertValue(
            new OrganismPostRequest()
              .setOrganismName("someName")
              .setTemplate("template%d")
              .setGeneIntStart(1L)
              .setTranscriptIntStart(1L),
            ObjectNode.class))
          .when()
          .post(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("and invalid user credentials")
    class T2
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() throws Exception {
        given()
          .header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr()))
          .contentType(ContentType.JSON)
          .body(Json.convertValue(
            new OrganismPostRequest()
              .setOrganismName("someName")
              .setTemplate("template%d")
              .setGeneIntStart(1L)
              .setTranscriptIntStart(1L),
            ObjectNode.class
          ))
          .when()
          .post(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("and valid user credentials")
    class T3
    {
      @Test
      @DisplayName("returns the id of the new record")
      void test1() throws Exception {
        var res = given()
          .header("Authorization", authHeader())
          .contentType(ContentType.JSON)
          .body(Json.convertValue(
            new OrganismPostRequest()
              .setOrganismName("someName")
              .setTemplate("template%d")
              .setGeneIntStart(1L)
              .setTranscriptIntStart(1L),
            ObjectNode.class
          ))
          .when()
          .post(makeUrl(API_PATH));

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(JsonNode.class);

        assertTrue(body.isIntegralNumber());
        assertTrue(1L <= body.asLong());
      }
    }
  }
}
