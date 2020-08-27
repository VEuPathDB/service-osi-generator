package org.veupathdb.service.osi.repo;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.vulpine.lib.query.util.basic.BasicPreparedListReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedReadQuery;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.raw.IdSetRow;
import org.veupathdb.service.osi.repo.SQL.Select.Osi.IdSets;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.service.genes.IdSetUtils;
import org.veupathdb.service.osi.util.QueryUtil;

public class IdSetRepo
{
  public static List < IdSetRow > select(RecordQuery query)
  throws Exception {
    var out = new ArrayList < IdSetRow >();

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
          out.add(IdSetUtils.newIdSetRow(rs));
        }
      }
    }

    return out;
  }

  public static List < IdSetRow > selectByCollectionId(long collectionId)
  throws Exception {
    return new BasicPreparedListReadQuery<>(
      SQL.Select.Osi.IdSets.BY_COLLECTION,
      DbMan::connection,
      IdSetUtils::newIdSetRow,
      QueryUtil.singleId(collectionId)
    ).execute().getValue();
  }

  public static List < IdSetRow > selectByCollectionIds(long[] collectionIds)
  throws Exception {
    return new BasicPreparedListReadQuery<>(
      SQL.Select.Osi.IdSets.BY_COLLECTIONS,
      DbMan::connection,
      IdSetUtils::newIdSetRow,
      QueryUtil.idSet(collectionIds)
    ).execute().getValue();
  }

  public static Optional < IdSetRow > select(long id) throws Exception {
    return new BasicPreparedReadQuery<>(
      IdSets.BY_ID,
      DbMan::connection,
      QueryUtil.option(IdSetUtils::newIdSetRow),
      QueryUtil.singleId(id)
    ).execute().getValue();
  }
}
