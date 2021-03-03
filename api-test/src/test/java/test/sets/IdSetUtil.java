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
    + "(organism_id, template, counter_start, num_issued, created_by) "
    + "VALUES (?, ?, ?, ?, ?) RETURNING id_set_id;";

  public static final long insertIdSet(
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
      ps.setLong(1, organism);
      ps.setString(2, template);
      ps.setLong(3, countStart);
      ps.setInt(4, numIssued);
      ps.setLong(5, user);

      try (var rs = ps.executeQuery()) {
        rs.next();
        return rs.getLong(1);
      }
    }
  }
}
