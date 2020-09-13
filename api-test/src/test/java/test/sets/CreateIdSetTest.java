package test.sets;

import java.time.OffsetDateTime;
import java.util.Random;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import test.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("POST " + IdSetUtil.API_PATH)
public class CreateIdSetTest extends IdSetTestBase
{
  private static final String API_URL = makeUrl(IdSetUtil.API_PATH);

  @BeforeEach
  protected void setUp() throws Exception {
    super.setUp();
  }

  @Nested
  @DisplayName("When given")
  class When
  {
    @Nested
    @DisplayName("a POST body that")
    class Body
    {
      @Nested
      @DisplayName("is null")
      class Null
      {
        @Test
        @DisplayName("returns a 400 error")
        void test1() {
          given()
            .header("Authorization", authHeader())
            .contentType(ContentType.JSON)
            .body(NullNode.getInstance())
            .when()
            .post(API_URL)
            .then()
            .statusCode(400)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("has a collection id that")
      class ColId
      {
        @Nested
        @DisplayName("is null")
        class T1
        {
          @Test
          @DisplayName("returns a 422 error")
          void test1() {
            var input = new IdSetPostRequest()
              .setCollectionId(null)
              .setOrganismId(organismId)
              .setGenerateGenes(5);

            var res = given()
              .header("Authorization", authHeader())
              .contentType(ContentType.JSON)
              .body(input)
              .when()
              .post(API_URL);

            res.then()
              .statusCode(422)
              .contentType(ContentType.JSON);

            var body = res.as(Error422Response.class);

            assertNotNull(body);
            assertNotNull(body.getErrors());

            var errs = body.getErrors();

            assertNotNull(errs.getByKey());
            assertEquals(1, errs.getByKey().size());

            var byKey = errs.getByKey();

            assertNotNull(byKey.get(IdSetPostRequest.JSON_KEY_COLL_ID));
            assertNotEquals(0, byKey.get(IdSetPostRequest.JSON_KEY_COLL_ID).length);
          }
        }

        @Nested
        @DisplayName("is missing")
        class T2
        {
          @Test
          @DisplayName("returns a 400 error")
          void test1() {
            var input = TestUtil.Json.convertValue(new IdSetPostRequest()
              .setOrganismId(organismId)
              .setGenerateGenes(0), ObjectNode.class)
              .remove(IdSetPostRequest.JSON_KEY_COLL_ID);

            given()
              .header("Authorization", authHeader())
              .contentType(ContentType.JSON)
              .body(input)
              .when()
              .post(API_URL)
              .then()
              .statusCode(400)
              .contentType(ContentType.JSON);
          }
        }

        @Nested
        @DisplayName("is invalid")
        class T3
        {
          @Test
          @DisplayName("returns a 422 error")
          void test1() {
            var input = TestUtil.Json.convertValue(new IdSetPostRequest()
              .setOrganismId(organismId)
              .setGenerateGenes(0), ObjectNode.class)
              .put(IdSetPostRequest.JSON_KEY_COLL_ID, "hello");

            var res = given()
              .header("Authorization", authHeader())
              .contentType(ContentType.JSON)
              .body(input)
              .when()
              .post(API_URL);

            res.then()
              .statusCode(422)
              .contentType(ContentType.JSON);

            var body = res.as(Error422Response.class);

            assertNotNull(body);
            assertNotNull(body.getErrors());

            var errs = body.getErrors();

            assertNotNull(errs.getByKey());
            assertEquals(1, errs.getByKey().size());

            var byKey = errs.getByKey();

            assertNotNull(byKey.get(IdSetPostRequest.JSON_KEY_COLL_ID));
            assertNotEquals(0, byKey.get(IdSetPostRequest.JSON_KEY_COLL_ID).length);
          }
        }
      }

      @Nested
      @DisplayName("has an organism id that")
      class OrgId
      {
        @Nested
        @DisplayName("is null")
        class T1
        {
          @Test
          @DisplayName("returns a 422 error")
          void test1() {
            var input = new IdSetPostRequest()
              .setCollectionId(collectionId)
              .setOrganismId(null)
              .setGenerateGenes(5);

            var res = given()
              .header("Authorization", authHeader())
              .contentType(ContentType.JSON)
              .body(input)
              .when()
              .post(API_URL);

            res.then()
              .statusCode(422)
              .contentType(ContentType.JSON);

            var body = res.as(Error422Response.class);

            assertNotNull(body);
            assertNotNull(body.getErrors());

            var errs = body.getErrors();

            assertNotNull(errs.getByKey());
            assertEquals(1, errs.getByKey().size());

            var byKey = errs.getByKey();

            assertNotNull(byKey.get(IdSetPostRequest.JSON_KEY_ORG_ID));
            assertNotEquals(0, byKey.get(IdSetPostRequest.JSON_KEY_ORG_ID).length);
          }
        }

        @Nested
        @DisplayName("is missing")
        class T2
        {
          @Test
          @DisplayName("returns a 422 error")
          void test1() {
            var input = TestUtil.Json.convertValue(new IdSetPostRequest()
              .setCollectionId(collectionId)
              .setGenerateGenes(0), ObjectNode.class)
              .remove(IdSetPostRequest.JSON_KEY_COLL_ID);

            var res = given()
              .header("Authorization", authHeader())
              .contentType(ContentType.JSON)
              .body(input)
              .when()
              .post(API_URL);

            res.then()
              .statusCode(422)
              .contentType(ContentType.JSON);

            var body = res.as(Error422Response.class);

            assertNotNull(body);
            assertNotNull(body.getErrors());
          }
        }

        @Nested
        @DisplayName("is invalid")
        class T3
        {
          @Test
          @DisplayName("returns a 422 error")
          void test1() {
            var input = TestUtil.Json.convertValue(new IdSetPostRequest()
              .setCollectionId(collectionId)
              .setGenerateGenes(0), ObjectNode.class)
              .put(IdSetPostRequest.JSON_KEY_ORG_ID, "hello");

            var res = given()
              .header("Authorization", authHeader())
              .contentType(ContentType.JSON)
              .body(input)
              .when()
              .post(API_URL);

            res.then()
              .statusCode(422)
              .contentType(ContentType.JSON);

            var body = res.as(Error422Response.class);

            assertNotNull(body);
            assertNotNull(body.getErrors());

            var errs = body.getErrors();

            assertNotNull(errs.getByKey());
            assertEquals(1, errs.getByKey().size());

            var byKey = errs.getByKey();

            assertNotNull(byKey.get(IdSetPostRequest.JSON_KEY_ORG_ID));
            assertNotEquals(0, byKey.get(IdSetPostRequest.JSON_KEY_ORG_ID).length);
          }
        }
      }

      @Nested
      @DisplayName("has a new gene count value that")
      class Count
      {
        @Nested
        @DisplayName("is null")
        class T1
        {
          @Test
          @Disabled(
            "cannot currently verify this.  the generated types are non-nullable and will default to 0")
          @DisplayName("returns a 422 error")
          void test1() {
            var input = new IdSetPostRequest()
              .setCollectionId(collectionId)
              .setOrganismId(organismId)
              .setGenerateGenes(null);

            var res = given()
              .header("Authorization", authHeader())
              .contentType(ContentType.JSON)
              .body(input)
              .when()
              .post(API_URL);

            res.then()
              .statusCode(422)
              .contentType(ContentType.JSON);

            var body = res.as(Error422Response.class);

            assertNotNull(body);
            assertNotNull(body.getErrors());

            var errs = body.getErrors();

            assertNotNull(errs.getByKey());
            assertEquals(1, errs.getByKey().size());

            var byKey = errs.getByKey();

            assertNotNull(byKey.get(IdSetPostRequest.JSON_KEY_GEN_GENES));
            assertNotEquals(0, byKey.get(IdSetPostRequest.JSON_KEY_GEN_GENES).length);
          }
        }

        @Nested
        @DisplayName("is missing")
        class T2
        {
          @Test
          @DisplayName("returns a 400 error")
          void test1() {
            var input = TestUtil.Json.convertValue(new IdSetPostRequest()
              .setCollectionId(collectionId)
              .setOrganismId(organismId), ObjectNode.class)
              .remove(IdSetPostRequest.JSON_KEY_GEN_GENES);

            given()
              .header("Authorization", authHeader())
              .contentType(ContentType.JSON)
              .body(input)
              .when()
              .post(API_URL)
              .then()
              .statusCode(400)
              .contentType(ContentType.JSON);
          }
        }

        @Nested
        @DisplayName("is invalid")
        class T3
        {
          @Test
          @DisplayName("returns a 422 error")
          void test1() {
            var input = TestUtil.Json.convertValue(new IdSetPostRequest()
              .setCollectionId(collectionId)
              .setOrganismId(organismId), ObjectNode.class)
              .put(IdSetPostRequest.JSON_KEY_GEN_GENES, "hello");

            var res = given()
              .header("Authorization", authHeader())
              .contentType(ContentType.JSON)
              .body(input)
              .when()
              .post(API_URL);

            res.then()
              .statusCode(422)
              .contentType(ContentType.JSON);

            var body = res.as(Error422Response.class);

            assertNotNull(body);
            assertNotNull(body.getErrors());

            var errs = body.getErrors();

            assertNotNull(errs.getByKey());
            assertEquals(1, errs.getByKey().size());

            var byKey = errs.getByKey();

            assertNotNull(byKey.get(IdSetPostRequest.JSON_KEY_GEN_GENES));
            assertNotEquals(0, byKey.get(IdSetPostRequest.JSON_KEY_GEN_GENES).length);
          }
        }
      }

      @Nested
      @DisplayName("is valid and credentials that")
      class ValidBody
      {
        @Nested
        @DisplayName("are missing")
        class T1
        {
          @Test
          @DisplayName("returns a 401 error")
          void test1() {
            var input = new IdSetPostRequest()
              .setCollectionId(collectionId)
              .setOrganismId(organismId)
              .setGenerateGenes(0);

            given()
              .contentType(ContentType.JSON)
              .body(input)
              .when()
              .post(API_URL)
              .then()
              .statusCode(401)
              .contentType(ContentType.JSON);
          }
        }

        @Nested
        @DisplayName("are invalid")
        class T2
        {
          @Test
          @DisplayName("returns a 401 error")
          void test1() {
            var input = new IdSetPostRequest()
              .setCollectionId(collectionId)
              .setOrganismId(organismId)
              .setGenerateGenes(0);

            given()
              .header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr()))
              .contentType(ContentType.JSON)
              .body(input)
              .when()
              .post(API_URL)
              .then()
              .statusCode(401)
              .contentType(ContentType.JSON);
          }
        }

        @Nested
        @DisplayName("are for the user admin")
        class T3
        {
          @Test
          @DisplayName("returns a 401 error")
          void test1() {
            var input = new IdSetPostRequest()
              .setCollectionId(collectionId)
              .setOrganismId(organismId)
              .setGenerateGenes(0);

            given()
              .header("Authorization", adminAuthHeader())
              .contentType(ContentType.JSON)
              .body(input)
              .when()
              .post(API_URL)
              .then()
              .statusCode(401)
              .contentType(ContentType.JSON);
          }
        }

        @Nested
        @DisplayName("are valid")
        class T4
        {
          @Test
          @DisplayName("creates a new ID set record")
          void test1() {
            var input = new IdSetPostRequest()
              .setCollectionId(collectionId)
              .setOrganismId(organismId)
              .setGenerateGenes(0);

            var res = given()
              .header("Authorization", authHeader())
              .contentType(ContentType.JSON)
              .body(input)
              .when()
              .post(API_URL);

            res.then()
              .statusCode(200)
              .contentType(ContentType.JSON);

            var body = res.as(IdSetResponse.class);
            var now  = OffsetDateTime.now();

            assertTrue(0 < body.getIdSetId());
            assertEquals(collectionId, body.getCollectionId());
            assertEquals(organismId, body.getOrganismId());
            assertEquals(template, body.getTemplate());
            assertEquals(user.getUserId(), body.getCreatedBy());
            Assert.datesSimilar(now, body.getCreatedOn());
            assertEquals(1, body.getGeneIntStart());
            assertEquals(0, body.getGeneratedGeneCount());
            assertEquals(0, body.getGeneratedIds().length);
          }
        }
      }

      @Nested
      @DisplayName("is valid and has a non-zero gene generation count")
      class GenMulti
      {
        private int generated;

        private IdSetResponse response;

        private OffsetDateTime now;

        @BeforeEach
        void setUp() {
          var rand = new Random();

          generated = rand.nextInt(95 + 5);

          response = createIdSet(new IdSetPostRequest()
            .setCollectionId(collectionId)
            .setOrganismId(organismId)
            .setGenerateGenes(generated));

          now = OffsetDateTime.now();
        }

        @Test
        @DisplayName("it lists those genes in the response output")
        void test1() {

          assertTrue(0 < response.getIdSetId());
          assertEquals(collectionId, response.getCollectionId());
          assertEquals(organismId, response.getOrganismId());
          assertEquals(user.getUserId(), response.getCreatedBy());
          assertEquals(template, response.getTemplate());
          Assert.datesSimilar(now, response.getCreatedOn());
          assertEquals(1, response.getGeneIntStart());
          assertEquals(generated, response.getGeneratedGeneCount());
          assertEquals(generated, response.getGeneratedIds().length);

          var pattern = Pattern.compile("^" + template.replaceAll("%d", "\\\\d+") + "$");
          for (var entry : response.getGeneratedIds()) {
            assertTrue(pattern.matcher(entry.getGeneId()).matches());
            assertEquals(0, entry.getProteins().length);
            assertEquals(0, entry.getTranscripts().length);
          }
        }
      }
    }
  }
}
