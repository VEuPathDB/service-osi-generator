package test.collections;

import java.time.OffsetDateTime;

import test.*;

import static io.restassured.RestAssured.with;

public class CollectionUtil
{
  public static final String API_PATH = "/idSetCollections";

  public static final String
    QUERY_NAME   = "name",
    QUERY_USER   = "createdBy",
    QUERY_AFTER  = "createdAfter",
    QUERY_BEFORE = "createdBefore";

  private static final String
    INSERT_COLLECTION = "INSERT INTO"
    + " osi.id_set_collections (name, created_by, created)"
    + " VALUES  (?, ?, ?)"
    + " RETURNING id_set_coll_id;";

  public static long createCollection(final String name, final long user) throws Exception {
    return createCollection(name, user, OffsetDateTime.now());
  }

  public static long createCollection(final String name, final long user, final OffsetDateTime date) throws Exception {
    try (
      var con = DbUtil.getServiceDataSource().getConnection();
      var ps = con.prepareStatement(INSERT_COLLECTION)
    ) {
      ps.setString(1, name);
      ps.setLong(2, user);
      ps.setObject(3, date);

      try (var rs = ps.executeQuery()) {
        rs.next();
        return rs.getLong(1);
      }
    }
  }

  public static CollectionResponse getCollection(final UserRecord user, final long collectionId) {
    return with()
      .header("Authorization", TestBase.authHeader(user.getUserName(), user.getApiKey()))
      .get(TestBase.makeUrl(API_PATH + "/" + collectionId))
      .as(CollectionResponse.class);
  }
}
