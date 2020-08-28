package org.veupathdb.service.osi.service.idset;

import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.vulpine.lib.query.util.basic.BasicPreparedListReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedReadQuery;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.NewIdSet;
import org.veupathdb.service.osi.db.SQL;
import org.veupathdb.service.osi.db.SQL.Select.Osi.IdSets;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.util.QueryUtil;

public class IdSetRepo
{
  public static List < IdSet > select(RecordQuery query)
  throws Exception {
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
          out.add(IdSetUtil.newIdSetRow(rs));
        }
      }
    }

    return out;
  }

  public static List < IdSet > selectByCollectionId(long collectionId)
  throws Exception {
    return new BasicPreparedListReadQuery<>(
      SQL.Select.Osi.IdSets.BY_COLLECTION,
      DbMan::connection,
      IdSetUtil::newIdSetRow,
      QueryUtil.singleId(collectionId)
    ).execute().getValue();
  }

  public static List < IdSet > selectByCollectionIds(long[] collectionIds)
  throws Exception {
    return new BasicPreparedListReadQuery<>(
      SQL.Select.Osi.IdSets.BY_COLLECTIONS,
      DbMan::connection,
      IdSetUtil::newIdSetRow,
      QueryUtil.idSet(collectionIds)
    ).execute().getValue();
  }

  public static Optional < IdSet > select(long id) throws Exception {
    return new BasicPreparedReadQuery<>(
      IdSets.BY_ID,
      DbMan::connection,
      QueryUtil.option(IdSetUtil::newIdSetRow),
      QueryUtil.singleId(id)
    ).execute().getValue();
  }

  public static IdSet insert(
    final NewIdSet set,
    final Connection con
  ) throws Exception {
    return new BasicPreparedReadQuery<>(
      SQL.Insert.Osi.ID_SET,
      DbMan::connection,
      QueryUtil.must(rs -> IdSetUtil.newIdSetRow(rs, set)),
      ps -> {
        ps.setLong(1, set.getCollection().getId());
        ps.setLong(2, set.getOrganism().getId());
        ps.setString(3, set.getTemplate());
        ps.setLong(4, set.getCounterStart());
        ps.setInt(5, set.getNumIssued());
        ps.setLong(6, set.getCreatedBy().getUserId());
      }
    ).execute().getValue();
  }
}
