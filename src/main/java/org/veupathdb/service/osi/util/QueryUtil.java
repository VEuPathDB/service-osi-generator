package org.veupathdb.service.osi.util;

import java.util.Optional;

import io.vulpine.lib.query.util.RowParser;
import io.vulpine.lib.query.util.StatementPreparer;

public class QueryUtil
{
  public static StatementPreparer singleId(long id) {
    return ps -> ps.setLong(1, id);
  }

  public static StatementPreparer singleString(String id) {
    return ps -> ps.setString(1, id);
  }

  public static StatementPreparer idSet(long[] ids) {
    return ps -> ps.setObject(1, ids);
  }

  public static < R > RowParser < Optional < R > > option(RowParser < R > ps) {
    return rs -> {
      if (!rs.next())
        return Optional.empty();
      return Optional.of(ps.parse(rs));
    };
  }

  public static < R > RowParser < R > must(RowParser < R > ps) {
    return rs -> {
      if (!rs.next())
        throw new IllegalStateException(); // TODO: give this an error message
      return ps.parse(rs);
    };
  }
}
