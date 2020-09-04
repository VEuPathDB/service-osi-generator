package test.collections;

import com.fasterxml.jackson.databind.JsonNode;
import test.Assert;
import test.TestBase;
import test.TestUtil;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.TestUtil.Json;

public class CollectionUtil
{
  public static final String API_PATH = "/idSetCollections";

  public static final String
    KEY_NAME          = "name",
    KEY_CREATED_BY    = "createdBy",
    KEY_COLLECTION_ID = "collectionId",
    KEY_CREATED_ON    = "createdOn",
    KEY_ID_SETS       = "idSets";

  private static final String
    INSERT_COLLECTION = "INSERT INTO osi.id_set_collections (name, created_by) VALUES  (?, ?) RETURNING id_set_coll_id;";

  public static long createCollection(final String name, final long user) throws Exception {
    try (
      var con = TestBase.dataSource.getConnection();
      var ps = con.prepareStatement(INSERT_COLLECTION)
    ) {
      ps.setString(1, name);
      ps.setLong(2, user);

      try (var rs = ps.executeQuery()) {
        rs.next();
        return rs.getLong(1);
      }
    }
  }

  public static JsonNode createRequest(final String name) {
    return Json.createObjectNode()
      .put(KEY_NAME, name);
  }

  public static void validateCollection(final JsonNode node) {
    assertTrue(node.isObject());

    Assert.Json.contains(node, KEY_COLLECTION_ID);
    Assert.Json.isIntegral(KEY_COLLECTION_ID, node.get(KEY_COLLECTION_ID));
    Assert.Json.isGreaterThan(KEY_COLLECTION_ID, node.get(KEY_COLLECTION_ID), 0);

    Assert.Json.contains(node, KEY_CREATED_BY);
    Assert.Json.isIntegral(KEY_CREATED_BY, node.get(KEY_CREATED_BY));
    Assert.Json.isGreaterThan(KEY_CREATED_BY, node.get(KEY_CREATED_BY), 0);

    Assert.Json.contains(node, KEY_CREATED_ON);
    Assert.Json.isString(KEY_CREATED_ON, node.get(KEY_CREATED_ON));
    Assert.Json.isIsoDate(KEY_CREATED_ON, node.get(KEY_CREATED_ON));

    Assert.Json.contains(node, KEY_NAME);
    Assert.Json.isString(KEY_NAME, node.get(KEY_NAME));
    Assert.Json.stringMinLength(KEY_NAME, node.get(KEY_NAME), 3);

    Assert.Json.contains(node, KEY_ID_SETS);
    Assert.Json.isArray(KEY_ID_SETS, node.get(KEY_ID_SETS));
  }
}
