package org.veupathdb.service.osi.service.idset;

import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.vulpine.lib.query.util.basic.BasicPreparedReadQuery;
import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.db.SQL;
import org.veupathdb.service.osi.db.SQL.Select.Osi.IdSets;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.NewIdSet;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.util.QueryUtil;

public class IdSetRepo
{
  @SuppressWarnings("FieldMayBeFinal")
  private static IdSetRepo instance = new IdSetRepo();

  private final Logger log = LogProvider.logger(getClass());

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static IdSetRepo getInstance() {
    return instance;
  }

  public static List < IdSet > select(final RecordQuery query)
  throws Exception {
    return getInstance().selectByQuery(query);
  }

  public static Optional < IdSet > select(final long id) throws Exception {
    return getInstance().selectById(id);
  }

  public static IdSet insert(final NewIdSet set, final Connection con)
  throws Exception {
    return getInstance().insertRow(set, con);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public List < IdSet > selectByQuery(final RecordQuery query)
  throws Exception {
    log.trace("IdSetRepo#selectByQuery(RecordQuery)");

    var out = new ArrayList < IdSet >();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(IdSets.BY_QUERY)
    ) {
      if (query.hasStart())
        ps.setObject(1, query.getStart());
      else
        ps.setNull(1, Types.TIMESTAMP_WITH_TIMEZONE);

      if (query.hasEnd())
        ps.setObject(2, query.getEnd());
      else
        ps.setNull(2, Types.TIMESTAMP_WITH_TIMEZONE);

      if (query.hasCreatedById())
        ps.setLong(3, query.getCreatedById());
      else
        ps.setNull(3, Types.INTEGER);

      if (query.hasCreatedByName())
        ps.setString(4, query.getCreatedByName());
      else
        ps.setNull(4, Types.VARCHAR);

      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          out.add(IdSetUtil.newIdSet(rs));
        }
      }
    }

    return out;
  }

  public Optional < IdSet > selectById(final long id) throws Exception {
    log.trace("IdSetRepo#selectById(long)");

    return new BasicPreparedReadQuery<>(
      IdSets.BY_ID,
      DbMan::connection,
      QueryUtil.option(IdSetUtil::newIdSet),
      QueryUtil.singleId(id)
    ).execute().getValue();
  }

  public IdSet insertRow(final NewIdSet set, final Connection con) throws Exception {
    log.trace("IdSetRepo#insertRow(NewIdSet, Connection)");

    return new BasicPreparedReadQuery<>(
      SQL.Insert.Osi.ID_SET,
      con,
      QueryUtil.must(rs -> IdSetUtil.newIdSet(rs, set)),
      ps -> {
        ps.setLong(1, set.getOrganism().getId());
        ps.setString(2, set.getTemplate());
        ps.setLong(3, set.getCounterStart());
        ps.setInt(4, set.getNumIssued());
        ps.setLong(5, set.getCreatedBy().getUserId());
      }
    ).execute().getValue();
  }
}
