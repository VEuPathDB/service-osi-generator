package org.veupathdb.service.osi.repo;

import java.sql.Types;
import java.util.*;

import io.vulpine.lib.query.util.basic.BasicPreparedListReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedMapReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedReadQuery;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.IdSetCollection;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.model.db.User;
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

  public static Map < Integer, IdSet > selectIdSetsByCollections(
    final int[] collectionIds,
    final Map < Integer, User > users,
    final Map < Integer, Organism > organisms,
    final Map < Integer, IdSetCollection > collections
  ) throws Exception {
    var out = new HashMap < Integer, IdSet >(collectionIds.length);

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(IdSets.BY_COLLECTIONS)
    ) {
      ps.setObject(1, collectionIds);

      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          var row = IdSetUtils.newIdSet(rs, users, organisms, collections);
          out.put(row.getId(), row);
        }
      }
    }

    return out;
  }

  public static Map < Long, IdSet > selectIdSetsByCollection(
    final IdSetCollection collection,
    final Map < Long, User > users,
    final Map < Long, Organism > organisms
  ) throws Exception {
    return new BasicPreparedMapReadQuery<>(
      IdSets.BY_COLLECTIONS,
      DbMan::connection,
      IdSetUtils::getId,
      rs -> IdSetUtils.newIdSet(rs, users, organisms, collection),
      QueryUtil.singleId(collection.getCollectionId())
    ).execute().getValue();
  }
}
