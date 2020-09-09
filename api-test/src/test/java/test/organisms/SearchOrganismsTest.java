package test.organisms;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import test.OrganismResponse;
import test.TestBase;
import test.TestUtil;
import test.auth.AuthUtil;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GET /organisms")
public class SearchOrganismsTest extends TestBase
{
  private static final String API_PATH = OrganismUtil.API_PATH;

  private String username;
  private String password;
  private long   userId;

  @BeforeEach
  void setUp() throws Exception {
    clearDB();
    username = TestUtil.randStr();
    password = TestUtil.randStr();
    userId   = AuthUtil.createUser(username, password);
  }

  @Nested
  @DisplayName("filtered by organism name")
  class T1
  {
    private final String[] orgNames  = {"test1", "test2", "unmatch1"};
    private final String[] templates = new String[orgNames.length];
    private final long[]   orgIds    = new long[orgNames.length];

    @BeforeEach
    void setUp() throws Exception {
      for (var i = 0; i < orgNames.length; i++) {
        orgIds[i] = OrganismUtil.createOrganism(
          orgNames[i],
          templates[i] = orgNames[i] + "%d",
          userId
        );
      }
    }

    @Nested
    @DisplayName("when no credentials are provided")
    class T11
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .queryParam("organismName", "test*")
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when invalid credentials are provided")
    class T12
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr()))
          .queryParam("organismName", "test*")
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when user admin credentials are provided")
    class T13
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .header("Authorization", adminAuthHeader())
          .queryParam("organismName", "test*")
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when valid credentials are provided")
    class T14
    {
      @Nested
      @DisplayName("and the query has a right side wildcard")
      class T141
      {
        @Test
        @DisplayName("returns the expected results")
        void test1() {
          var res = given()
            .header("Authorization", authHeader(username, password))
            .queryParam("organismName", "test*")
            .when()
            .get(makeUrl(API_PATH));

          res.then()
            .statusCode(200)
            .contentType(ContentType.JSON);

          var body = res.as(OrganismResponse[].class);

          assertEquals(2, body.length);

          var A = 0;
          var B = 0;

          // Correct results are 0 and 1, align the results with the inputs
          if (body[0].getOrganismId() == orgIds[1])
            A = 1;
          else
            B = 1;

          assertEquals(orgNames[A], body[0].getOrganismName());
          assertEquals(templates[A], body[0].getTemplate());
          assertEquals(1L, body[0].getGeneIntStart());
          assertEquals(1L, body[0].getGeneIntCurrent());
          assertEquals(1L, body[0].getTranscriptIntStart());
          assertEquals(1L, body[0].getTranscriptIntCurrent());
          assertNotNull(body[0].getCreatedOn());
          assertEquals(OffsetDateTime.now().getYear(), body[0].getCreatedOn().getYear());
          assertEquals(
            OffsetDateTime.now().getMonthValue(),
            body[0].getCreatedOn().getMonth().getValue()
          );
          assertEquals(
            OffsetDateTime.now().getDayOfMonth(),
            body[0].getCreatedOn().getDayOfMonth()
          );
          assertEquals(OffsetDateTime.now().getHour(), body[0].getCreatedOn().getHour());
          assertEquals(userId, body[0].getCreatedBy());

          assertEquals(orgNames[B], body[1].getOrganismName());
          assertEquals(templates[B], body[1].getTemplate());
          assertEquals(1L, body[1].getGeneIntStart());
          assertEquals(1L, body[1].getGeneIntCurrent());
          assertEquals(1L, body[1].getTranscriptIntStart());
          assertEquals(1L, body[1].getTranscriptIntCurrent());
          assertNotNull(body[1].getCreatedOn());
          assertEquals(OffsetDateTime.now().getYear(), body[1].getCreatedOn().getYear());
          assertEquals(
            OffsetDateTime.now().getMonthValue(),
            body[1].getCreatedOn().getMonth().getValue()
          );
          assertEquals(
            OffsetDateTime.now().getDayOfMonth(),
            body[1].getCreatedOn().getDayOfMonth()
          );
          assertEquals(OffsetDateTime.now().getHour(), body[1].getCreatedOn().getHour());
          assertEquals(userId, body[1].getCreatedBy());
        }
      }

      @Nested
      @DisplayName("and the query has a left side wildcard")
      class T142
      {
        @Test
        @DisplayName("returns the expected results")
        void test1() {
          var res = given()
            .header("Authorization", authHeader(username, password))
            .queryParam("organismName", "*1")
            .when()
            .get(makeUrl(API_PATH));

          res.then()
            .statusCode(200)
            .contentType(ContentType.JSON);

          var body = res.as(OrganismResponse[].class);

          assertEquals(2, body.length);

          var A = 0;
          var B = 0;

          // Correct results are 0 and 2, align the results with the inputs
          if (body[0].getOrganismId() == orgIds[2])
            A = 2;
          else
            B = 2;

          assertEquals(orgNames[A], body[0].getOrganismName());
          assertEquals(templates[A], body[0].getTemplate());
          assertEquals(1L, body[0].getGeneIntStart());
          assertEquals(1L, body[0].getGeneIntCurrent());
          assertEquals(1L, body[0].getTranscriptIntStart());
          assertEquals(1L, body[0].getTranscriptIntCurrent());
          assertNotNull(body[0].getCreatedOn());
          assertEquals(OffsetDateTime.now().getYear(), body[0].getCreatedOn().getYear());
          assertEquals(
            OffsetDateTime.now().getMonthValue(),
            body[0].getCreatedOn().getMonth().getValue()
          );
          assertEquals(
            OffsetDateTime.now().getDayOfMonth(),
            body[0].getCreatedOn().getDayOfMonth()
          );
          assertEquals(OffsetDateTime.now().getHour(), body[0].getCreatedOn().getHour());
          assertEquals(userId, body[0].getCreatedBy());

          assertEquals(orgNames[B], body[1].getOrganismName());
          assertEquals(templates[B], body[1].getTemplate());
          assertEquals(1L, body[1].getGeneIntStart());
          assertEquals(1L, body[1].getGeneIntCurrent());
          assertEquals(1L, body[1].getTranscriptIntStart());
          assertEquals(1L, body[1].getTranscriptIntCurrent());
          assertNotNull(body[1].getCreatedOn());
          assertEquals(OffsetDateTime.now().getYear(), body[1].getCreatedOn().getYear());
          assertEquals(
            OffsetDateTime.now().getMonthValue(),
            body[1].getCreatedOn().getMonth().getValue()
          );
          assertEquals(
            OffsetDateTime.now().getDayOfMonth(),
            body[1].getCreatedOn().getDayOfMonth()
          );
          assertEquals(OffsetDateTime.now().getHour(), body[1].getCreatedOn().getHour());
          assertEquals(userId, body[1].getCreatedBy());
        }
      }
    }
  }

  @Nested
  @DisplayName("filtered by records created after")
  class T2
  {
    private final String[]         orgNames  = {"test1", "test2", "unmatch1"};
    private final String[]         templates = new String[orgNames.length];
    private final OffsetDateTime[] dates     = new OffsetDateTime[]{
      OffsetDateTime.of(2020, 10, 31, 11, 59, 59, 0, ZoneOffset.UTC),
      OffsetDateTime.of(2019, 10, 31, 11, 59, 59, 0, ZoneOffset.UTC),
      OffsetDateTime.of(2018, 10, 31, 11, 59, 59, 0, ZoneOffset.UTC),
    };
    private final long[]           orgIds    = new long[orgNames.length];

    @BeforeEach
    void setUp() throws Exception {
      for (var i = 0; i < orgNames.length; i++) {
        orgIds[i] = OrganismUtil.createOrganism(
          orgNames[i],
          templates[i] = orgNames[i] + "%d",
          userId,
          dates[i]
        );
      }
    }

    @Nested
    @DisplayName("when no credentials are provided")
    class T21
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .queryParam(
            "createdAfter",
            OffsetDateTime.of(2018, 12, 18, 3, 12, 14, 0, ZoneOffset.UTC).toEpochSecond()
          )
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when invalid credentials are provided")
    class T22
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr()))
          .queryParam(
            "createdAfter",
            OffsetDateTime.of(2018, 12, 18, 3, 12, 14, 0, ZoneOffset.UTC).toEpochSecond()
          )
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when user admin credentials are provided")
    class T23
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .header("Authorization", adminAuthHeader())
          .queryParam(
            "createdAfter",
            OffsetDateTime.of(2018, 12, 18, 3, 12, 14, 0, ZoneOffset.UTC).toEpochSecond()
          )
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when valid credentials are provided")
    class T24
    {
      @Test
      @DisplayName("returns the expected results")
      void test1() {
        var res = given()
          .header("Authorization", authHeader(username, password))
          .queryParam(
            "createdAfter",
            OffsetDateTime.of(2018, 12, 18, 3, 12, 14, 0, ZoneOffset.UTC).toEpochSecond()
          )
          .when()
          .get(makeUrl(API_PATH));

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(OrganismResponse[].class);

        assertEquals(2, body.length);

        var A = 0;
        var B = 0;

        // Correct results are 0 and 1, align the results with the inputs
        if (body[0].getOrganismId() == orgIds[1])
          A = 1;
        else
          B = 1;

        assertEquals(orgNames[A], body[0].getOrganismName());
        assertEquals(templates[A], body[0].getTemplate());
        assertEquals(1L, body[0].getGeneIntStart());
        assertEquals(1L, body[0].getGeneIntCurrent());
        assertEquals(1L, body[0].getTranscriptIntStart());
        assertEquals(1L, body[0].getTranscriptIntCurrent());
        assertNotNull(body[0].getCreatedOn());
        assertEquals(dates[A].getYear(), body[0].getCreatedOn().getYear());
        assertEquals(dates[A].getMonthValue(), body[0].getCreatedOn().getMonth().getValue());
        assertEquals(dates[A].getDayOfMonth(), body[0].getCreatedOn().getDayOfMonth());
        assertEquals(dates[A].getHour(), body[0].getCreatedOn().getHour());
        assertEquals(userId, body[0].getCreatedBy());

        assertEquals(orgNames[B], body[1].getOrganismName());
        assertEquals(templates[B], body[1].getTemplate());
        assertEquals(1L, body[1].getGeneIntStart());
        assertEquals(1L, body[1].getGeneIntCurrent());
        assertEquals(1L, body[1].getTranscriptIntStart());
        assertEquals(1L, body[1].getTranscriptIntCurrent());
        assertNotNull(body[1].getCreatedOn());
        assertEquals(dates[B].getYear(), body[1].getCreatedOn().getYear());
        assertEquals(dates[B].getMonthValue(), body[1].getCreatedOn().getMonth().getValue());
        assertEquals(dates[B].getDayOfMonth(), body[1].getCreatedOn().getDayOfMonth());
        assertEquals(dates[B].getHour(), body[1].getCreatedOn().getHour());
        assertEquals(userId, body[1].getCreatedBy());
      }
    }
  }

  @Nested
  @DisplayName("filtered by records created before")
  class T3
  {
    private final String[]         orgNames  = {"test1", "test2", "unmatch1"};
    private final String[]         templates = new String[orgNames.length];
    private final OffsetDateTime[] dates     = new OffsetDateTime[]{
      OffsetDateTime.of(2020, 10, 31, 11, 59, 59, 0, ZoneOffset.UTC),
      OffsetDateTime.of(2019, 10, 31, 11, 59, 59, 0, ZoneOffset.UTC),
      OffsetDateTime.of(2018, 10, 31, 11, 59, 59, 0, ZoneOffset.UTC),
    };
    private final long[]           orgIds    = new long[orgNames.length];

    @BeforeEach
    void setUp() throws Exception {
      for (var i = 0; i < orgNames.length; i++) {
        orgIds[i] = OrganismUtil.createOrganism(
          orgNames[i],
          templates[i] = orgNames[i] + "%d",
          userId,
          dates[i]
        );
      }
    }

    @Nested
    @DisplayName("when no credentials are provided")
    class T21
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .queryParam(
            "createdBefore",
            OffsetDateTime.of(2020, 9, 18, 3, 12, 14, 0, ZoneOffset.UTC).toEpochSecond()
          )
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when invalid credentials are provided")
    class T22
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr()))
          .queryParam(
            "createdBefore",
            OffsetDateTime.of(2020, 9, 18, 3, 12, 14, 0, ZoneOffset.UTC).toEpochSecond()
          )
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when user admin credentials are provided")
    class T23
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .header("Authorization", adminAuthHeader())
          .queryParam(
            "createdBefore",
            OffsetDateTime.of(2020, 9, 18, 3, 12, 14, 0, ZoneOffset.UTC).toEpochSecond()
          )
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when valid credentials are provided")
    class T24
    {
      @Test
      @DisplayName("returns the expected results")
      void test1() {
        var res = given()
          .header("Authorization", authHeader(username, password))
          .queryParam(
            "createdBefore",
            OffsetDateTime.of(2020, 9, 18, 3, 12, 14, 0, ZoneOffset.UTC).toEpochSecond()
          )
          .when()
          .get(makeUrl(API_PATH));

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(OrganismResponse[].class);

        assertEquals(2, body.length);

        var A = 1;
        var B = 1;

        // Correct results are 1 and 2, align the results with the inputs
        if (body[0].getOrganismId() == orgIds[2])
          A = 2;
        else
          B = 2;

        assertEquals(orgNames[A], body[0].getOrganismName());
        assertEquals(templates[A], body[0].getTemplate());
        assertEquals(1L, body[0].getGeneIntStart());
        assertEquals(1L, body[0].getGeneIntCurrent());
        assertEquals(1L, body[0].getTranscriptIntStart());
        assertEquals(1L, body[0].getTranscriptIntCurrent());
        assertNotNull(body[0].getCreatedOn());
        assertEquals(dates[A].getYear(), body[0].getCreatedOn().getYear());
        assertEquals(dates[A].getMonthValue(), body[0].getCreatedOn().getMonth().getValue());
        assertEquals(dates[A].getDayOfMonth(), body[0].getCreatedOn().getDayOfMonth());
        assertEquals(dates[A].getHour(), body[0].getCreatedOn().getHour());
        assertEquals(userId, body[0].getCreatedBy());

        assertEquals(orgNames[B], body[1].getOrganismName());
        assertEquals(templates[B], body[1].getTemplate());
        assertEquals(1L, body[1].getGeneIntStart());
        assertEquals(1L, body[1].getGeneIntCurrent());
        assertEquals(1L, body[1].getTranscriptIntStart());
        assertEquals(1L, body[1].getTranscriptIntCurrent());
        assertNotNull(body[1].getCreatedOn());
        assertEquals(dates[B].getYear(), body[1].getCreatedOn().getYear());
        assertEquals(dates[B].getMonthValue(), body[1].getCreatedOn().getMonth().getValue());
        assertEquals(dates[B].getDayOfMonth(), body[1].getCreatedOn().getDayOfMonth());
        assertEquals(dates[B].getHour(), body[1].getCreatedOn().getHour());
        assertEquals(userId, body[1].getCreatedBy());
      }
    }
  }

  @Nested
  @DisplayName("filtered by records created by user id")
  class T4
  {
    private final String[] orgNames  = {"test1", "test2", "unmatch1"};
    private final String[] templates = new String[orgNames.length];
    private final long[]   orgIds    = new long[orgNames.length];
    private final long[]   userIds   = new long[orgNames.length];

    @BeforeEach
    void setUp() throws Exception {
      var user2name = TestUtil.randStr();
      var user2pass = TestUtil.randStr();
      var user2id   = AuthUtil.createUser(user2name, user2pass);

      userIds[0] = userId;
      userIds[1] = userId;
      userIds[2] = user2id;

      for (var i = 0; i < orgNames.length; i++) {
        orgIds[i] = OrganismUtil.createOrganism(
          orgNames[i],
          templates[i] = orgNames[i] + "%d",
          userIds[i]
        );
      }
    }

    @Nested
    @DisplayName("when no credentials are provided")
    class T41
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .queryParam("createdBy", userId)
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when invalid credentials are provided")
    class T42
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr()))
          .queryParam("createdBy", userId)
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when user admin credentials are provided")
    class T43
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .header("Authorization", adminAuthHeader())
          .queryParam("createdBy", userId)
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when valid credentials are provided")
    class T44
    {
      @Test
      @DisplayName("returns the expected results")
      void test1() {
        var res = given()
          .header("Authorization", authHeader(username, password))
          .queryParam("createdBy", userId)
          .when()
          .get(makeUrl(API_PATH));

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(OrganismResponse[].class);

        assertEquals(2, body.length);

        var A = 0;
        var B = 0;

        // Correct results are 0 and 1, align the results with the inputs
        if (body[0].getOrganismId() == orgIds[1])
          A = 1;
        else
          B = 1;

        assertEquals(orgNames[A], body[0].getOrganismName());
        assertEquals(templates[A], body[0].getTemplate());
        assertEquals(1L, body[0].getGeneIntStart());
        assertEquals(1L, body[0].getGeneIntCurrent());
        assertEquals(1L, body[0].getTranscriptIntStart());
        assertEquals(1L, body[0].getTranscriptIntCurrent());
        assertNotNull(body[0].getCreatedOn());
        assertEquals(OffsetDateTime.now().getYear(), body[0].getCreatedOn().getYear());
        assertEquals(
          OffsetDateTime.now().getMonthValue(),
          body[0].getCreatedOn().getMonth().getValue()
        );
        assertEquals(OffsetDateTime.now().getDayOfMonth(), body[0].getCreatedOn().getDayOfMonth());
        assertEquals(OffsetDateTime.now().getHour(), body[0].getCreatedOn().getHour());
        assertEquals(userIds[A], body[0].getCreatedBy());

        assertEquals(orgNames[B], body[1].getOrganismName());
        assertEquals(templates[B], body[1].getTemplate());
        assertEquals(1L, body[1].getGeneIntStart());
        assertEquals(1L, body[1].getGeneIntCurrent());
        assertEquals(1L, body[1].getTranscriptIntStart());
        assertEquals(1L, body[1].getTranscriptIntCurrent());
        assertNotNull(body[1].getCreatedOn());
        assertEquals(OffsetDateTime.now().getYear(), body[1].getCreatedOn().getYear());
        assertEquals(
          OffsetDateTime.now().getMonthValue(),
          body[1].getCreatedOn().getMonth().getValue()
        );
        assertEquals(OffsetDateTime.now().getDayOfMonth(), body[1].getCreatedOn().getDayOfMonth());
        assertEquals(OffsetDateTime.now().getHour(), body[1].getCreatedOn().getHour());
        assertEquals(userIds[B], body[1].getCreatedBy());
      }
    }
  }

  @Nested
  @DisplayName("filtered by records created by user id")
  class T5
  {
    private final String[] orgNames  = {"test1", "test2", "unmatch1"};
    private final String[] templates = new String[orgNames.length];
    private final long[]   orgIds    = new long[orgNames.length];
    private final long[]   userIds   = new long[orgNames.length];
    private final String[] userNames = new String[orgNames.length];
    private       String   user2name;

    @BeforeEach
    void setUp() throws Exception {
      user2name = TestUtil.randStr();
      var user2pass = TestUtil.randStr();
      var user2id   = AuthUtil.createUser(user2name, user2pass);

      userIds[0]   = userId;
      userNames[0] = username;

      userIds[1]   = user2id;
      userNames[1] = user2name;
      userIds[2]   = user2id;
      userNames[2] = user2name;


      for (var i = 0; i < orgNames.length; i++) {
        orgIds[i] = OrganismUtil.createOrganism(
          orgNames[i],
          templates[i] = orgNames[i] + "%d",
          userIds[i]
        );
      }
    }

    @Nested
    @DisplayName("when no credentials are provided")
    class T51
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .queryParam("createdBy", username)
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when invalid credentials are provided")
    class T52
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .header("Authorization", authHeader(TestUtil.randStr(), TestUtil.randStr()))
          .queryParam("createdBy", username)
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when user admin credentials are provided")
    class T53
    {
      @Test
      @DisplayName("returns a 401 error")
      void test1() {
        given()
          .header("Authorization", adminAuthHeader())
          .queryParam("createdBy", username)
          .when()
          .get(makeUrl(API_PATH))
          .then()
          .statusCode(401)
          .contentType(ContentType.JSON);
      }
    }

    @Nested
    @DisplayName("when valid credentials are provided")
    class T54
    {
      @Test
      @DisplayName("returns the expected results")
      void test1() {
        var res = given()
          .header("Authorization", authHeader(username, password))
          .queryParam("createdBy", user2name)
          .when()
          .get(makeUrl(API_PATH));

        res.then()
          .statusCode(200)
          .contentType(ContentType.JSON);

        var body = res.as(OrganismResponse[].class);

        assertEquals(2, body.length);

        var A = 1;
        var B = 1;

        // Correct results are 1 and 2, align the results with the inputs
        if (body[0].getOrganismId() == orgIds[2])
          A = 2;
        else
          B = 2;

        assertEquals(orgNames[A], body[0].getOrganismName());
        assertEquals(templates[A], body[0].getTemplate());
        assertEquals(1L, body[0].getGeneIntStart());
        assertEquals(1L, body[0].getGeneIntCurrent());
        assertEquals(1L, body[0].getTranscriptIntStart());
        assertEquals(1L, body[0].getTranscriptIntCurrent());
        assertNotNull(body[0].getCreatedOn());
        assertEquals(OffsetDateTime.now().getYear(), body[0].getCreatedOn().getYear());
        assertEquals(
          OffsetDateTime.now().getMonthValue(),
          body[0].getCreatedOn().getMonth().getValue()
        );
        assertEquals(OffsetDateTime.now().getDayOfMonth(), body[0].getCreatedOn().getDayOfMonth());
        assertEquals(OffsetDateTime.now().getHour(), body[0].getCreatedOn().getHour());
        assertEquals(userIds[A], body[0].getCreatedBy());

        assertEquals(orgNames[B], body[1].getOrganismName());
        assertEquals(templates[B], body[1].getTemplate());
        assertEquals(1L, body[1].getGeneIntStart());
        assertEquals(1L, body[1].getGeneIntCurrent());
        assertEquals(1L, body[1].getTranscriptIntStart());
        assertEquals(1L, body[1].getTranscriptIntCurrent());
        assertNotNull(body[1].getCreatedOn());
        assertEquals(OffsetDateTime.now().getYear(), body[1].getCreatedOn().getYear());
        assertEquals(
          OffsetDateTime.now().getMonthValue(),
          body[1].getCreatedOn().getMonth().getValue()
        );
        assertEquals(OffsetDateTime.now().getDayOfMonth(), body[1].getCreatedOn().getDayOfMonth());
        assertEquals(OffsetDateTime.now().getHour(), body[1].getCreatedOn().getHour());
        assertEquals(userIds[B], body[1].getCreatedBy());
      }
    }
  }
}
