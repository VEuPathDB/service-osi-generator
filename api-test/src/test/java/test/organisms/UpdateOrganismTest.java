package test.organisms;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import test.Error422Response;
import test.OrganismPutRequest;
import test.TestBase;
import test.TestUtil;
import test.auth.AuthUtil;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PUT /organisms/{id}")
public class UpdateOrganismTest extends TestBase
{
  public static final String API_PATH = OrganismUtil.API_PATH + "/{id}";
  public static final String API_URL  = makeUrl(API_PATH);

  private String orgName;
  private String template;
  private long   orgId;
  private String username;
  private String password;
  private long   userId;

  @BeforeEach
  protected void setUp() throws Exception {
    super.setUp();

    username = TestUtil.randStr();
    password = TestUtil.randStr();
    userId   = AuthUtil.createUser(username, password);
    orgName  = TestUtil.randStr();
    template = TestUtil.randStr().substring(0, 28) + "%d";
    orgId    = OrganismUtil.createOrganism(orgName, template, userId);
  }

  @Nested
  @DisplayName("When updating the gene counter")
  class GeneCounter
  {
    @Nested
    @DisplayName("and the organism id is invalid")
    class Err404
    {
      @Test
      @DisplayName("returns a 404 error")
      void test1() {
        given().
          contentType(ContentType.JSON).
          body(new OrganismPutRequest().setGeneIntStart(3L)).
          header("Authorization", authHeader(username, password)).
        when().
          put(API_URL, 0).
        then().
          contentType(ContentType.JSON).
          statusCode(404);
      }
    }

    @Nested
    @DisplayName("and no gene ids have been issued")
    class NoIds
    {
      @Nested
      @DisplayName("given no user credentials")
      class Err401_1
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given().
            contentType(ContentType.JSON).
            body(new OrganismPutRequest().setGeneIntStart(3L)).
            when().
            put(API_URL, orgId).
            then().
            contentType(ContentType.JSON).
            statusCode(401);
        }
      }

      @Nested
      @DisplayName("given invalid user credentials")
      class Err401_2
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given().
            contentType(ContentType.JSON).
            header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr())).
            body(new OrganismPutRequest().setGeneIntStart(3L)).
            when().
            put(API_URL, orgId).
            then().
            contentType(ContentType.JSON).
            statusCode(401);
        }
      }

      @Nested
      @DisplayName("given user admin credentials")
      class Err401_3
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given().
            contentType(ContentType.JSON).
            header("Authorization", adminAuthHeader()).
            body(new OrganismPutRequest().setGeneIntStart(3L)).
            when().
            put(API_URL, orgId).
            then().
            contentType(ContentType.JSON).
            statusCode(401);
        }
      }

      @Nested
      @DisplayName("given valid user credentials")
      class ValidCreds
      {
        @Nested
        @DisplayName("and an invalid gene counter value")
        class Err422
        {
          @Test
          @DisplayName("returns a 422 error describing the issue")
          void test1() {
            var res = given().
              contentType(ContentType.JSON).
              header("Authorization", authHeader(username, password)).
              body(new OrganismPutRequest().setGeneIntStart(0L)).
              when().
              put(API_URL, orgId);

            res.then().statusCode(422).contentType(ContentType.JSON);

            var body = res.as(Error422Response.class);

            assertNotNull(body.getErrors());
            assertNotNull(body.getErrors().getByKey());
            assertFalse(body.getErrors().getByKey().isEmpty());
            assertTrue(body.getErrors().getByKey().containsKey(OrganismPutRequest.KEY_GENE_START));
            assertNotNull(body.getErrors().getByKey().get(OrganismPutRequest.KEY_GENE_START));
            assertNotEquals(
              body.getErrors().getByKey().get(OrganismPutRequest.KEY_GENE_START).length,
              0
            );
          }
        }

        @Nested
        @DisplayName("and a valid gene counter value")
        class Success
        {
          @Test
          @DisplayName("updates the counter starting point")
          void test1() throws Exception {
            given().
              contentType(ContentType.JSON).
              header("Authorization", authHeader(username, password)).
              body(new OrganismPutRequest().setGeneIntStart(3L)).
              when().
              put(API_URL, orgId).
              then().
              statusCode(204);

            var row = OrganismUtil.getOrganism(orgId);
            assertEquals(3L, row.getGeneIntStart());
            assertEquals(3L, row.getGeneIntCurrent());
          }
        }
      }
    }

    @Nested
    @DisplayName("When gene ids have already been issued")
    class WithIds
    {
      @BeforeEach
      void setUp() throws Exception {
        // "Issue" new ids
        OrganismUtil.incrementCounters(orgId);
      }

      @Nested
      @DisplayName("given no user credentials")
      class Err401_1
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given().
            contentType(ContentType.JSON).
            body(new OrganismPutRequest().setGeneIntStart(3L)).
            when().
            put(API_URL, orgId).
            then().
            contentType(ContentType.JSON).
            statusCode(401);
        }
      }

      @Nested
      @DisplayName("given invalid user credentials")
      class Err401_2
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given().
            contentType(ContentType.JSON).
            header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr())).
            body(new OrganismPutRequest().setGeneIntStart(3L)).
            when().
            put(API_URL, orgId).
            then().
            contentType(ContentType.JSON).
            statusCode(401);
        }
      }

      @Nested
      @DisplayName("given user admin credentials")
      class Err401_3
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given().
            contentType(ContentType.JSON).
            header("Authorization", adminAuthHeader()).
            body(new OrganismPutRequest().setGeneIntStart(3L)).
            when().
            put(API_URL, orgId).
            then().
            contentType(ContentType.JSON).
            statusCode(401);
        }
      }

      @Nested
      @DisplayName("given valid user credentials")
      class ValidCreds
      {
        @Nested
        @DisplayName("and an invalid gene counter value")
        class Err422_1
        {
          @Test
          @DisplayName("returns a 422 error describing the issue")
          void test1() {
            var res = given().
              contentType(ContentType.JSON).
              header("Authorization", authHeader(username, password)).
              body(new OrganismPutRequest().setGeneIntStart(0L)).
              when().
              put(API_URL, orgId);

            res.then().statusCode(422).contentType(ContentType.JSON);

            var body = res.as(Error422Response.class);

            assertNotNull(body.getErrors());
            assertNotNull(body.getErrors().getByKey());
            assertFalse(body.getErrors().getByKey().isEmpty());
            assertTrue(body.getErrors().getByKey().containsKey(OrganismPutRequest.KEY_GENE_START));
            assertNotNull(body.getErrors().getByKey().get(OrganismPutRequest.KEY_GENE_START));
            assertNotEquals(
              body.getErrors().getByKey().get(OrganismPutRequest.KEY_GENE_START).length,
              0
            );
          }
        }

        @Nested
        @DisplayName("and a valid gene counter value")
        class Err422_2
        {
          @Test
          @DisplayName("returns a 422 error")
          void test1() throws Exception {
            var res = given().
              contentType(ContentType.JSON).
              header("Authorization", authHeader(username, password)).
              body(new OrganismPutRequest().setGeneIntStart(500L)).
              when().
              put(API_URL, orgId);

            res.then().statusCode(422).contentType(ContentType.JSON);

            var body = res.as(Error422Response.class);

            assertNotNull(body.getErrors());
            assertNotNull(body.getErrors().getByKey());
            assertFalse(body.getErrors().getByKey().isEmpty());
            assertTrue(body.getErrors().getByKey().containsKey(OrganismPutRequest.KEY_GENE_START));
            assertNotNull(body.getErrors().getByKey().get(OrganismPutRequest.KEY_GENE_START));
            assertNotEquals(
              body.getErrors().getByKey().get(OrganismPutRequest.KEY_GENE_START).length,
              0
            );
          }
        }
      }
    }
  }

  @Nested
  @DisplayName("When updating the transcript counter")
  class TranscriptCounter
  {
    @Nested
    @DisplayName("and the organism id is invalid")
    class Err404
    {
      @Test
      @DisplayName("returns a 404 error")
      void test1() {
        given().
          contentType(ContentType.JSON).
          body(new OrganismPutRequest().setGeneIntStart(3L)).
          header("Authorization", authHeader(username, password)).
          when().
          put(API_URL, 0).
          then().
          contentType(ContentType.JSON).
          statusCode(404);
      }
    }

    @Nested
    @DisplayName("and no transcript ids have been issued")
    class NoIds
    {
      @Nested
      @DisplayName("given no user credentials")
      class Err401_1
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given().
            contentType(ContentType.JSON).
            body(new OrganismPutRequest().setTranscriptIntStart(3L)).
            when().
            put(API_URL, orgId).
            then().
            contentType(ContentType.JSON).
            statusCode(401);
        }
      }

      @Nested
      @DisplayName("given invalid user credentials")
      class Err401_2
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given().
            contentType(ContentType.JSON).
            header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr())).
            body(new OrganismPutRequest().setTranscriptIntStart(3L)).
            when().
            put(API_URL, orgId).
            then().
            contentType(ContentType.JSON).
            statusCode(401);
        }
      }

      @Nested
      @DisplayName("given user admin credentials")
      class Err401_3
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given().
            contentType(ContentType.JSON).
            header("Authorization", adminAuthHeader()).
            body(new OrganismPutRequest().setTranscriptIntStart(3L)).
            when().
            put(API_URL, orgId).
            then().
            contentType(ContentType.JSON).
            statusCode(401);
        }
      }

      @Nested
      @DisplayName("given valid user credentials")
      class ValidCreds
      {
        @Nested
        @DisplayName("and an invalid transcript counter value")
        class Err422
        {
          @Test
          @DisplayName("returns a 422 error describing the issue")
          void test1() {
            var res = given().
              contentType(ContentType.JSON).
              header("Authorization", authHeader(username, password)).
              body(new OrganismPutRequest().setTranscriptIntStart(0L)).
              when().
              put(API_URL, orgId);

            res.then().statusCode(422).contentType(ContentType.JSON);

            var body = res.as(Error422Response.class);

            assertNotNull(body.getErrors());
            assertNotNull(body.getErrors().getByKey());
            assertFalse(body.getErrors().getByKey().isEmpty());
            assertTrue(body.getErrors().getByKey().containsKey(OrganismPutRequest.KEY_TRAN_START));
            assertNotNull(body.getErrors().getByKey().get(OrganismPutRequest.KEY_TRAN_START));
            assertNotEquals(
              body.getErrors().getByKey().get(OrganismPutRequest.KEY_TRAN_START).length,
              0
            );
          }
        }

        @Nested
        @DisplayName("and a valid transcript counter value")
        class Success
        {
          @Test
          @DisplayName("updates the counter starting point")
          void test1() throws Exception {
            given().
              contentType(ContentType.JSON).
              header("Authorization", authHeader(username, password)).
              body(new OrganismPutRequest().setTranscriptIntStart(3L)).
              when().
              put(API_URL, orgId).
              then().
              statusCode(204);

            var row = OrganismUtil.getOrganism(orgId);
            assertEquals(3L, row.getTranscriptIntStart());
            assertEquals(3L, row.getTranscriptIntCurrent());
          }
        }
      }
    }

    @Nested
    @DisplayName("When transcript ids have already been issued")
    class WithIds
    {
      @BeforeEach
      void setUp() throws Exception {
        // "Issue" new ids
        OrganismUtil.incrementCounters(orgId);
      }

      @Nested
      @DisplayName("given no user credentials")
      class Err401_1
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given().
            contentType(ContentType.JSON).
            body(new OrganismPutRequest().setTranscriptIntStart(3L)).
            when().
            put(API_URL, orgId).
            then().
            contentType(ContentType.JSON).
            statusCode(401);
        }
      }

      @Nested
      @DisplayName("given invalid user credentials")
      class Err401_2
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given().
            contentType(ContentType.JSON).
            header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr())).
            body(new OrganismPutRequest().setTranscriptIntStart(3L)).
            when().
            put(API_URL, orgId).
            then().
            contentType(ContentType.JSON).
            statusCode(401);
        }
      }

      @Nested
      @DisplayName("given user admin credentials")
      class Err401_3
      {
        @Test
        @DisplayName("returns a 401 error")
        void test1() {
          given().
            contentType(ContentType.JSON).
            header("Authorization", adminAuthHeader()).
            body(new OrganismPutRequest().setTranscriptIntStart(3L)).
            when().
            put(API_URL, orgId).
            then().
            contentType(ContentType.JSON).
            statusCode(401);
        }
      }

      @Nested
      @DisplayName("given valid user credentials")
      class ValidCreds
      {
        @Nested
        @DisplayName("and an invalid transcript counter value")
        class Err422_1
        {
          @Test
          @DisplayName("returns a 422 error describing the issue")
          void test1() {
            var res = given().
              contentType(ContentType.JSON).
              header("Authorization", authHeader(username, password)).
              body(new OrganismPutRequest().setTranscriptIntStart(0L)).
              when().
              put(API_URL, orgId);

            res.then().statusCode(422).contentType(ContentType.JSON);

            var body = res.as(Error422Response.class);

            assertNotNull(body.getErrors());
            assertNotNull(body.getErrors().getByKey());
            assertFalse(body.getErrors().getByKey().isEmpty());
            assertTrue(body.getErrors().getByKey().containsKey(OrganismPutRequest.KEY_TRAN_START));
            assertNotNull(body.getErrors().getByKey().get(OrganismPutRequest.KEY_TRAN_START));
            assertNotEquals(
              body.getErrors().getByKey().get(OrganismPutRequest.KEY_TRAN_START).length,
              0
            );
          }
        }

        @Nested
        @DisplayName("and a valid transcript counter value")
        class Err422_2
        {
          @Test
          @DisplayName("returns a 422 error")
          void test1() throws Exception {
            var res = given().
              contentType(ContentType.JSON).
              header("Authorization", authHeader(username, password)).
              body(new OrganismPutRequest().setTranscriptIntStart(500L)).
              when().
              put(API_URL, orgId);

            res.then().statusCode(422).contentType(ContentType.JSON);

            var body = res.as(Error422Response.class);

            assertNotNull(body.getErrors());
            assertNotNull(body.getErrors().getByKey());
            assertFalse(body.getErrors().getByKey().isEmpty());
            assertTrue(body.getErrors().getByKey().containsKey(OrganismPutRequest.KEY_TRAN_START));
            assertNotNull(body.getErrors().getByKey().get(OrganismPutRequest.KEY_TRAN_START));
            assertNotEquals(
              body.getErrors().getByKey().get(OrganismPutRequest.KEY_TRAN_START).length,
              0
            );
          }
        }
      }
    }  }
}
