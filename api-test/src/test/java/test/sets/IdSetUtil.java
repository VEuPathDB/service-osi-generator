package test.sets;

import org.apiguardian.api.API;
import test.DbUtil;
import test.IdSetResponse;

import static io.restassured.RestAssured.given;

public class IdSetUtil
{
  public static final String API_PATH = "/idSets";

  private static final String
    INSERT_SQL = "INSERT INTO osi.id_sets "
    + "(id_set_coll_id, organism_id, template, counter_start, num_issued, created_by) "
    + "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_set_id;";

  public static final long insertIdSet(
    final long collection,
    final long organism,
    final String template,
    final long countStart,
    final int numIssued,
    final long user
  ) throws Exception {
    try (
      var con = DbUtil.getServiceDataSource().getConnection();
      var ps  = con.prepareStatement(INSERT_SQL)
    ) {
      ps.setLong(1, collection);
      ps.setLong(2, organism);
      ps.setString(3, template);
      ps.setLong(4, countStart);
      ps.setInt(5, numIssued);
      ps.setLong(6, user);

      try (var rs = ps.executeQuery()) {
        rs.next();
        return rs.getLong(1);
      }
    }
  }
}
