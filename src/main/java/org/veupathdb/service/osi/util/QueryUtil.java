package org.veupathdb.service.osi.util;

import java.util.Objects;
import java.util.Optional;

import io.vulpine.lib.query.util.RowParser;
import io.vulpine.lib.query.util.StatementPreparer;
import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;

public class QueryUtil
{
  @SuppressWarnings("FieldMayBeFinal")
  private static QueryUtil instance = new QueryUtil();

  private final Logger log = LogProvider.logger(getClass());

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static QueryUtil getInstance() {
    return instance;
  }

  public static StatementPreparer singleId(final long id) {
    return getInstance().prepSingleId(id);
  }

  public static StatementPreparer singleString(final String id) {
    return getInstance().prepSingleString(id);
  }

  public static StatementPreparer idSet(final long[] ids) {
    return getInstance().prepIdSet(ids);
  }

  public static StatementPreparer stringSet(final String[] vals) {
    return getInstance().prepStringSet(vals);
  }

  public static < R > RowParser < Optional < R > > option(
    final RowParser < R > ps
  ) {
    return getInstance().optionalResult(ps);
  }

  public static < R > RowParser < R > must(RowParser < R > ps) {
    return getInstance().requiredResult(ps);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public StatementPreparer prepSingleId(final long id) {
    log.trace("QueryUtil#prepSingleId(long)");
    return ps -> {
      log.trace("QueryUtil#prepSingleId$prep(PreparedStatement)");
      ps.setLong(1, id);
    };
  }

  public StatementPreparer prepSingleString(final String id) {
    log.trace("QueryUtil#prepSingleString(String)");
    return ps -> {
      log.trace("QueryUtil#prepSingleString$prep(PreparedStatement)");
      ps.setString(1, id);
    };
  }

  public StatementPreparer prepIdSet(final long[] ids) {
    log.trace("QueryUtil#prepIdSet(long[])");
    return ps -> {
      log.trace("QueryUtil#prepIdSet$prep(PreparedStatement)");
      ps.setObject(1, ids);
    };
  }

  public StatementPreparer prepStringSet(final String[] vals) {
    log.trace("QueryUtil#prepStringSet(String[])");
    return ps -> {
      log.trace("QueryUtil#prepStringSet$prep(PreparedStatement)");
      ps.setObject(1, vals);
    };
  }

  public < R > RowParser < Optional < R > > optionalResult(
    final RowParser < R > ps
  ) {
    log.trace("QueryUtil#optionalResult(RowParser)");

    Objects.requireNonNull(ps);

    return rs -> {
      if (!rs.next())
        return Optional.empty();

      return Optional.of(ps.parse(rs));
    };
  }

  public < R > RowParser < R > requiredResult(final RowParser < R > ps) {
    log.trace("QueryUtil#requiredResult(RowParser)");

    Objects.requireNonNull(ps);

    return rs -> {
      if (!rs.next())
        throw new IllegalStateException(); // TODO: give this an error message

      return ps.parse(rs);
    };
  }
}
