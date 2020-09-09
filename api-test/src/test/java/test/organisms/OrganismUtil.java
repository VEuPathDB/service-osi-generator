package test.organisms;

import java.time.OffsetDateTime;

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
      var con = TestBase.dataSource.getConnection();
      var ps  = con.prepareStatement(INSERT_SQL)
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
}
