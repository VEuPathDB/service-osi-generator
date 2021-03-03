package test.sets;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import test.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("GET " + IdSetTestBase.ID_PATH)
public class GetIdSetTest extends IdSetTestBase
{
  @Override
  @BeforeEach
  protected void setUp() throws Exception {
    super.setUp();
  }

  @Nested
  @DisplayName("When given")
  class When
  {
    @Nested
    @DisplayName("an invalid ID set ID")
    class Invalid
    {
      @Test
      @DisplayName("returns a 404 error")
      void test1() {
        given()
          .header("Authorization", authHeader())
          .get(ID_URL, 0)
          .then()
          .statusCode(404)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("a valid ID set ID")
    class Valid
    {
      private IdSetResponse response;

      private int genCount;

      @BeforeEach
      void setUp() {
        var rand = new Random();

        genCount = rand.nextInt(95) + 5;
        response = createIdSet(genCount);
      }

      @Nested
      @DisplayName("and no user credentials")
      class T1
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given()
            .get(ID_URL, 0)
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
        void test1() {
          given()
            .header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr()))
            .get(ID_URL, response.getIdSetId())
            .then()
            .statusCode(401)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("and user admin credentials")
      class T3
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given()
            .header("Authorization", adminAuthHeader())
            .get(ID_URL, response.getIdSetId())
            .then()
            .statusCode(401)
            .contentType(ContentType.JSON);
        }
      }

      @Nested
      @DisplayName("and valid user credentials")
      class T4
      {
        @Test
        @DisplayName("returns the desired record")
        void test1() {
          var res = given()
            .header("Authorization", authHeader())
            .get(ID_URL, response.getIdSetId());

          res.then()
            .statusCode(200)
            .contentType(ContentType.JSON);

          var body = res.as(IdSetResponse.class);

          assertEquals(response.getIdSetId(), body.getIdSetId());
          assertEquals(organismId, body.getOrganismId());
          assertEquals(user.getUserId(), body.getCreatedBy());
          assertEquals(response.getCreatedOn(), body.getCreatedOn());
          assertEquals(template, body.getTemplate());
          assertEquals(1, body.getGeneIntStart());
          assertEquals(genCount, body.getGeneratedGeneCount());
          assertEquals(genCount, body.getGeneratedIds().length);

          var expect = response.getGeneratedIds();
          var actual = body.getGeneratedIds();

          Arrays.sort(expect, Comparator.comparing(GeneratedTranscriptEntry::getGeneId));
          Arrays.sort(actual, Comparator.comparing(GeneratedTranscriptEntry::getGeneId));

          for (var i = 0; i < expect.length; i++) {
            assertEquals(expect[i].getGeneId(), actual[i].getGeneId());
          }
        }
      }
    }
  }
}
