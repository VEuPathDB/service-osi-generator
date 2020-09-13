package test.organisms;

import java.time.OffsetDateTime;

import test.DbUtil;
import test.OrganismResponse;
import test.TestBase;

public class OrganismUtil
{
  public static final String API_PATH = "/organisms";

  private static final String
    INSERT_SQL = "INSERT INTO osi.organisms (name, template, created_by, created) VALUES (?, ?, ?, ?) RETURNING organism_id;";

  public static long createOrganism(final String name, final String template, final long user)
  throws Exception {
    return createOrganism(name, template, user, OffsetDateTime.now());
  }

  public static long createOrganism(
    final String name,
    final String template,
    final long user,
    final OffsetDateTime created
  ) throws Exception {
    try (
      var con = DbUtil.getServiceDataSource().getConnection();
      var ps = con.prepareStatement(INSERT_SQL)
    ) {
      ps.setString(1, name);
      ps.setString(2, template);
      ps.setLong(3, user);
      ps.setObject(4, created);

      try (var rs = ps.executeQuery()) {
        rs.next();
        return rs.getLong(1);
      }
    }
  }

  private static final String
    UPDATE_SQL = "UPDATE osi.organisms"
    + " SET gene_counter_current = gene_counter_current + 1"
    + ", transcript_counter_current = transcript_counter_current + 1"
    + " WHERE organism_id = ?;";

  public static void incrementCounters(final long orgId) throws Exception {
    try (
      var con = DbUtil.getServiceDataSource().getConnection();
      var ps = con.prepareStatement(UPDATE_SQL);
    ) {
      ps.setLong(1, orgId);
      ps.execute();
    }
  }

  private static final String
    SELECT_SQL = "SELECT * FROM osi.organisms WHERE organism_id = ?;";

  public static OrganismResponse getOrganism(final long orgId) throws Exception {
    try (
      var con = DbUtil.getServiceDataSource().getConnection();
      var ps = con.prepareStatement(SELECT_SQL);
    ) {
      ps.setLong(1, orgId);
      try (var rs = ps.executeQuery()) {
        rs.next();

        var meta  = rs.getMetaData();
        var count = meta.getColumnCount();
        var out   = new OrganismResponse();

        for (var i = 1; i <= count; i++) {
          var name = meta.getColumnName(i);

          var field = OrganismResponse.class.getDeclaredField(name);
          field.setAccessible(true);

          var type = field.getType();
          if (type.equals(Long.class))
            field.set(out, rs.getLong(i));
          else if (type.equals(String.class))
            field.set(out, rs.getString(i));
          else if (type.equals(OffsetDateTime.class))
            field.set(out, rs.getObject(i, OffsetDateTime.class));
          else
            throw new IllegalStateException("Unrecognized type for field " + name);
        }

        return out;
      }
    }
  }
}
