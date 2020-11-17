package test.sets;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import test.GeneratedTranscriptEntry;
import test.IdSetPatchRequest;
import test.IdSetResponse;
import test.TestUtil;
import test.collections.CollectionUtil;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("PATCH " + IdSetTestBase.ID_PATH)
public class UpdateIdSetTest extends IdSetTestBase
{

  private IdSetResponse response;

  @Override
  @BeforeEach
  protected void setUp() throws Exception {
    super.setUp();

    var rand = new Random();

    int geneCount = rand.nextInt(95) + 5;
    response  = createIdSet(geneCount);

    Arrays.sort(response.getGeneratedIds(), this::geneIdComparator);
  }

  @Nested
  @DisplayName("When given a PATCH body that")
  class When
  {
    @Nested
    @DisplayName("is null")
    class Null
    {
      @Test
      @DisplayName("returns a 400 error")
      void test1() {
        given()
          .contentType(ContentType.JSON)
          .header("Authorization", authHeader())
          .body(NullNode.getInstance())
          .when()
          .patch(ID_URL, response.getIdSetId())
          .then()
          .statusCode(400)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("is empty")
    class Empty
    {
      @Test
      @DisplayName("returns a 200")
      void test1() {
        given()
          .contentType(ContentType.JSON)
          .header("Authorization", authHeader())
          .body(new IdSetPatchRequest())
          .when()
          .patch(ID_URL, response.getIdSetId())
          .then()
          .statusCode(200)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("contains an invalid entry that")
    class Invalid
    {
      @Nested
      @DisplayName("has a gene ID that")
      class GeneId
      {
        @Nested
        @DisplayName("is null")
        class T1
        {
          @Test
          @DisplayName("returns a 422 error")
          void test1() {
            var body = new IdSetPatchRequest();

            body.add(new IdSetPatchRequest.Entry().setGeneId(null).setTranscripts(5));

            given()
              .contentType(ContentType.JSON)
              .header("Authorization", authHeader())
              .body(body)
              .when()
              .patch(ID_URL, response.getIdSetId())
              .then()
              .statusCode(422)
              .contentType(ContentType.JSON);
          }
        }

        @Nested
        @DisplayName("is missing")
        class T2
        {
          @Test
          @DisplayName("returns a 422 error")
          void test1() {
            var body = new IdSetPatchRequest();

            body.add(new IdSetPatchRequest.Entry().setGeneId(null).setTranscripts(5));

            var json = TestUtil.Json.convertValue(body, ArrayNode.class);
            ((ObjectNode) json.get(0)).remove(IdSetPatchRequest.Entry.JSON_KEY_GENE_ID);

            given()
              .contentType(ContentType.JSON)
              .header("Authorization", authHeader())
              .body(json)
              .when()
              .patch(ID_URL, response.getIdSetId())
              .then()
              .statusCode(422)
              .contentType(ContentType.JSON);
          }
        }

        @Nested
        @DisplayName("is blank")
        class T3
        {
          @Test
          @DisplayName("returns a 422 error")
          void test1() {
            var body = new IdSetPatchRequest();

            body.add(new IdSetPatchRequest.Entry().setGeneId("    ").setTranscripts(5));

            given()
              .contentType(ContentType.JSON)
              .header("Authorization", authHeader())
              .body(body)
              .when()
              .patch(ID_URL, response.getIdSetId())
              .then()
              .statusCode(422)
              .contentType(ContentType.JSON);
          }
        }
      }

      @Nested
      @DisplayName("has a transcript count value that")
      class Transcripts
      {
        @Nested
        @DisplayName("is less than 0")
        class T3
        {
          @Test
          @DisplayName("returns a 422 error")
          void test1() {
            var body = new IdSetPatchRequest();

            body.add(new IdSetPatchRequest.Entry()
              .setGeneId(response.getGeneratedIds()[0].getGeneId())
              .setTranscripts(-1));

            given()
              .contentType(ContentType.JSON)
              .header("Authorization", authHeader())
              .body(body)
              .when()
              .patch(ID_URL, response.getIdSetId())
              .then()
              .statusCode(422)
              .contentType(ContentType.JSON);
          }
        }
      }
    }

    @Nested
    @DisplayName("is valid and")
    class Valid
    {
      @Nested
      @DisplayName("has no user credentials")
      class T1
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          var body = new IdSetPatchRequest();

          body.add(new IdSetPatchRequest.Entry()
            .setGeneId(response.getGeneratedIds()[0].getGeneId())
            .setTranscripts(5));
          body.add(new IdSetPatchRequest.Entry()
            .setGeneId(response.getGeneratedIds()[1].getGeneId())
            .setTranscripts(10));

          given()
            .contentType(ContentType.JSON)
            .body(body)
            .when()
            .patch(ID_URL, response.getIdSetId())
            .then()
            .statusCode(401)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("has invalid user credentials")
      class T2
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          var body = new IdSetPatchRequest();

          body.add(new IdSetPatchRequest.Entry()
            .setGeneId(response.getGeneratedIds()[0].getGeneId())
            .setTranscripts(5));
          body.add(new IdSetPatchRequest.Entry()
            .setGeneId(response.getGeneratedIds()[1].getGeneId())
            .setTranscripts(10));

          given()
            .contentType(ContentType.JSON)
            .header("Authorization", authHeader("hello", "goodbye"))
            .body(body)
            .when()
            .patch(ID_URL, response.getIdSetId())
            .then()
            .statusCode(401)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("has user admin credentials")
      class T3
      {
        @Test
        @Disabled
        @DisplayName("returns a 401 error")
        void test1() {
          var body = new IdSetPatchRequest();

          body.add(new IdSetPatchRequest.Entry()
            .setGeneId(response.getGeneratedIds()[0].getGeneId())
            .setTranscripts(5));
          body.add(new IdSetPatchRequest.Entry()
            .setGeneId(response.getGeneratedIds()[1].getGeneId())
            .setTranscripts(10));

          given()
            .contentType(ContentType.JSON)
            .header("Authorization", adminAuthHeader())
            .body(body)
            .when()
            .patch(ID_URL, response.getIdSetId())
            .then()
            .statusCode(401)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("has valid user credentials")
      class T4
      {
        @Test
        @DisplayName("creates new transcript ids for the specified genes")
        void test1() {
          var input = new IdSetPatchRequest();
          var sizes = new int[]{5, 10};

          for (var i = 0; i < sizes.length; i++) {
            input.add(new IdSetPatchRequest.Entry()
              .setGeneId(response.getGeneratedIds()[i].getGeneId())
              .setTranscripts(sizes[i]));
          }

          var res = given()
            .contentType(ContentType.JSON)
            .header("Authorization", authHeader())
            .body(input)
            .when()
            .patch(ID_URL, response.getIdSetId());

          res.then()
            .statusCode(200)
            .contentType(ContentType.JSON);

          var body = res.as(IdSetResponse.class);

          var actual = body.getGeneratedIds();

          Arrays.sort(actual, UpdateIdSetTest.this::geneIdComparator);

          verifyEntries(sizes, response.getGeneratedIds(), actual);
        }

        @Test
        @DisplayName("new transcripts appear in parent type responses")
        void test2() {
          var input = new IdSetPatchRequest();
          var sizes = new int[]{5, 10};

          for (var i = 0; i < sizes.length; i++) {
            input.add(new IdSetPatchRequest.Entry()
              .setGeneId(response.getGeneratedIds()[i].getGeneId())
              .setTranscripts(sizes[i]));
          }

          given()
            .contentType(ContentType.JSON)
            .header("Authorization", authHeader())
            .body(input)
            .when()
            .patch(ID_URL, response.getIdSetId())
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);

          var body = CollectionUtil.getCollection(user, collectionId);

          var actual = body.getIdSets()[0].getGeneratedIds();

          Arrays.sort(actual, UpdateIdSetTest.this::geneIdComparator);

          verifyEntries(sizes, response.getGeneratedIds(), actual);
        }

        private void verifyEntries(
          final int[] sizes,
          final GeneratedTranscriptEntry[] expect,
          final GeneratedTranscriptEntry[] actual
        ) {
          var last = 0;

          for (var i = 0; i < sizes.length; i++) {
            assertEquals(expect[i].getGeneId(), actual[i].getGeneId());
            assertEquals(sizes[i], actual[i].getTranscripts().length);
            assertEquals(sizes[i], actual[i].getProteins().length);

            Arrays.sort(
              actual[i].getTranscripts(),
              Comparator.comparing(s -> Integer.parseInt(s.split(".R")[1]))
            );
            Arrays.sort(
              actual[i].getProteins(),
              Comparator.comparing(s -> Integer.parseInt(s.split(".P")[1]))
            );

            for (var j = 0; j < sizes[i]; j++) {
              var id = j + 1 + last;

              assertEquals(expect[i].getGeneId() + ".R" + id, actual[i].getTranscripts()[j]);
              assertEquals(expect[i].getGeneId() + ".P" + id, actual[i].getProteins()[j]);
            }

            last = sizes[i];
          }

        }
      }
    }
  }

  @Nested
  @DisplayName("When patching an empty idSet with a gene previously patched into another idSet")
  class Issue2 // In response to bug ticket #2
  {
    IdSetResponse response1;
    IdSetResponse response2;

    @BeforeEach
    void setUp() {
      response1 = createIdSet(0);
      response2 = createIdSet(0);
    }

    @Test
    @DisplayName("creates a new copy of the external gene for the second idSet")
    void test1() {
      final var geneId = "helloWorld123";

      var requestBody = new IdSetPatchRequest();
      requestBody.add(new IdSetPatchRequest.Entry()
        .setGeneId(geneId)
        .setTranscripts(1));

      // IdSet 1

      var res1 = given()
        .contentType(ContentType.JSON)
        .header("Authorization", authHeader())
        .body(requestBody)
        .when()
        .patch(ID_URL, response1.getIdSetId());

      res1.then()
        .statusCode(200)
        .contentType(ContentType.JSON);

      var actual1 = res1.as(IdSetResponse.class).getGeneratedIds();

      assertEquals(1, actual1.length);
      assertEquals(geneId, actual1[0].getGeneId());
      assertEquals(1, actual1[0].getTranscripts().length);

      // IdSet 2

      var res2 = given()
        .contentType(ContentType.JSON)
        .header("Authorization", authHeader())
        .body(requestBody)
        .when()
        .patch(ID_URL, response2.getIdSetId());

      res2.then()
        .statusCode(200)
        .contentType(ContentType.JSON);

      var actual2 = res2.as(IdSetResponse.class).getGeneratedIds();

      assertEquals(1, actual2.length);
      assertEquals(geneId, actual2[0].getGeneId());
      assertEquals(1, actual2[0].getTranscripts().length);
    }
  }
}
