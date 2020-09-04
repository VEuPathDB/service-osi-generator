package test.organisms;

import test.TestBase;

public class OrganismUtil
{
  private static final String
    INSERT_SQL = "INSERT INTO osi.organisms (name, template, created_by) VALUES (?, ?, ?) RETURNING organism_id;";

  public static long createOrganism(final String name, final String template, final long user)
  throws Exception {
    try (
      var con = TestBase.dataSource.getConnection();
      var ps  = con.prepareStatement(INSERT_SQL)
    ) {
      ps.setString(1, name);
      ps.setString(2, template);
      ps.setLong(3, user);

      try (var rs = ps.executeQuery()) {
        rs.next();
        return rs.getLong(1);
      }
    }
  }
}
